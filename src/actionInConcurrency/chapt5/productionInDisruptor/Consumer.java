package actionInConcurrency.chapt5.productionInDisruptor;

import com.lmax.disruptor.WorkHandler;
//消费者实现为WorkHandler接口，来自Disruptor框架
public class Consumer implements WorkHandler<Commodity>{

	@Override
	public void onEvent(Commodity event) throws Exception {
		System.out.println(Thread.currentThread().getId()+":Event: --"+event.getValue()*event.getValue()+"--");
	}

}
