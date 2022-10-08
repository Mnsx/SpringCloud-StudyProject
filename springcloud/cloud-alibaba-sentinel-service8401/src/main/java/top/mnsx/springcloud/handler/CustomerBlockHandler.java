package top.mnsx.springcloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @BelongsProject: springcloud
 * @User: Mnsx_x
 * @CreateTime: 2022/10/8 13:40
 * @Description:
 */
public class CustomerBlockHandler {
    public static String handlerException(BlockException exception) {
        return "error";
    }
}
