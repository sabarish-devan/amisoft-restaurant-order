package com.digi.order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import static com.digi.order.constant.RestaurantOrderConstant.EXCHANGE_NAME;
import static com.digi.order.constant.RestaurantOrderConstant.QUEUE_NAME;

@Configuration
public class OrderCreatedEventConfig {

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }


    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        template.setMessageConverter(jsonConverter);
        return template;
    }

}
