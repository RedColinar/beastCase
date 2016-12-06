package actionInConcurrency.chapt5.future;

public class Main {
	public static void main(String[] args) {
		Client client = new Client();
		Data data = client.reuqest("hi-pq！   ");
		System.out.println("请求完毕");
		try{
			System.out.println("我先等5s");
			Thread.sleep(5000);
			System.out.println("再有5s结果就出来了");
		}catch (Exception e) {}
		//调用数据时，会等到realData数据返回时，
		System.out.println("数据= "+data.getResult());
	}
}
