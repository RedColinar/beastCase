package actionInConcurrency.chapt5.future;

public class Client {
	public Data reuqest(final String queryStr){
		final  FutureData future = new FutureData();
		//RealData的构造很慢，所以在单独的线程进行
		new Thread(() -> {
			RealData realdata = new RealData(queryStr);
			future.setRealData(realdata);
		}).start();
		//立即返回Future，即使这个future中没有真实数据
		return future;
	}
}
