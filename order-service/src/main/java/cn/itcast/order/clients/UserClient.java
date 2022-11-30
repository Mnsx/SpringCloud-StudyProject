package cn.itcast.order.clients;

import cn.itcast.order.config.FeignConfig;
import cn.itcast.order.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@FeignClient(value = "userService")
//@FeignClient(value = "userService", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/user/{id}")
    User findById(@PathVariable("id")Long id);
}
