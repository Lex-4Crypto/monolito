package br.com.lex4crypto.monolito;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class MonolitoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonolitoApplication.class, args);
	}
}
