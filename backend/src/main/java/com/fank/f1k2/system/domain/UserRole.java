package com.fank.f1k2.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("t_user_role")
@Data
public class UserRole implements Serializable{
	
	private static final long serialVersionUID = -3166012934498268403L;

	private Long userId;

	private Long roleId;

}