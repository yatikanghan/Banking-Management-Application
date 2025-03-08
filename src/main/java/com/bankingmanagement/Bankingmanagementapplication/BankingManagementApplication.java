package com.bankingmanagement.Bankingmanagementapplication;

import Repository.Customer_Repository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.naming.Context;

@SpringBootApplication(scanBasePackages = {"com.bankingmanagement.Bankingmanagementapplication", "Services"})
public class BankingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingManagementApplication.class, args);
	}

}
