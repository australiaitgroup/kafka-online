package com.example.kafka.purchase.model;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseRequest {
  private long purchaseId;
  private List<OrderItem> items;
}
