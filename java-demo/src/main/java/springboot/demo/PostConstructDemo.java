package springboot.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description
 * @Author fengwen
 * @Date 2020-10-29
 */
@Component
public class PostConstructDemo {
    @Autowired
    private BBB bbb;
    public PostConstructDemo() {
        System.out.println("此时b还未被注入: b = " + bbb);
    }

    @PostConstruct
    private void init() {
        System.out.println("@PostConstruct将在依赖注入完成后被自动调用: b = " + bbb);

    }
}
