package damian.tab.core.thread;

import damian.tab.core.monitor.algorithm.RicartAgrawalaExecutor;
import damian.tab.core.task.DistributedTaskInterface;
import damian.tab.core.zmq.SocketProxy;
import damian.tab.core.zmq.SocketProxyBuilderService;
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

    private final ZContextConfiguration zContextConfiguration;
    private final SocketProxyHandler proxyHandler;
    private final SocketProxyBuilderService socketBuilderService;
    private final RicartAgrawalaExecutor algorithmExecutor;

    @Bean
    @Scope(value = "prototype")
    public ClientListenerRunnable createClientListenerRunnable() {
        ZContext context = zContextConfiguration.createZMQContext();
        SocketProxy publisher = socketBuilderService.createPublisher(context);
        SocketProxy initializationRequester = socketBuilderService.createPortMapperRequester(context);
        return new ClientListenerRunnable(algorithmExecutor, proxyHandler, context, publisher, initializationRequester);
    }

    @Bean
    @Scope(value = "prototype")
    public PortMapperListenerRunnable createPortMapperListenerRunnable() {
        ZContext context = zContextConfiguration.createZMQContext();
        SocketProxy publisher = socketBuilderService.createPublisher(context);
        SocketProxy initializationReplayer = socketBuilderService.createPortMapperReplayer(context);
        return new PortMapperListenerRunnable(proxyHandler, context, publisher, initializationReplayer);
    }

    @Bean
    @Scope(value = "prototype")
    public DistributedThread createDistributedThread(DistributedTaskInterface distributedTask) {
        ClientListenerRunnable clientListenerRunnable = createClientListenerRunnable();
        distributedTask.assignClientListener(clientListenerRunnable);
        distributedTask.assignAlgorithmExecutor(algorithmExecutor);
        return new DistributedThread(distributedTask, clientListenerRunnable);
    }
}
