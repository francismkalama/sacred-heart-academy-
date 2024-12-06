package com.startlet.starlet_academy.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "api.props")
@Configuration
public class AppConfigs {
    private String apiUsername;
    private String apiPassword;

}
