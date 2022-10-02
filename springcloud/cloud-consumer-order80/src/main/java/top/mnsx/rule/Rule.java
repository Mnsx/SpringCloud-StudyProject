package top.mnsx.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: springcloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/2 18:36
 * @Description:
 */
@Configuration
public class Rule {
    @Bean
    public IRule myRule() {
        return new RandomRule();
    }
}
