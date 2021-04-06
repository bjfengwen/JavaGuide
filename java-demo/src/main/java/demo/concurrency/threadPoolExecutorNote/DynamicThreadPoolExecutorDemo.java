package demo.concurrency.threadPoolExecutorNote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 动态修改线程池
 * https://mp.weixin.qq.com/s/9HLuPcoWmTqAeFKa1kj-_A
 * https://blog.csdn.net/qq_19201215/article/details/100665680
 * @Description
 * @Author fengwen
 * @Date 2020-10-29
 */
public class DynamicThreadPoolExecutorDemo {
    public static void main(String[] args) throws  Exception {
        dynamicModifyExecutor1();
    }
    private static ThreadPoolExecutor build() {
        return new ThreadPoolExecutor(2, 5,
                60, TimeUnit.SECONDS,
                new ResizableCapacityLinkedBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("threadPool-%d").build());
    }
    public static void runTask(ThreadPoolExecutor executor){
        for (int i = 0; i < 15; i++) {
            executor.submit(()->{
                threadPoolStatus(executor,"创建任务");
                try{
                    TimeUnit. SECONDS. sleep(10);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    private static void dynamicModifyExecutor1() throws InterruptedException {
        ThreadPoolExecutor executor = build();
        runTask(executor);
        threadPoolStatus(executor, "改变之前");
        TimeUnit.SECONDS.sleep(1);
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
        int prest =  executor.prestartAllCoreThreads();
        System.out.println("核心线程数预热数量:"+prest);
        ResizableCapacityLinkedBlockingQueue queue = (ResizableCapacityLinkedBlockingQueue)
                executor.getQueue();
        queue.setCapacity(100);
        threadPoolStatus(executor, "改变之后");
        TimeUnit.SECONDS.sleep(100);
        threadPoolStatus(executor,"100秒后");
        executor.shutdown();
    }
    private static void dynamicModifyExecutor2() throws InterruptedException {
        ThreadPoolExecutor executor = build();
        runTask(executor);
        threadPoolStatus(executor,"改变之前");
//        TimeUnit. SECONDS. sleep(1);
//        executor.setCorePoolSize(10);
//        executor.setMaximumPoolSize(12);
//        int prest =  executor.prestartAllCoreThreads();
//        System.out.println("核心线程数预热数量:"+prest);
//        threadPoolStatus(executor,"改变之后");
        TimeUnit.SECONDS.sleep(100);
        threadPoolStatus(executor,"100秒后");
        executor.shutdown();
    }
    public  static String getNow(){
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
    private static void threadPoolStatus(ThreadPoolExecutor executor, String name) {
        ResizableCapacityLinkedBlockingQueue queue = (ResizableCapacityLinkedBlockingQueue) executor.getQueue();

        System.out.println(getNow()+""+Thread.currentThread().getName() + "-" + name + "-" +
                "核心线程数：" + executor.getCorePoolSize() +
                " 活动线程数：" + executor.getActiveCount() +
                " 最大线程数：" + executor.getMaximumPoolSize() +
                " 线程池活跃度：" + divide(executor.getActiveCount(), executor.getMaximumPoolSize()) +
                " 任务完成数：" + executor.getCompletedTaskCount() +
                " 队列大小：" + (queue.size() + queue.remainingCapacity()) +
                " 当前排队线程数：" + queue.size() +
                " 队列剩余大小：" + queue.remainingCapacity() +
                " 队列使用度：" + divide(queue.size(), queue.size() + queue.remainingCapacity())
        )  ;
    }

    private static String divide(int numl, int num2) {
        return String.format("%1.2f%%", Double.parseDouble( numl + "")/Double.parseDouble( num2 + "")*180);
    }



}
