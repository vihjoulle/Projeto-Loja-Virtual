package vaja.mentoria.lojavirtual2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "vaja.mentoria.lojavirtual2")
@ComponentScan(basePackages = {"vaja.*"})
@EnableJpaRepositories(basePackages = {"vaja.mentoria.lojavirtual2.repository"})
@EnableTransactionManagement
public class LojaVirtualMentoria2Application {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualMentoria2Application.class, args);
	}

}
