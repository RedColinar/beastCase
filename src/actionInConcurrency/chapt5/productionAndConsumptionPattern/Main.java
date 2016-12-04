package actionInConcurrency.chapt5.productionAndConsumptionPattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		//建立缓冲区,BlockingQueue中的offer()和take()使用锁和阻塞等待实现线程间的同步，性能并不优越，只是为了方便数据共享
		BlockingQueue<Commodity> queue = new LinkedBlockingQueue<Commodity>(10);
		
		Producer producer1 = new Producer(queue);
		Producer producer2 = new Producer(queue);
		Producer producer3 = new Producer(queue);
		Consumer consumer1 = new Consumer(queue);
		Consumer consumer2 = new Consumer(queue);
		Consumer consumer3 = new Consumer(queue);
		
		ExecutorService  service = Executors.newCachedThreadPool();//建立线程池
		service.execute(producer1);
		service.execute(producer2);
		service.execute(producer3);
		service.execute(consumer1);
		service.execute(consumer2);
		service.execute(consumer3);
		Thread.sleep(10*1000);
		producer1.stop();
		producer2.stop();
		producer3.stop();
		Thread.sleep(3000);
		service.shutdown();
	}
}
