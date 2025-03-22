package com.bankingmanagement.Bankingmanagementapplication;

import MyModel.Account;
import MyModel.FixedDeposit;
import Repository.Customer_Repository;
import Services.Customer_Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import java.time.LocalDate;
import java.util.List;


@SpringBootApplication(scanBasePackages = {"com.bankingmanagement.Bankingmanagementapplication", "Services"})
@EnableMongoRepositories("Repository")
@EnableScheduling
public class BankingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingManagementApplication.class, args);
	}

}
@Component
class ScheduledTasks {

	private final Customer_Service customer_Service;

	public ScheduledTasks(Customer_Service customer_Service) {
		this.customer_Service = customer_Service;
	}

	@Scheduled(fixedRate = 60000) // Every 1 minute
	public void taskEveryMinute() {
		List<FixedDeposit> fixedDeposits=customer_Service.findallfixeddeposit();
		LocalDate today = LocalDate.now();
		for(FixedDeposit fd:fixedDeposits){
			if (fd.getStatus().equals("Active")) {
				if (!today.isBefore(fd.getMaturityDate())) {
					Account account=customer_Service.findaccountrecodebyid(fd.getAccountId());
					customer_Service.maturedfixeddeposit(fd,account);
				}
			}
		}

	}
}