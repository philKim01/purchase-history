package com.example.purchasehistory.purchase;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.example.purchasehistory.account.Account;

@Entity(name = "purchases")
@Table(name = "purchases")
public class Purchase {
  @Id
  @SequenceGenerator(name = "purchase_sequence", sequenceName = "purchase_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_sequence")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "cost")
  private Double cost;

  @Column(name = "madeAt")
  private LocalDateTime madeAt;

  @ManyToOne
  @JoinColumn(name = "account_id", referencedColumnName = "account_id", foreignKey = @ForeignKey(name = "account_id"))
  private Account account;

  // Constructors
  public Purchase() {
  }

  public Purchase(String name, String description, Double cost, Account account) {
    this.name = name;
    this.description = description;
    this.cost = cost;
    this.account = account;
    this.madeAt = LocalDateTime.now();
  }

  public Purchase(String name, Double cost, Account account) {
    this.name = name;
    this.cost = cost;
    this.account = account;
    this.madeAt = LocalDateTime.now();
  }

  public Purchase(String name, String description, Double cost) {
    this.name = name;
    this.description = description;
    this.cost = cost;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public LocalDateTime getMadeAt() {
    return madeAt;
  }

  public void setMadeAt(LocalDateTime madeAt) {
    this.madeAt = madeAt;
  }

  public Long getAccount() {
    return account.getId();
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  @Override
  public String toString() {
    return "Purchase{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", cost="
        + cost + ", madeAt=" + madeAt + '}';
  }
}
