package demo.springrabbitmq.delay;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class ProducerConfig {

    /**
     * 定义死信队列相关信息
     */
    public final static String deadQueueName = "dead_queue";
    public final static String deadRoutingKey = "dead_routing_key";
    public final static String deadExchangeName = "dead_exchange";
    /**
     * 死信队列 交换机标识符
     */
    public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";


    // fanout 交换机
    public static String EXCHANGE_NAME = "fanoutExchange";
    // 邮件队列
    public static final String FANOUT_EMAIL_QUEUE = "fanout_email_queue";

    // 短信队列
    public  static final String FANOUT_SMS_QUEUE = "fanout_sms_queue";
    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    ////2 如果有队列延时不同，必须定制多个队列绑定，因为队列消费是从顶端开始丢弃到死信队列，如果前一条消息过期时间1个小时，第二条30分钟，这样也只会等待第一条1小时的队列丢弃后才丢弃第二条30分钟过期的队列
    //	//所以需要保证同一队列过期时间是一致 PS 可以通过设置消息的expiration字段或者x-message-ttl属性来设置过期时间，两者是一样的效果，都设置了过期时间取两者的最小值
    @Bean
    Queue fanOutEamilQueue() {

//        return QueueBuilder.durable(FANOUT_EMAIL_QUEUE)
//                .withArgument("x-dead-letter-exchange", "exchange.delay")
//                .withArgument("x-dead-letter-routing-key", "queue.delay")
//                .build();
        // 将普通队列绑定到死信队列交换机上
        Map<String, Object> args = new HashMap<>(2);
        args.put(DEAD_LETTER_QUEUE_KEY, deadExchangeName);
        args.put(DEAD_LETTER_ROUTING_KEY, deadRoutingKey);
        //x-dead-letter-exchange代表消息过期后，消息要进入的交换机，这里配置的是dead_exchange，也就是死信交换机，
        // x-dead-letter-routing-key是配置消息过期后，进入死信交换机的dead_routing_key,跟发送消息的routing-key一个道理，根据这个key将消息放入不同的队列
        // 设置消息的过期时间， 单位是毫秒
        args.put("x-message-ttl", 3000);
        // 是否持久化
        boolean durable = true;
        // 仅创建者可以使用的私有队列，断开后自动删除
        boolean exclusive = false;
        // 当所有消费客户端连接断开后，是否自动删除队列
        boolean autoDelete = false;
        return new Queue(FANOUT_EMAIL_QUEUE, durable, exclusive, autoDelete, args);
    }
    // 2.定义短信队列
    @Bean
    public Queue fanOutSmsQueue() {
        return new Queue(FANOUT_SMS_QUEUE);
    }

    // 2.定义交换机
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    // 3.队列与交换机绑定邮件队列
    @Bean
    Binding bindingExchangeEamil(Queue fanOutEamilQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanOutEamilQueue).to(fanoutExchange);
    }

    // 4.队列与交换机绑定短信队列
    @Bean
    Binding bindingExchangeSms(Queue fanOutSmsQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanOutSmsQueue).to(fanoutExchange);
    }

    /**
     * 配置死信队列
     *
     * @return
     */
    @Bean
    public Queue deadQueue() {
        Queue queue = new Queue(deadQueueName, true);
        return queue;
    }

    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(deadExchangeName);
    }

    @Bean
    public Binding bindingDeadExchange(Queue deadQueue, DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(deadRoutingKey);
    }


}
