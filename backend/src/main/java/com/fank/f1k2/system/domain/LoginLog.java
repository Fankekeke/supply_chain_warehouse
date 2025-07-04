package com.fank.f1k2.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@TableName("t_login_log")
@Data
public class LoginLog {
    /**
     * 用户 ID
     */
    private String username;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录地点
     */
    private String location;

    private String ip;
}
