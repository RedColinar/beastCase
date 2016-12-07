package actionInConcurrency.chapt5.productionInDisruptor;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;
//构造生产者时，需要一个RingBuffer的引用
public class Producer {
	private final RingBuffer<Commodity> ringBuffer;

	public Producer(RingBuffer<Commodity> ringBuffer) {
		super();
		this.ringBuffer = ringBuffer;
	}
	//pushData()方法将产生的数据推入缓冲区，接受一个ByteBuffer对象，可以包装任何数据类型
	public void pushData(ByteBuffer bb){
		long sequence = ringBuffer.next();
		try{
			Commodity commodity = ringBuffer.get(sequence);
			commodity.setValue(bb.getLong(0));
		}finally{
			ringBuffer.publish(sequence);
		}
	}
}
