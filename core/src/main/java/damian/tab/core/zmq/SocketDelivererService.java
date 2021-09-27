package damian.tab.core.zmq;

import damian.tab.core.config.EnvironmentProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zeromq.SocketType;
import org.zeromq.ZContext;

@Service
@RequiredArgsConstructor
public class SocketDelivererService {

    private final EnvironmentProperties properties;

    public SocketProxy createPublisher(ZContext zContext){
        return SocketProxy.builder()
                .context(zContext)
                .address(properties.getAddress())
                .type(SocketType.SUB)
                .build();
    }
}