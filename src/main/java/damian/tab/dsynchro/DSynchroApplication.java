package damian.tab.dsynchro;

import damian.tab.dsynchro.proto.InitRequestMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DSynchroApplication {

	public static void main(String[] args) {
		SpringApplication.run(DSynchroApplication.class, args);
		InitRequestMessage lol = InitRequestMessage.newBuilder()
				.setAddress("lol")
				.setReady(true)
				.build();
		System.out.println(lol);
	}

}
