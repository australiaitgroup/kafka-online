package com.example.inventory.service;

import jr.cloud.practice.PurchaseEventsAvro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PurchaseConsumer {

  @KafkaListener(topics = "${spring.kafka.template.default-topic}")
  public void process(PurchaseEventsAvro avroEvent,
                      @Header(KafkaHeaders.OFFSET) long offset,
                      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) String partition) {
    log.info("received in partition:{} at offset: {} with message: {}",
        partition, offset, avroEvent.toString());

    // do whatever you want
  }
}
