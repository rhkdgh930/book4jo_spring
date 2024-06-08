package com.booksajo.bookPanda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BookPandaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookPandaApplication.class, args);
	}

}
