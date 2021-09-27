package damian.tab.core.zmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.zeromq.ZContext;

@Configuration
public class ZContextConfiguration {

    @Bean
    @Scope(value = "prototype")
    public ZContext createZMQContext() {
        return new ZContext();
    }
}
