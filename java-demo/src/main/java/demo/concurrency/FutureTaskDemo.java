package demo.concurrency;

import java.util.concurrent.*;

public class FutureTaskDemo {
    static ExecutorService taskExe = Executors.newFixedThreadPool(10);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> getHttpContent1 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //模拟发起http 请求
                Thread.sleep((long) (Math.random() * 1000));
                return "1";
            }
        };

        Callable<String> getHttpContent2 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //模拟发起http 请求
                Thread.sleep((long) (Math.random() * 1000));
                return "2";
            }
        };
        FutureTask<String> f1= new FutureTask<>(getHttpContent1);
        FutureTask<String> f2= new FutureTask<>(getHttpContent2);
        taskExe.submit(f1);
        taskExe.submit(f2);
        String content1= f1.get();
        String content2= f2.get();
        System.out.println(content1);
        System.out.println(content2);
    }
}
