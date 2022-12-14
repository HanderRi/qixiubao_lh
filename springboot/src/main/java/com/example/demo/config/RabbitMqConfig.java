package com.example.demo.config;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RabbitMqConfig {
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessagePropertiesConverter(defaultMessagePropertiesConverter());
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        return rabbitTemplate;
//    }

//    @Bean
//    public MessagePropertiesConverter defaultMessagePropertiesConverter() {
//        DefaultMessagePropertiesConverter messagePropertiesConverter =
//                new DefaultMessagePropertiesConverter();
////        messagePropertiesConverter
////                .setCorrelationIdPolicy(DefaultMessagePropertiesConverter.CorrelationIdPolicy.STRING);
//        return messagePropertiesConverter;
//    }

    @Bean
    public MessagePostProcessor correlationIdProcessor() {

        return new MessagePostProcessor() {

            @Override
            public Message postProcessMessage(Message message, Correlation correlation) {

                MessageProperties messageProperties = message.getMessageProperties();

                if (correlation instanceof CorrelationData) {

                    String correlationId = ((CorrelationData) correlation).getId();
                    messageProperties.setCorrelationId(correlationId);
                }
                return message;
            }

            @Override
            public Message postProcessMessage(Message message) throws AmqpException {

                return message;
            }
        };
    }


    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    //??????fanout?????????????????? ????????????
    @Bean
    public FanoutExchange fanout_exchange() {
        return new FanoutExchange("fanout_exchange", true, false);
    }

    //topic??????????????? ????????????
    @Bean
    public TopicExchange topic_exchange() {
        return new TopicExchange("topic_exchange", true, false);
    }

    //??????????????????
    @Bean
    public Queue fanout_queue() {
        return new Queue("fanout_queue");
    }

    @Bean
    public Queue topic_queue1() {//????????????????????????
        return new Queue("userA");
    }

    @Bean
    public Queue topic_queue2() {
        return new Queue("userB");
    }

    //??????????????????????????????
    @Bean
    public Binding bindingFanout() {
        return BindingBuilder.bind(fanout_queue()).to(fanout_exchange());
    }

    @Bean
    public Binding bindingDirect1() {
        return BindingBuilder.bind(topic_queue1()).to(topic_exchange()).with("usera");
    }

    @Bean
    public Binding bindingDirect2() {
        return BindingBuilder.bind(topic_queue2()).to(topic_exchange()).with("userb");
    }

}
