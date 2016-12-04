package actionInConcurrency.chapt5.productionAndConsumptionPattern;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
	private BlockingQueue<Commodity> queue;
	private static final int SLEEPING = 1000;
	
	public Consumer(BlockingQueue<Commodity> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		System.out.println("start Consumer id="+Thread.currentThread().getId());
		Random r = new Random();
		
		try{
			while(true){
				Commodity commodity = queue.take();
				System.out.println(commodity+" is out of queue");
				if(null!=commodity){
					int re = commodity.getId() * commodity.getId();
					//MessageFormat
					System.out.println(MessageFormat.format("{0}*{1}={2}", 
							commodity.getId(),commodity.getId(),re));
					Thread.sleep(r.nextInt(SLEEPING));
				}
			}
		}catch(InterruptedException e){
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}
