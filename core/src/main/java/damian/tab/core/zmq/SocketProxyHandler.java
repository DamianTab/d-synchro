package damian.tab.core.zmq;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class SocketProxyHandler {

    public void send(SocketProxy socketProxy, Object object) {
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
        socketProxy.getSocket().send(bytes);
    }

    public Object receive(SocketProxy socketProxy) {
        Object object = null;
        byte[] bytes = socketProxy.getSocket().recv();
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            object = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

}
