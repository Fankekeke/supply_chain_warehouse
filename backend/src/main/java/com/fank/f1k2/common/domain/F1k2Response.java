package com.fank.f1k2.common.domain;

import java.util.HashMap;

public class F1k2Response extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public F1k2Response message(String message) {
        this.put("message", message);
        return this;
    }

    public F1k2Response data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public F1k2Response put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
