package com.fank.f1k2.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.business.entity.StaffInfo;
import com.fank.f1k2.business.entity.SupplierInfo;
import com.fank.f1k2.business.entity.SupplierMain;
import com.fank.f1k2.business.service.IStaffInfoService;
import com.fank.f1k2.business.service.ISupplierInfoService;
import com.fank.f1k2.business.service.ISupplierMainService;
import com.fank.f1k2.common.domain.F1k2Constant;
import com.fank.f1k2.common.domain.QueryRequest;
import com.fank.f1k2.common.service.CacheService;
import com.fank.f1k2.common.utils.SortUtil;
import com.fank.f1k2.common.utils.MD5Util;
import com.fank.f1k2.system.dao.UserMapper;
import com.fank.f1k2.system.dao.UserRoleMapper;
import com.fank.f1k2.system.domain.User;
import com.fank.f1k2.system.domain.UserRole;
import com.fank.f1k2.system.manager.UserManager;
import com.fank.f1k2.system.service.UserConfigService;
import com.fank.f1k2.system.service.UserRoleService;
import com.fank.f1k2.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserConfigService userConfigService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserManager userManager;

    @Autowired
    private IStaffInfoService staffInfoService;

    @Autowired
    private ISupplierInfoService supplierInfoService;

    @Autowired
    private ISupplierMainService supplierMainService;


    @Override
    public User findByName(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }


    @Override
    public IPage<User> findUserDetail(User user, QueryRequest request) {
        try {
            Page<User> page = new Page<>();
            SortUtil.handlePageSort(request, page, "userId", F1k2Constant.ORDER_ASC, false);
            return this.baseMapper.findUserDetail(page, user);
        } catch (Exception e) {
            log.error("查询用户异常", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void updateLoginTime(String username) throws Exception {
        User user = new User();
        user.setLastLoginTime(new Date());

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));

        // 重新将用户信息加载到 redis中
        cacheService.saveUser(username);
    }

    @Override
    @Transactional
    public void createUser(User user) throws Exception {
        // 创建用户
        user.setCreateTime(new Date());
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setPassword(MD5Util.encrypt(user.getUsername(), User.DEFAULT_PASSWORD));
        save(user);

        // 保存用户角色
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);

        // 创建用户默认的个性化配置
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));

        // 将用户相关信息保存到 Redis中
        userManager.loadUserRedisCache(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) throws Exception {
        // 更新用户
        user.setPassword(null);
        user.setModifyTime(new Date());
        updateById(user);

        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getUserId()));

        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);

        // 重新将用户信息，用户角色信息，用户权限信息 加载到 redis中
        cacheService.saveUser(user.getUsername());
        cacheService.saveRoles(user.getUsername());
        cacheService.savePermissions(user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUsers(String[] userIds) throws Exception {
        // 先删除相应的缓存
        this.userManager.deleteUserRedisCache(userIds);

        List<String> list = Arrays.asList(userIds);

        removeByIds(list);

        // 删除用户角色
        this.userRoleService.deleteUserRolesByUserId(userIds);
        // 删除用户个性化配置
        this.userConfigService.deleteByUserId(userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProfile(User user) throws Exception {
        updateById(user);
        // 重新缓存用户信息
        cacheService.saveUser(user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String username, String avatar) throws Exception {
        User user = new User();
        user.setAvatar(avatar);

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // 重新缓存用户信息
        cacheService.saveUser(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String username, String password) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // 重新缓存用户信息
        cacheService.saveUser(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void regist(String username, String password) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setUsername(username);
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSsex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setDescription("注册用户");
        this.save(user);

        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(2L); // 注册用户角色 ID
        this.userRoleMapper.insert(ur);

        // 创建用户默认的个性化配置
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));
        // 将用户相关信息保存到 Redis中
        userManager.loadUserRedisCache(user);

    }

    /**
     * 注册员工
     *
     * @param username 用户名
     * @param password 密码
     * @param staffName 员工姓名
     */
    @Override
    public void registStaff(String username, String password, String staffName) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setUsername(username);
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSsex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setDescription("注册员工");
        this.save(user);

        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(75L);
        this.userRoleMapper.insert(ur);

        StaffInfo staffInfo = new StaffInfo();
        staffInfo.setCode("STF-" + System.currentTimeMillis());
        staffInfo.setName(staffName);
        staffInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        staffInfo.setSysUserId(Math.toIntExact(user.getUserId()));
        staffInfo.setDelFlag("0");
        staffInfoService.save(staffInfo);

        // 创建用户默认的个性化配置
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));
        // 将用户相关信息保存到 Redis中
        userManager.loadUserRedisCache(user);
    }

    /**
     * 注册供应商
     *
     * @param username 用户名
     * @param password 密码
     * @param supplierName 供应商名称
     */
    @Override
    public void registSupplier(String username, String password, String supplierName) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setUsername(username);
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSsex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setDescription("注册供应商");
        this.save(user);

        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(76L);
        this.userRoleMapper.insert(ur);

        SupplierInfo supplierInfo = new SupplierInfo();
        supplierInfo.setCode("SUP-" + System.currentTimeMillis());
        supplierInfo.setSysUserId(Math.toIntExact(user.getUserId()));
        supplierInfo.setStatus("0");
        supplierInfo.setName(supplierName);
        supplierInfoService.save(supplierInfo);

        SupplierMain supplierMain = new SupplierMain();
        supplierMain.setSupplierId(supplierInfo.getId());
        supplierMain.setCorpName(supplierName);
        supplierMainService.save(supplierMain);
        // 创建用户默认的个性化配置
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));
        // 将用户相关信息保存到 Redis中
        userManager.loadUserRedisCache(user);
    }

    /**
     * 注册员工
     *
     * @param staffInfo 员工信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerStaff(StaffInfo staffInfo) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(staffInfo.getCode(), "1234qwer"));
        user.setUsername(staffInfo.getCode());
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSsex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setDescription("注册员工");
        this.save(user);

        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(75L);
        this.userRoleMapper.insert(ur);

        staffInfo.setSysUserId(Math.toIntExact(user.getUserId()));
        staffInfoService.save(staffInfo);

        // 创建用户默认的个性化配置
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));
        // 将用户相关信息保存到 Redis中
        userManager.loadUserRedisCache(user);
    }

    /**
     * 注册供应商
     *
     * @param supplierInfo 供应商信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerSupplier(SupplierInfo supplierInfo) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(supplierInfo.getCode(), "1234qwer"));
        user.setUsername(supplierInfo.getCode());
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSsex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setDescription("注册供应商");
        this.save(user);

        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(76L);
        this.userRoleMapper.insert(ur);

        supplierInfo.setSysUserId(Math.toIntExact(user.getUserId()));
        supplierInfo.setStatus("1");
        supplierInfoService.updateById(supplierInfo);

        // 创建用户默认的个性化配置
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));
        // 将用户相关信息保存到 Redis中
        userManager.loadUserRedisCache(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String[] usernames) throws Exception {
        for (String username : usernames) {

            User user = new User();
            user.setPassword(MD5Util.encrypt(username, User.DEFAULT_PASSWORD));

            this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
            // 重新将用户信息加载到 redis中
            cacheService.saveUser(username);
        }

    }

    private void setUserRoles(User user, String[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(Long.valueOf(roleId));
            this.userRoleMapper.insert(ur);
        });
    }
}
