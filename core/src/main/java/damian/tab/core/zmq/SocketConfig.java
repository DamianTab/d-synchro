package damian.tab.core.zmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zeromq.ZContext;

@Configuration
public class SocketConfig {

    @Bean
    public ZContext createZMQContext() {
        return new ZContext();
    }
}
