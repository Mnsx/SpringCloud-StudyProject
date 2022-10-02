package top.mnsx.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mnsx.springcloud.entity.Payment;

/**
 * @BelongsProject: spring-cloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/1 10:30
 * @Description:
 */
@Mapper
public interface PaymentDao {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
