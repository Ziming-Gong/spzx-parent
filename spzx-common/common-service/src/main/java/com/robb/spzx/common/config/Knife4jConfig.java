package com.robb.spzx.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public GroupedOpenApi adminApi() {  //创建一个API接口的分组
        return GroupedOpenApi.builder()
                .group("admin-api")  // 分组名称
                .pathsToMatch("/admin/**") // 接口请求路径规则：满足这个接口的路径
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SPZX API Document")
                        .version("1,0")
                        .description("SPZX API Document")
                        .contact(new Contact().name("Awesome Boy"))
                );

    }

}
