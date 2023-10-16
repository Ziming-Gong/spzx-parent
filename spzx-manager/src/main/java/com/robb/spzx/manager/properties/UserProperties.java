package com.robb.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 读取properties里面的内容
 */

@Data
@ConfigurationProperties(prefix = "spzx.auth")
public class UserProperties {
    private List<String> noAuthUrls;
}
