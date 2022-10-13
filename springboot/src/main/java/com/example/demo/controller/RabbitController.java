package com.example.demo.controller;
import com.example.demo.entity.MessageInfo;
import com.example.demo.service.RabbitMQService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/RabbitMQ")
public class RabbitController {
    @Resource
    private RabbitMQService rabbitMQService;


    @GetMapping("/sendbroad")
    public void broad(){
        MessageInfo messageInfo=new MessageInfo();
        String msg="这是来自管理员的群发的消息!";
        messageInfo.setMsg(msg);
        messageInfo.setFrom("admin");
        rabbitMQService.sendBroad(messageInfo);
    }

    @GetMapping("sendone/{toName}")
    public void one(@PathVariable String toName){
        MessageInfo messageInfo=new MessageInfo();
        String msg="这是来自管理员单发给你的消息!";
        messageInfo.setMsg(msg);
        messageInfo.setFrom("admin");
        rabbitMQService.sendOne(messageInfo,toName);
    }
}
