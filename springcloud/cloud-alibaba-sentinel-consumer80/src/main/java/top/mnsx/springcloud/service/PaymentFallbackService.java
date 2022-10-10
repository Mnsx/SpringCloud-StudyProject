package top.mnsx.springcloud.service;

import org.springframework.stereotype.Service;

/**
 * @BelongsProject: springcloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/10 9:27
 * @Description:
 */
@Service
public class PaymentFallbackService implements PaymentService {
    @Override
    public String paymentSQL(Long id) {
        return "服务降级返回";
    }
}
