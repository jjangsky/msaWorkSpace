package com.example.orderservice.messagequeue;

import com.example.orderservice.dto.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderDto send(String topic, OrderDto orderDto){
        // 주문 서비스가 들어오면 JSON 포맷으로 변경
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";

        // 전달할 때 String값으로 전달을 하고 그 String 값의 모양이 Json 형태로 변환
        try{
            jsonInString = mapper.writeValueAsString(orderDto);
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
        kafkaTemplate.send(topic, jsonInString);
        log.info("kafka producer sent data from the order MicroService: " + orderDto);

        return orderDto;
    }
}
