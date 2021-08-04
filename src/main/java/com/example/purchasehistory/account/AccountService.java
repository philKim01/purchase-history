package com.example.purchasehistory.account;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public List<Account> getAccounts() {
    return accountRepository.findAll();
  }

  public void addNewAccount(Account account) {
    Optional<Account> accountByEmail = accountRepository.findAccountByEmail(account.getEmail());

    if (accountByEmail.isPresent()) {
      throw new IllegalStateException("email taken");
    } else {
      accountRepository.save(account);
    }
  }

  public void deleteAccount(Long id) {
    boolean exists = accountRepository.existsById(id);

    if (!exists) {
      throw new IllegalStateException("Account with id " + id + "does not exist");
    }
    accountRepository.deleteById(id);
  }

  @Transactional
  public void updateAccount(Long id, String name, String email, Double balance) {
    Account account = accountRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("account with id " + id + " does not exist"));

    if (name != null && name.length() > 0 && !Objects.equals(account.getName(), name)) {
      account.setName(name);
    }

    if (email != null && email.length() > 0 && !Objects.equals(account.getEmail(), email)) {
      Optional<Account> accountOptional = accountRepository.findAccountByEmail(email);
      if (accountOptional.isPresent()) {
        throw new IllegalStateException("email taken");
      }
      account.setEmail(email);
    }
  }
}
