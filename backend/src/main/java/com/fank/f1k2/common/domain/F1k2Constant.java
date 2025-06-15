package com.fank.f1k2.common.domain;

/**
 * F1k2常量
 */
public class F1k2Constant {

    // user缓存前缀
    public static final String USER_CACHE_PREFIX = "febs.cache.user.";
    // user角色缓存前缀
    public static final String USER_ROLE_CACHE_PREFIX = "febs.cache.user.role.";
    // user权限缓存前缀
    public static final String USER_PERMISSION_CACHE_PREFIX = "febs.cache.user.permission.";
    // user个性化配置前缀
    public static final String USER_CONFIG_CACHE_PREFIX = "febs.cache.user.config.";
    // token缓存前缀
    public static final String TOKEN_CACHE_PREFIX = "febs.cache.token.";

    // 存储在线用户的 zset前缀
    public static final String ACTIVE_USERS_ZSET_PREFIX = "febs.user.active";

    // 排序规则： descend 降序
    public static final String ORDER_DESC = "descend";
    // 排序规则： ascend 升序
    public static final String ORDER_ASC = "ascend";

    // 按钮
    public static final String TYPE_BUTTON = "1";
    // 菜单
    public static final String TYPE_MENU = "0";

    // 网络资源 Url
    public static final String MEIZU_WEATHER_URL = "https://aider.meizu.com/app/weather/listWeather";

}
