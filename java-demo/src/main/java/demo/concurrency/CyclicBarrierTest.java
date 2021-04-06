package demo.concurrency;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//CyclicBarrier(循环路障):维护一个计数器，等待这个CyclicBarrier的线程必须等到计数器达到某个值时，才可以继续。
// 好比整个公司的人员利用周末时间集体去郊游一样，先各自从家出发到公司集合，再同时出发到公园游玩，
// 在指定地点集合后再同时开始就餐。
public class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CyclicBarrier cb = new CyclicBarrier(3);
        for(int i=0;i<3;i++){
            Runnable runnable = new Runnable(){
                    public void run(){
                    try {
                        Thread.sleep((long)(Math.random()*10000));    
                        System.out.println("线程" + Thread.currentThread().getName() + 
                                "即将到达集合地点1，当前已有" + (cb.getNumberWaiting()+1) + "个已经到达，" + (cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));                        
                        cb.await();

                        Thread.sleep((long)(Math.random()*10000));    
                        System.out.println("线程" + Thread.currentThread().getName() + 
                                "即将到达集合地点2，当前已有" + (cb.getNumberWaiting()+1) + "个已经到达，" + (cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));
                        cb.await();    
                        Thread.sleep((long)(Math.random()*10000));    
                        System.out.println("线程" + Thread.currentThread().getName() + 
                                "即将到达集合地点3，当前已有" + (cb.getNumberWaiting() + 1) + "个已经到达，" + (cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));                        
                        cb.await();                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                
                }
            };
            service.execute(runnable);
        }
        service.shutdown();
    }
}