package design.pattern.observer;

/**
 * // 短信日志通知
 * @Description
 * @Author fengwen
 * @Date 2020-10-27
 */
public class HipiaoMSM implements ITicketObserver {


    @Override
    public void onBuyTicketOver(ITicketObservable sender, String args) {
        System.out.println(" 短信日志记录：购票成功 : $ticket <br>" );
    }
}

