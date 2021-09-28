package damian.tab.core.zmq;

import lombok.Builder;
import lombok.Getter;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.*;

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


    public void send(Object object) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean lol = this.socket.send(bytes);
    }

    public Object receive() {
        Object object = null;
        byte[] bytes = this.socket.recv();
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            object = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
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

    private String initializeAddress(ZMQ.Socket zmqSocket, String address){
        String port = address.substring(address.lastIndexOf(':') + 1);
        return port.equals("0") ? zmqSocket.getLastEndpoint() : address;
    }


}
