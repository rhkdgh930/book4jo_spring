package com.booksajo.bookPanda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.booksajo.bookPanda")
@EntityScan(basePackages = "com.booksajo.bookPanda.User")
@EnableJpaRepositories(basePackages = "com.booksajo.bookPanda.User")
public class BookPandaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookPandaApplication.class, args);
	}

}
