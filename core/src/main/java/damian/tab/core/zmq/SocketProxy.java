package damian.tab.core.zmq;

import lombok.Builder;
import lombok.Getter;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

@Getter
public class SocketProxy implements AutoCloseable {
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
        this.socket = initializeSocket();
    }

    @Override
    public void close() {
        this.socket.close();
    }

    private ZMQ.Socket initializeSocket() {
        String bindAddress = new StringBuilder()
                .append("tcp://")
                .append(address)
                .append(":")
                .append(port)
                .toString();

        ZMQ.Socket socket = null;
        if (type == SocketType.REQ || type == SocketType.SUB) {
            socket = context.createSocket(type);
            socket.connect(bindAddress);
            if (type == SocketType.SUB){
                socket.subscribe("".getBytes());
            }
        } else if (type == SocketType.REP || type == SocketType.PUB) {
            socket = context.createSocket(type);
            socket.bind(bindAddress);
        }
        return socket;
    }


}
