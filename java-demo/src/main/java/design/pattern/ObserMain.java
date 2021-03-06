package design.pattern;

import design.pattern.observer.HipiaoBuy;
import design.pattern.observer.HipiaoDiKou;
import design.pattern.observer.HipiaoMSM;

/**
 场景描述：

 * 哈票以购票为核心业务 ( 此模式不限于该业务 ) ，但围绕购票会产生不同的其他逻辑，如：

 * 1 、购票后记录文本日志
 * 2 、购票后记录数据库日志
 * 3 、购票后发送短信
 * 4 、购票送抵扣卷、兑换卷、积分

 * 5 、其他各类活动等
 * 传统解决方案 :

 * 在购票逻辑等类内部增加相关代码，完成各种逻辑。

 *

 * 存在问题：

 * 1 、一旦某个业务逻辑发生改变，如购票业务中增加其他业务逻辑，需要修改购票核心文件、甚至购票流程。

 * 2 、日积月累后，文件冗长，导致后续维护困难。

 *

 * 存在问题原因主要是程序的 " 紧密耦合 " ，使用观察模式将目前的业务逻辑优化成 " 松耦合 " ，达到易维护、易修改的目的，

 * 同时也符合面向接口编程的思想。

 *

 * 观察者模式典型实现方式：

 * 1 、定义 2 个接口：观察者（通知）接口、被观察者（主题）接口

 * 2 、定义 2 个类，观察者对象实现观察者接口、主题类实现被观者接口

 * 3 、主题类注册自己需要通知的观察者

 * 4 、主题类某个业务逻辑发生时通知观察者对象，每个观察者执行自己的业务逻辑。
 */
public class ObserMain {
    public static void main(String[] args) {
        //============================ 用户购票 ====================

        HipiaoBuy $buy = new HipiaoBuy ();

        $buy.addObserver ( new HipiaoMSM() ); // 根据不同业务逻辑加入各种通知

        $buy.addObserver ( new HipiaoDiKou() );

        // 购票
        $buy.buyTicket ( " 一排一号 " );


    }
}
