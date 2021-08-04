package com.example.purchasehistory.account;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/account")
public class AccountController {
  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @Autowired
  AccountRepository accountRepository;

  @GetMapping
  public List<Account> getAccounts() {
    return accountService.getAccounts();
  }

  @PostMapping
  public void postNewAccount(@RequestBody Account account) {
    accountService.addNewAccount(account);
  }

  @DeleteMapping(path = "{accountId}")
  public void deleteAccount(@PathVariable("accountId") Long accountId) {
    accountService.deleteAccount(accountId);
  }

  @PutMapping(path = "{accountId}")
  public ResponseEntity<Account> updateAccount(@PathVariable("accountId") Long accountId,
      @RequestBody Account account) {
    Optional<Account> accountData = accountRepository.findById(accountId);
    if (accountData.isPresent()) {
      Account _account = accountData.get();
      _account.setName(account.getName());
      _account.setEmail(account.getEmail());
      _account.setBalance(account.getBalance());
      return new ResponseEntity<>(accountRepository.save(_account), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
