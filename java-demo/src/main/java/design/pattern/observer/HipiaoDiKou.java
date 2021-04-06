package design.pattern.observer;

/**
 * @Description
 * @Author fengwen
 * @Date 2020-10-27
 */
public class HipiaoDiKou implements ITicketObserver {
    @Override
    public void onBuyTicketOver(ITicketObservable sender, String args) {
        System.out.println("赠送抵扣卷：购票成功 : $ticket 赠送 10 元抵扣卷 1 张。 <br>");
    }
}


