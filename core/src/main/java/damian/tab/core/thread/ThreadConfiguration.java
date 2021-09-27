package damian.tab.core.thread;

import damian.tab.core.zmq.SocketDelivererService;
import damian.tab.core.zmq.SocketProxy;
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

    private final ZContextConfiguration zContextConfiguration;
    private final SocketDelivererService socketDeliverer;


    /**
     * When invoked from createDistributedThread method then use the same context as DistributedThread.
     * When invoked alone then everytime new ZContext.
     * @param context ZContext
     * @return
     */
    @Bean
    @Scope(value = "prototype")
    public ClientZmqThread createClientZmq(ZContext context) {
        SocketProxy publisher = socketDeliverer.createPublisher(context);
        return new ClientZmqThread(context, publisher);
    }

    @Bean
    @Scope(value = "prototype")
    public DistributedThread createDistributedThread(Runnable runnable) {
        ZContext context = zContextConfiguration.createZMQContext();
        return new DistributedThread(runnable, context, createClientZmq(context));
    }
}
