package com.fank.f1k2.business.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {

    SseEmitter getConn(String clientId);

    void send(String clientId, String message);

    void closeConn(String clientId);

    String img(String message);

}
