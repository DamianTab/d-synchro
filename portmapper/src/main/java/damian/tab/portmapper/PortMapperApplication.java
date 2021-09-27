package damian.tab.portmapper;

import damian.tab.core.CoreApplication;
import damian.tab.core.config.EnvironmentProperties;
import damian.tab.core.proto.InitRequestMessage;
import damian.tab.core.proto.SynchroMessage;
import damian.tab.core.zmq.SocketProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class PortMapperApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CoreApplication.class, args);
        EnvironmentProperties properties = context.getBean(EnvironmentProperties.class);
        ZContext zContext = context.getBean(ZContext.class);

		InitRequestMessage message = InitRequestMessage.newBuilder()
				.setAddress("lol")
				.build();

        SynchroMessage message1 = SynchroMessage.newBuilder()
                .setProcessID(1)
                .addAllClock(Arrays.asList(1,2,3,4,5))
                .setType(SynchroMessage.MessageType.LOCK_REQ)
                .setObjectID("22")
                .addReceiverProcessID(3)
                .build();

//	REP
//		SocketProxy proxy = SocketProxy.builder()
//				.context(zContext)
//				.address(properties.getAddress())
//				.type(SocketType.REP)
//				.build();
//
//		while (!Thread.currentThread().isInterrupted()) {
//			log.info("Received: [ {} ]", proxy.receive());
//			proxy.send("Hello, world!");
//		}

//		PUB
        SocketProxy proxy = SocketProxy.builder()
                .context(zContext)
                .address(properties.getAddress())
                .type(SocketType.PUB)
                .build();

        while (!Thread.currentThread().isInterrupted()) {
        	log.info("Sending ...");
            proxy.send(message1);
//            proxy.send("Hello, world!");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
