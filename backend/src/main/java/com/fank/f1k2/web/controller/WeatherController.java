package com.fank.f1k2.web.controller;

import com.fank.f1k2.common.domain.F1k2Constant;
import com.fank.f1k2.common.domain.F1k2Response;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.common.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("weather")
public class WeatherController {

    @GetMapping
    @RequiresPermissions("weather:view")
    public F1k2Response queryWeather(@NotBlank(message = "{required}") String areaId) throws F1k2Exception {
        try {
            String data = HttpUtil.sendPost(F1k2Constant.MEIZU_WEATHER_URL, "cityIds=" + areaId);
            return new F1k2Response().data(data);
        } catch (Exception e) {
            String message = "天气查询失败";
            log.error(message, e);
            throw new F1k2Exception(message);
        }
    }
}
