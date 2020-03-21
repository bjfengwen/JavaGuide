package demo.springrabbitmq.delay;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SenderService {

    @Autowired
    private RabbitTemplate rabbitMessagingTemplate;

    public void sendDelayMessage(final String bar){
        this.rabbitMessagingTemplate.convertAndSend(ProducerConfig.EXCHANGE_NAME, "", bar.toString());
        //this.rabbitMessagingTemplate.convertAndSend(ProducerConfig.EXCHANGE_NAME, "", bar.toString(), new ExpirationMessagePostProcessor(3000L));
        //
    }

}
