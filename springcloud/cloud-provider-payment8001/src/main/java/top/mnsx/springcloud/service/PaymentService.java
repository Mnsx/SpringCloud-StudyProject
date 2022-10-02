package top.mnsx.springcloud.service;

import org.apache.ibatis.annotations.Param;
import top.mnsx.springcloud.entity.Payment;

/**
 * @BelongsProject: spring-cloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/1 10:45
 * @Description:
 */
public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
