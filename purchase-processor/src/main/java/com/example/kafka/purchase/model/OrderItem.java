package com.example.kafka.purchase.model;

import lombok.Data;

@Data
public class OrderItem {
  private long itemId;
  private int amount;
}
