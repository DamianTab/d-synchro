package damian.tab.portmapper;

import damian.tab.core.proto.InitRequestMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortMapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortMapperApplication.class, args);
		InitRequestMessage lol = InitRequestMessage.newBuilder()
				.setAddress("lol")
				.build();
		System.out.println(lol);
	}

}
