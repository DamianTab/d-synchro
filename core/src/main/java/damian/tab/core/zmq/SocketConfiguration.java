package damian.tab.core.zmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.zeromq.ZContext;

@Configuration
public class SocketConfiguration {

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ZContext createZMQContext() {
        return new ZContext();
    }
}
