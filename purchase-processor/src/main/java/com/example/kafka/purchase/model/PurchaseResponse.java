package com.example.kafka.purchase.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseResponse {
  int status;
}
