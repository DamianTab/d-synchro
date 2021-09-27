package damian.tab.core.zmq;

import damian.tab.core.config.EnvironmentProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zeromq.SocketType;
import org.zeromq.ZContext;

@Service
@RequiredArgsConstructor
public class SocketProxyDelivererService {

    private final EnvironmentProperties properties;

    public SocketProxy createPublisher(ZContext zContext){
        return SocketProxy.builder()
                .context(zContext)
                .address(properties.getPublisherAddress())
                .type(SocketType.PUB)
                .build();
    }

    public SocketProxy createPortMapperRequester(ZContext zContext){
        return SocketProxy.builder()
                .context(zContext)
                .address(properties.getPortMapperAddress())
                .type(SocketType.REQ)
                .build();
    }

    public SocketProxy createPortMapperReplayer(ZContext zContext){
        return SocketProxy.builder()
                .context(zContext)
                .address(properties.getPortMapperAddress())
                .type(SocketType.REP)
                .build();
    }
}
