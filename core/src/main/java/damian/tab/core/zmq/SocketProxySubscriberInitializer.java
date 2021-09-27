package damian.tab.core.zmq;

import org.zeromq.SocketType;
import org.zeromq.ZContext;

public class SocketProxySubscriberInitializer {
    public static SocketProxy createSubscriber(ZContext zContext, String address){
        return SocketProxy.builder()
                .context(zContext)
                .address(address)
                .type(SocketType.SUB)
                .build();
    }
}
