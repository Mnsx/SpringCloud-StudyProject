package top.mnsx.springcloud.config;

/**
 * @BelongsProject: springcloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/4 10:37
 * @Description:
 */

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouterLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_pornhub", r -> r.path("/ps").uri("http://baidu.com/guonei")).build();
        return routes.build();
    }
}
