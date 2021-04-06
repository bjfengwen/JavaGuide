package design.pattern.observer;

/**
 * // 被观察对象接口
 * @Description
 * @Author fengwen
 * @Date 2020-10-27
 */
public interface ITicketObservable {
   void addObserver(ITicketObserver observer); // 提供注册观察者方法


}
