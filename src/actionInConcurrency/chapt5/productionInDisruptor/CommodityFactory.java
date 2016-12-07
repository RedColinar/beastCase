package actionInConcurrency.chapt5.productionInDisruptor;

import com.lmax.disruptor.EventFactory;
//一个产生Commodity的工厂类，会在Disruptor系统初始化时，构造所有缓冲区中的对象实例
public class CommodityFactory implements EventFactory<Commodity>{

	@Override
	public Commodity newInstance() {
		return new Commodity();
	}

}
