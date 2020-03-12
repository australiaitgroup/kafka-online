package com.example.kafka.purchase.service;

import jr.cloud.practice.PurchaseEventsAvro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseEventProducer {

  private final KafkaTemplate<Long, PurchaseEventsAvro> kafkaTemplate;

  public void send(long eventId, PurchaseEventsAvro event) {
    ProducerRecord<Long, PurchaseEventsAvro> record
        = new ProducerRecord<>(kafkaTemplate.getDefaultTopic(),
        null,
        eventId,
        event);

    kafkaTemplate.send(record).addCallback(new ListenableFutureCallback<SendResult<Long, PurchaseEventsAvro>>() {
      @Override
      public void onFailure(Throwable ex) {
        log.error(ex.getMessage(), ex);
      }

      @Override
      public void onSuccess(SendResult<Long, PurchaseEventsAvro> result) {
        int partition = result.getRecordMetadata().partition();
        long offset = result.getRecordMetadata().offset();
        log.info("sent event in partition: {} with offset: {}", partition, offset);
      }
    });
  }
}
