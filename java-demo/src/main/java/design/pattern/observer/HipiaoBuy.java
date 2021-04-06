package design.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author fengwen
 * @Date 2020-10-27
 */
public class HipiaoBuy implements ITicketObservable {// 实现主题接口（被观察者）

    @Override
    public void addObserver(ITicketObserver observer) { // 添加 N 个通知
        list.add(observer);
    }

    private List<ITicketObserver> list = new ArrayList<>(); // 通知数组（观察者）

    public void buyTicket(String ticket){ // 购票核心类，处理购票流程
        // TODO 购票逻辑
        System.out.println("购票开始了....");
        System.out.println("购票成功....");
        // 循环通知，调用其 onBuyTicketOver 实现不同业务逻辑
        list.forEach(iTicket -> {
            iTicket.onBuyTicketOver(this, ticket);
        });

    }


}
