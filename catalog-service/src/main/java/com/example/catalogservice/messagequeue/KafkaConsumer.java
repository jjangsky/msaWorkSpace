package com.example.catalogservice.messagequeue;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {
    CatalogRepository repository;

    @Autowired
    public KafkaConsumer(CatalogRepository repository){
        this.repository = repository;
    }

    // 설정한 토픽에 데이터가 전달되면 실행
    @KafkaListener(topics = "example-catalog-topic")
        public void updateQty(String kafkaMessage) {

        log.info("kafka message : -> " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();

        // 카프카로 전달된 데이터는 문자열로 직렬화 필요
        ObjectMapper mapper = new ObjectMapper();
        try{
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
        CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
        if(entity != null){
            entity.setStock(entity.getStock() - (Integer)map.get("qty"));
            repository.save(entity);
        }



    }

}
