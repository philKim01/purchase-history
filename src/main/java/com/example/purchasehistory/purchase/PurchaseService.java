package com.example.purchasehistory.purchase;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseService {

  private final PurchaseRepository purchaseRepository;

  @Autowired
  public PurchaseService(PurchaseRepository purchaseRepository) {
    this.purchaseRepository = purchaseRepository;
  }

  public List<Purchase> getPurchases() {
    return purchaseRepository.findAll();
  }

  public void addNewPurchase(Purchase purchase) {
    purchaseRepository.save(purchase);
  }

  public void deletePurchase(Long id) {
    boolean exists = purchaseRepository.existsById(id);

    if (!exists) {
      throw new IllegalStateException("Purchase with id " + id + "does not exists");
    }
    purchaseRepository.deleteById(id);
  }

  @Transactional
  public void updatePurchase(Long id, String name, String description, Double cost) {

    Purchase purchase = purchaseRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("purchase with id " + id + " does not exist"));

    if (name != null && name.length() > 0 && !Objects.equals(purchase.getName(), name)) {
      purchase.setName(name);
    }

    if (description != null && description.length() > 0 && !Objects.equals(purchase.getName(), description)) {
      purchase.setDescription(description);
    }

    if (cost != null && !Objects.equals(purchase.getCost(), cost)) {
      purchase.setCost(cost);
    }

  }

}
