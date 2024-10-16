package ManagementComunication.communicationCSV.Service;

import ManagementComunication.communicationCSV.DTO.JsonDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
        rabbitTemplate.convertAndSend("csvValues", message);
    }
}
