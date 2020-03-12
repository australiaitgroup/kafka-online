package com.example.kafka.purchase.controller;

import com.example.kafka.purchase.model.PurchaseRequest;
import com.example.kafka.purchase.model.PurchaseResponse;
import com.example.kafka.purchase.service.PurchaseEventProducer;
import jr.cloud.practice.PurchaseDetailsAvro;
import jr.cloud.practice.PurchaseEventType;
import jr.cloud.practice.PurchaseEventsAvro;
import jr.cloud.practice.PurchaseItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PurchaseController {

  private final PurchaseEventProducer producer;

  @PostMapping(value = "/purchase/submit",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PurchaseResponse submit(@RequestBody PurchaseRequest request) {

    // do business logic here

    PurchaseEventsAvro arvo = PurchaseEventsAvro.newBuilder()
        .setEventType(PurchaseEventType.PURCHASE_SUBMITTED)
        .setPurchaseDetails(PurchaseDetailsAvro.newBuilder()
            .setPurchaseId(request.getPurchaseId())
            .setPurchaseItems(
                request.getItems().stream().map(purchaseItem ->
                  PurchaseItem.newBuilder()
                      .setItemId(purchaseItem.getItemId())
                      .setAmount(purchaseItem.getAmount())
                      .build())
                .collect(Collectors.toList())
            )
            .build())
        .build();

    producer.send(request.getPurchaseId(), arvo);

    return PurchaseResponse.builder()
        .status(200).build();
  }
}
