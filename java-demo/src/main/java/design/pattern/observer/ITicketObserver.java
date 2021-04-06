package design.pattern.observer;

/**
 * @Description
 * @Author fengwen
 * @Date 2020-10-27
 */
/**

 * 观察者接口 ( 通知接口 )
 */


public interface  ITicketObserver {
   void onBuyTicketOver(ITicketObservable sender, String args); // 得到通知后调用的方法
}
