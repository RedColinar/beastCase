package actionInConcurrency.chapt5.productionInDisruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class Main {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException {
		ThreadFactory threadFactory = new ThreadFactory(){

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r);
			}
			
		};
		CommodityFactory factory = new CommodityFactory();
		//设置缓冲区大小
		int bufferSize = 1024;
		//创建disruptor对象，封装整个disruptor库的使用
		Disruptor<Commodity> disruptor = new Disruptor<Commodity>(factory,
				bufferSize,
				threadFactory,
				ProducerType.MULTI,
				new BlockingWaitStrategy());
		//设置用于处理数据的消费者，设置了4个线程
		disruptor.handleEventsWithWorkerPool(
				new Consumer(),
				new Consumer(),
				new Consumer(),
				new Consumer());
		disruptor.start();
		
		RingBuffer<Commodity> ringBuffer = disruptor.getRingBuffer();
		Producer producer = new Producer(ringBuffer);
		ByteBuffer bb = ByteBuffer.allocate(8);
		
		for(long l = 0;true;l++){
			bb.putLong(0, l);
			producer.pushData(bb);
			Thread.sleep(100);
			System.out.println("add data "+l);
		}
		
	}
}
