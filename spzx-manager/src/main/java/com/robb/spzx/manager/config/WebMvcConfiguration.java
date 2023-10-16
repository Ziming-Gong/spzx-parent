package com.robb.spzx.manager.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 允许跨域访问
 */
@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //路径规则
                .allowCredentials(true)  // 是否允许跨域的情况下传递cookie
                .allowedOriginPatterns("*") //允许请求来源的域规则
                .allowedHeaders("*")   //允许所有请求头
                .allowedMethods("*");  //允许所有请求方法
    }


}
