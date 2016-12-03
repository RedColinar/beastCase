package actionInConcurrency.chapt3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
	public static void main(String[] args) {
		Runnable r = myUtil.MakeRunner.uncheck(() ->{
			System.out.println(System.currentTimeMillis()+":Thread ID:"+ Thread.currentThread().getId());
			//让每个线程执行一秒钟
			Thread.sleep(1000);
		});
		//创建固定大小的线程池
		//ExecutorService es = Executors.newFixedThreadPool(5);
		//创建非固定大小的线程池
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0;i < 10;i++){
			es.submit(r);
		}
	}
}
