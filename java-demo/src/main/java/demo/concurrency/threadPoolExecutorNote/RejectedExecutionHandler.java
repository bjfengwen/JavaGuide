package demo.concurrency.threadPoolExecutorNote;

public interface RejectedExecutionHandler {

    void rejectedExecution(Runnable r, ThreadPoolExecutorNote executor);
}
