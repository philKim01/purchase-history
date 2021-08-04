package com.example.purchasehistory;

import java.util.List;

import com.example.purchasehistory.account.Account;
import com.example.purchasehistory.account.AccountRepository;
import com.example.purchasehistory.purchase.Purchase;
import com.example.purchasehistory.purchase.PurchaseRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PurchaseHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchaseHistoryApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AccountRepository accountRepository,
			PurchaseRepository purchaseRepository) {
		return args -> {
			Account phil = new Account("Phil", "Phillip.Kim.3112@gmail.com");

			Purchase food = new Purchase("food", "Mcdonalds", 15.00, phil);
			Purchase rp = new Purchase("Riot Points", "For League", 10.00, phil);

			phil.addPurchase(food);
			phil.addPurchase(rp);

			Double balance = phil.getBalance();
			List<Purchase> purchases = phil.getPurchases();
			for (int i = 0; i < purchases.size(); i++) {
				balance += purchases.get(i).getCost();
			}
			phil.setBalance(balance);
			accountRepository.save(phil);
			purchaseRepository.saveAll(List.of(food, rp));

		};
	}

}
