package ro.tuc.ds2020.services;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ro.tuc.ds2020.dtos.builders.JsonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(JsonDto message) {
        rabbitTemplate.convertAndSend("deviceToMonitor", message);
    }
}
