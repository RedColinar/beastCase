package actionInConcurrency.chapt3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//自定义线程创建 ThreadFactory
public class UserDefinedThreadFactory {
	public static void main(String[] args) throws InterruptedException {
		Runnable run =myUtil.MakeRunner.uncheck(() -> {
			System.out.println(System.currentTimeMillis() + ":Thread ID:"+Thread.currentThread().getId());
			Thread.sleep(1000);
		});
		ExecutorService es = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, 
				new SynchronousQueue<Runnable>(), 
				(Runnable r)->{	//用lambda实现ThreadFactory()接口，这里可能使用匿名内部类更好一些
					Thread t = new Thread(r);
					//设置守护线程，当一个java应用内只有守护线程时，java虚拟机会自动退出
					t.setDaemon(true);
					System.out.println("creat "+t);
					return t;
				});
		for(int i=0;i<5;i++){
			es.submit(run);
		}
		Thread.sleep(2000);
	}
}