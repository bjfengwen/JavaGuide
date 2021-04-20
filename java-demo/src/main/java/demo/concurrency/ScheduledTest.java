package demo.concurrency;


import utils.DateTimeUtil;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ScheduledTest {
    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService executorService= Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
               // t.setName("testScheduled");
               // t.setDaemon(true);
                return t;
            }
        });
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("run.....");
//               // executorService.execute(this);
//            }
//        });
        long initialDelay2 =2;
        long delay2 = 1;
        // 从现在开始2秒钟之后，每隔2秒钟执行一次job2
        System.out.println("main"+ DateTimeUtil.formatDate(new Date(), DateTimeUtil.FORMAT_SECOND));

        executorService.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("run....."+ DateTimeUtil.formatDate(new Date(),DateTimeUtil.FORMAT_SECOND));
                    }
                }, initialDelay2, delay2, TimeUnit.SECONDS);
        synchronized (ScheduledTest.class){
            ScheduledTest.class.wait();
        }
    }

}
