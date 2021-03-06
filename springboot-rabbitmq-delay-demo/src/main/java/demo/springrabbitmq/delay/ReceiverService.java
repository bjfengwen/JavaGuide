package demo.springrabbitmq.delay;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ReceiverService {

    @RabbitListener(queues = ProducerConfig.deadQueueName)
    public void receiveBarQueue(String message) {
        System.out.println("收到消息" + message + ",收到时间" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
