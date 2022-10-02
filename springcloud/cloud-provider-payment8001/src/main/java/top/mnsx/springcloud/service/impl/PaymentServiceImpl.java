package top.mnsx.springcloud.service.impl;

import org.springframework.stereotype.Service;
import top.mnsx.springcloud.dao.PaymentDao;
import top.mnsx.springcloud.entity.Payment;
import top.mnsx.springcloud.service.PaymentService;

import javax.annotation.Resource;

/**
 * @BelongsProject: spring-cloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/1 10:46
 * @Description:
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
