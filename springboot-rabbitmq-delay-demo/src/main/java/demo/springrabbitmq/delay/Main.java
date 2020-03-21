package demo.springrabbitmq.delay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication
@ComponentScan(basePackages = "demo.springrabbitmq.delay")
public class Main implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    SenderService senderService;

    @Override
    public void run(String... strings) throws Exception {

            String bar = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            System.out.println("发送消息,发送时间" + bar);
            senderService.sendDelayMessage(bar);

    }

}
