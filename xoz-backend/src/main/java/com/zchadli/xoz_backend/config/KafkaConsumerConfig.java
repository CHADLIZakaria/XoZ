package com.zchadli.xoz_backend.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zchadli.xoz_backend.dto.GameStartDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, GameStartDto> consumerFactory() {
        JsonDeserializer<GameStartDto> deserializer = new JsonDeserializer<>(GameStartDto.class);
        deserializer.addTrustedPackages("com.zchadli.xoz_backend.dto");
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", deserializer);
        props.put("group.id", "game-start-group");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GameStartDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GameStartDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
