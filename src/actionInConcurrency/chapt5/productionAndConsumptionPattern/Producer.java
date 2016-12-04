package actionInConcurrency.chapt5.productionAndConsumptionPattern;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable{
	private volatile boolean isRunning = true;
	private BlockingQueue<Commodity> queue;//内存缓冲区
	private static AtomicInteger count = new AtomicInteger();//总数，原子操作
	private static final int SLEEPING = 1000;
	
	public Producer(BlockingQueue<Commodity> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		Commodity commodity =  null;
		Random r = new Random();
		System.out.println("start Producer id ="+Thread.currentThread().getId());
		try{
			while(isRunning){
				Thread.sleep(r.nextInt(SLEEPING));
				commodity = new Commodity(count.incrementAndGet());
				System.out.println(commodity+" is into queue");
				if(!queue.offer(commodity, 2, TimeUnit.SECONDS)){
					System.err.println("failed to put commodity:"+commodity);
				}
			}
		}catch (InterruptedException e){
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	public void stop(){
		isRunning = false;
	}
}
