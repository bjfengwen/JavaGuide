package demo.concurrency.threadPoolExecutorNote;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author fengwen
 * @Date 2020-10-28
 */
public class ThreadPoolMain {
    public static void main(String[] args) {
      //    ThreadPoolExecutorNote t = new
       // java.util.concurrent.ThreadPoolExecutorNote()
     //  java.util.concurrent.AbstractExecutorServiceNote()
        int PAGE_SIZE = 10000;
        ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(10, 15, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("my-thread-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
