package actionInConcurrency.chapt5.futureInJDK;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
/**
 * Future接口还有一些方法可以
 * 1.取消任务
 * 2.是否已取消
 * 3.是否已完成
 * 4.取得返回对象
 * 5.get(long timeout,TimeUnit unit)//设置超时时间，取得返回对象
 */
public class FutureMain {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//构造FutureTask
		
		FutureTask<String> future = new FutureTask<String>(new RealData("hi-pq! "));
		//新建一个线程池…虽然只有一个…
		ExecutorService executor = Executors.newFixedThreadPool(1);
		//执行FutureTask,
		executor.submit(future);
		
		System.out.println("请求完毕");
		try {
			System.out.println("结果出来还需10s");
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		//此时call()方法如果还没有完成，则会继续等待
		System.out.println("数据  = "+future.get());
	}
}

