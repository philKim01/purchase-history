package com.example.purchasehistory.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.example.purchasehistory.purchase.Purchase;

@Entity(name = "accounts")
@Table(name = "accounts")
public class Account {
  @Id
  @SequenceGenerator(name = "account_id", sequenceName = "account_id", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id")
  @Column(name = "account_id")
  private Long id;

  @Column(name = "Holder_Name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;

  @OneToMany(mappedBy = "account", orphanRemoval = true, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
  private final List<Purchase> purchases = new ArrayList<>();

  @Column(name = "Amount_Due")
  private Double balance;

  // Constructors
  public Account() {

  }

  public Account(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.balance = 0.0;
  }

  public Account(String name, String email) {
    this.name = name;
    this.email = email;
    this.balance = 0.0;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public List<Purchase> getPurchases() {
    return purchases;
  }

  public void addPurchase(Purchase purchase) {
    if (!this.purchases.contains(purchase)) {
      purchases.add(purchase);
      purchase.setAccount(this);
    }
  }

  public void removePurchase(Purchase purchase) {
    if (this.purchases.contains(purchase)) {
      this.purchases.remove(purchase);
      purchase.setAccount(null);
    }
  }

  @Override
  public String toString() {
    return "Account{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", balance=" + balance
        + '}';
  }

}
