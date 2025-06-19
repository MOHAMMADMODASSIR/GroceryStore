package com.grocery.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class GroceryStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryStoreApplication.class, args);
	}

}
