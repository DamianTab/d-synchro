package damian.tab.core.thread;

import damian.tab.core.task.DistributedTask;
import damian.tab.core.zmq.SocketProxy;
import damian.tab.core.zmq.SocketProxyDelivererService;
import damian.tab.core.zmq.SocketProxyHandler;
import damian.tab.core.zmq.ZContextConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.zeromq.ZContext;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ThreadConfiguration {

    private final SocketProxyHandler proxyHandler;
    private final ZContextConfiguration zContextConfiguration;
    private final SocketProxyDelivererService socketDeliverer;

    @Bean
    @Scope(value = "prototype")
    public ClientListenerRunnable createClientListenerRunnable() {
        ZContext context = zContextConfiguration.createZMQContext();
        SocketProxy publisher = socketDeliverer.createPublisher(context);
        SocketProxy initializationRequester = socketDeliverer.createPortMapperRequester(context);
        return new ClientListenerRunnable(context, publisher, proxyHandler, initializationRequester);
    }

    @Bean
    @Scope(value = "prototype")
    public PortMapperListenerRunnable createPortMapperListenerRunnable() {
        ZContext context = zContextConfiguration.createZMQContext();
        SocketProxy publisher = socketDeliverer.createPublisher(context);
        SocketProxy initializationReplayer = socketDeliverer.createPortMapperReplayer(context);
        return new PortMapperListenerRunnable(context, publisher, proxyHandler, initializationReplayer);
    }

    @Bean
    @Scope(value = "prototype")
    public DistributedThread createDistributedThread(DistributedTask distributedTask) {
        return new DistributedThread(distributedTask, createClientListenerRunnable());
    }
}
