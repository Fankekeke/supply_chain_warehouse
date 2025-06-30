package com.fank.f1k2.business.entity.vo;

import lombok.Data;

@Data
public class ChatVo {

    private String content;
    private String createDate;

    /**
     * 1:供应商消息 2:系统消息
     */
    private String type;
}
