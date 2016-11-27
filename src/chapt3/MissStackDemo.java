package chapt3;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
//让整数0做除数，查看异常
public class MissStackDemo {
	public static void main1(String[] args) {
		ThreadPoolExecutor pools = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, 
				new SynchronousQueue<>());
		for(int i=0;i<5;i++){
			int value = i;
			pools.submit(()->{
				System.out.println((double)100/value);
			});
		}
	}
	/*打印结果：
	 *  Infinity
		33.333333333333336
		25.0
		50.0
		100.0
	 */
	public static void main2(String[] args) {
		ThreadPoolExecutor pools = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, 
				new SynchronousQueue<>());
		for(int i=0;i<5;i++){
			int value = i;
			pools.submit(()->{
				System.out.println(100/value);
			});
		}
	}
	/*	打印结果：少了一位，而且没有任何的错误信息
	 * 	100
		33
		25
		50
	 * */
	public static void main(String[] args) {
		ThreadPoolExecutor pools = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, 
				new SynchronousQueue<>());
		for(int i=0;i<5;i++){
			int value = i;
			//不用submit，而用execute，会显示部分堆栈信息
			pools.execute(()->{
				System.out.println(100/value);
			});
		}
	}
}
