package com.example.purchasehistory.purchase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.purchasehistory.account.Account;
import com.example.purchasehistory.account.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path = "/api/purchase")
public class PurchaseController {

  private final PurchaseService purchaseService;
  private final AccountRepository accountRepository;

  @Autowired
  public PurchaseController(PurchaseService purchaseService, AccountRepository accountRepository) {
    this.purchaseService = purchaseService;
    this.accountRepository = accountRepository;
  }

  @Autowired
  PurchaseRepository purchaseRepository;

  @GetMapping
  public List<Purchase> getPurchases() {
    return purchaseService.getPurchases();
  }

  @PostMapping(path = "{accountId}")
  public ResponseEntity<Purchase> postNewPurchase(@PathVariable("accountId") Long accountId,
      @RequestBody Purchase purchase) {
    Optional<Account> accountData = accountRepository.findById(accountId);

    if (!accountData.isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {

      purchase.setMadeAt(LocalDateTime.now());

      purchaseService.addNewPurchase(purchase);

      Account account = accountData.get();

      purchase.setAccount(account);
      account.addPurchase(purchase);

      Double balance = account.getBalance();
      balance += purchase.getCost();

      account.setBalance(balance);

      accountRepository.save(account);
      purchaseRepository.save(purchase);
      return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

  }

  @DeleteMapping(path = "{purchaseId}")
  public void deletePurchase(@PathVariable("purchaseId") Long purchaseId) {

    Optional<Purchase> purchaseData = purchaseRepository.findById(purchaseId);

    Purchase deletingPurchase = purchaseData.get();

    // This portion is very spaghetti because I had getAccount() return the
    // accountId so that it wouldnt return an infinite loop when someone was to hit
    // the GET route
    Long accountId = deletingPurchase.getAccount();
    Optional<Account> accountData = accountRepository.findById(accountId);

    Account adjustingBalanceAccount = accountData.get();

    Double adjustingBalance = adjustingBalanceAccount.getBalance();

    adjustingBalance -= deletingPurchase.getCost();

    adjustingBalanceAccount.setBalance(adjustingBalance);

    purchaseService.deletePurchase(purchaseId);
  }

  @PutMapping(path = "{purchaseId}")
  public ResponseEntity<Purchase> updatePurchase(@PathVariable("purchaseId") Long purchaseId,
      @RequestBody Purchase purchase) {
    Optional<Purchase> purchaseData = purchaseRepository.findById(purchaseId);

    if (purchaseData.isPresent()) {
      Purchase _purchase = purchaseData.get();
      _purchase.setName(purchase.getName());
      _purchase.setDescription(purchase.getDescription());
      return new ResponseEntity<>(purchaseRepository.save(_purchase), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

}
