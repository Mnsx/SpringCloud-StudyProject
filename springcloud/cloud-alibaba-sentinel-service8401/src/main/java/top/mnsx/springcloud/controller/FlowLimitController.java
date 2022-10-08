package top.mnsx.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.mnsx.springcloud.handler.CustomerBlockHandler;

import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: springcloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/8 8:29
 * @Description:
 */
@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() {
        return "---testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "---testB";
    }

    @GetMapping("/testC")
    @SentinelResource(value = "testC", blockHandler = "handlerException", blockHandlerClass = CustomerBlockHandler.class)
    public String testC() {
        return "---testC";
    }

    @GetMapping("/testD")
    public String testD() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("---testD");
        return "---testD";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "handlerException", blockHandlerClass = CustomerBlockHandler.class)
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return p1 + p2;
    }

    public String deadHandler(String p1, String p2, BlockException exception) {
        return "烂了";
    }
}
