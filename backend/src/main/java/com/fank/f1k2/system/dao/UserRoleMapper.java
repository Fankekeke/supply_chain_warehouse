package com.fank.f1k2.system.dao;

import com.fank.f1k2.system.domain.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
     */
    Boolean deleteByUserId(@Param("userId") Long userId);

    /**
     * 根据角色Id删除该角色的用户关系
     *
     * @param roleId 角色ID
     * @return boolean
     * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
     */
    Boolean deleteByRoleId(@Param("roleId") Long roleId);
}