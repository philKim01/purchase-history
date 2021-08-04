// package com.example.purchasehistory.purchase;

// import java.util.List;

// // import com.example.purchasehistory.account.Account;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class PurchaseConfig {
// @Bean
// CommandLineRunner PurchaseRunner(PurchaseRepository purchaseRepository) {
// return args -> {

// Purchase food = new Purchase("Mcdonald's", "McChicken", 1.00);
// Purchase rp = new Purchase("Riot Points", "For League", 15.00);

// purchaseRepository.saveAll(List.of(food, rp));
// };
// }
// }
