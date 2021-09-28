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

    @Builder
    public SocketProxy(ZContext context, SocketType type, String address) {
        this.context = context;
        this.type = type;
        this.socket = initializeSocket(address);
        this.address = initializeAddress(this.socket, address);
    }

    @Override
    public void close() {
        this.socket.close();
    }

    private ZMQ.Socket initializeSocket(String address) {
        ZMQ.Socket zmqSocket = null;
        if (type == SocketType.REQ || type == SocketType.SUB) {
            zmqSocket = context.createSocket(type);
            zmqSocket.connect(address);
            if (type == SocketType.SUB) {
                zmqSocket.subscribe("".getBytes());
            }
        } else if (type == SocketType.REP || type == SocketType.PUB) {
            zmqSocket = context.createSocket(type);
            zmqSocket.bind(address);
        }
        return zmqSocket;
    }

    private String initializeAddress(ZMQ.Socket zmqSocket, String address) {
        String port = address.substring(address.lastIndexOf(':') + 1);
        return port.equals("0") ? zmqSocket.getLastEndpoint() : address;
    }
}
