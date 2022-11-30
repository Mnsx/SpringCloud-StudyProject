package cn.itcast.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Data
@Component
@ConfigurationProperties(prefix = "pattern")
public class TestConfigProperties {
    private String dateformat;
    private String name;
}
