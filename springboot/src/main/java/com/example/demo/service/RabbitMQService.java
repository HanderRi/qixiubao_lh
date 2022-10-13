package com.example.demo.service;


import com.example.demo.entity.MessageInfo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class RabbitMQService {

    @RabbitListener(queues = "fanout_queue")
    @RabbitHandler
    public void getFanOutMessage(Message message, Channel channel) throws IOException {
        System.out.println("收到广播消息："+message);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, false);
        String id = message.getMessageProperties().getCorrelationId();
        System.out.println("收到Id: " + id+"\n");
    }

    @RabbitListener(queues = "userA")
    @RabbitHandler
    public void getAMessage(Message message, Channel channel) throws IOException {
        System.out.println("收到给a的消息："+message);
        String id = message.getMessageProperties().getCorrelationId();
        System.out.println("收到Id: " + id+"\n");
        try {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            //丢弃这条消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

    @RabbitListener(queues = "userB")
    @RabbitHandler
    public void getBMessage(Message message, Channel channel) throws IOException {
        System.out.println("收到给b的消息："+message);
        String id = message.getMessageProperties().getCorrelationId();
        System.out.println("收到Id: " + id+"\n");
        try {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            //丢弃这条消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    MessagePostProcessor correlationIdProcessor;


    public void sendBroad(MessageInfo message) {
        CorrelationData correlationData = new CorrelationData();
        String uuid = UUID.randomUUID().toString();
        correlationData.setId(uuid);
        System.out.println("分配ID：" + correlationData.getId());
        System.out.println("发送广播消息");
        rabbitTemplate.convertAndSend("fanout_exchange", "", message,correlationIdProcessor,correlationData);
    }

    public void sendOne(MessageInfo message, String toName) {
        CorrelationData correlationData = new CorrelationData();
        String uuid = UUID.randomUUID().toString();
        correlationData.setId(uuid);
        System.out.println("分配ID：" + correlationData.getId());
        System.out.println("发送给"+toName+"，消息内容："+ message);
        rabbitTemplate.convertAndSend("topic_exchange", toName, message,correlationIdProcessor,correlationData);
    }
}
