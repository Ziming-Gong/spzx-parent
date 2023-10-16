package com.robb.spzx.manager.config;

import com.robb.spzx.manager.interceptor.LoginAuthInterceptor;
import com.robb.spzx.manager.properties.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 允许跨域访问
 */
@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor;

    @Autowired
    private UserProperties userProperties;

    /**
     * 拦截器注册
     * 优化： excludePathPatterns的hardcode转入application.properties里面进行
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(userProperties.getNoAuthUrls());
//                .excludePathPatterns("/admin/system/index/login", "/admin/system/index/generateValidateCode");
    }

    /**
     * 跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //路径规则
                .allowCredentials(true)  // 是否允许跨域的情况下传递cookie
                .allowedOriginPatterns("*") //允许请求来源的域规则
                .allowedHeaders("*")   //允许所有请求头
                .allowedMethods("*");  //允许所有请求方法
    }


}
