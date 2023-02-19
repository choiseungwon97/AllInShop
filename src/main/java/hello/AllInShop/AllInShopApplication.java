package hello.AllInShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AllInShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllInShopApplication.class, args);
	}

}
