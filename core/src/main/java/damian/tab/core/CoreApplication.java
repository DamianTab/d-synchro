package damian.tab.core;

import damian.tab.core.config.EnvironmentProperties;
import damian.tab.core.proto.InitRequestMessage;
import damian.tab.core.zmq.SocketProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

@Slf4j
@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CoreApplication.class, args);
        InitRequestMessage lol = InitRequestMessage.newBuilder()
                .setAddress("lol")
                .setReady(true)
                .build();
        System.out.println(lol);

        EnvironmentProperties properties = context.getBean(EnvironmentProperties.class);
        log.info(properties.getAddress());
        log.info(properties.getPort());

        ZContext zContext = context.getBean(ZContext.class);


        SocketProxy proxy = SocketProxy.builder()
                .context(zContext)
                .address(properties.getAddress())
                .port(properties.getPort())
                .type(SocketType.REQ)
                .build();

        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Sending request ");
            proxy.getSocket().send("WIADOMOSC".getBytes(ZMQ.CHARSET), 0);
            byte[] reply = proxy.getSocket().recv(0);
            System.out.println("Received: [" + new String(reply, ZMQ.CHARSET) + "]");
        }
    }

}
