package cn.itcast.order.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Configuration
public class FeignConfig {
    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.BASIC;
    }
}
