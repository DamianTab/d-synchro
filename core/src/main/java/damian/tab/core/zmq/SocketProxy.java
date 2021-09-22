package damian.tab.core.zmq;

import lombok.Builder;
import lombok.Getter;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

@Getter
public class SocketProxy {
    private final ZContext context;
    private final ZMQ.Socket socket;
    private final SocketType type;
    private final String address;
    private final String port;

    @Builder
    public SocketProxy(ZContext context, SocketType type, String address, String port) {
        this.context = context;
        this.type = type;
        this.address = address;
        this.port = port;

        String bindAddress = new StringBuilder()
                .append("tcp://")
                .append(address)
                .append(":")
                .append(port)
                .toString();

        if (type == SocketType.REQ){
            this.socket = context.createSocket(type);
            this.socket.connect(bindAddress);

        } else if (type == SocketType.REP) {
            this.socket = context.createSocket(type);
            this.socket.bind(bindAddress);
        }else {
            this.socket = null;
        }

    }
}
