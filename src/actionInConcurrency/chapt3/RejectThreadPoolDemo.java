package actionInConcurrency.chapt3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//自定义线程池 和 拒绝策略的使用
public class RejectThreadPoolDemo {
	public static void main(String[] args) throws InterruptedException {
		Runnable run =myUtil.MakeRunner.uncheck(() -> {
			System.out.println(System.currentTimeMillis() + ":Thread ID:"+Thread.currentThread().getId());
			Thread.sleep(1000);
		});
		/*    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler  拒绝策略
                              ) {}
 
		 */
		ExecutorService es = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, 
				new LinkedBlockingQueue<Runnable>(10), 
				Executors.defaultThreadFactory(),
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.out.println(r.toString()+" is discard");
					}
				});
		for (int i = 0;i<Integer.MAX_VALUE;i++){
			es.submit(run);
			Thread.sleep(10);
		}
	}
}
