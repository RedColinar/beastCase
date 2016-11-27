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
	public static void main3(String[] args) {
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
	/*java.lang.ArithmeticException: / by zero
		at chapt3.MissStackDemo.lambda$2(MissStackDemo.java:48)
		at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
		at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
		at java.lang.Thread.run(Unknown Source)

	 * 这里的异常不完整
	 * */
	/**
	 * 更好的打印完整堆栈信息的办法：使用自己写的myUtil.TraceThreadPoolExecutor方法，目前只写了一个构造方法
	 * */
	public static void main4(String[] args) {
		ThreadPoolExecutor pools = new myUtil.TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0, 
				TimeUnit.SECONDS, 
				new SynchronousQueue<>());
		for(int i=0;i<5;i++){
			int value = i;
			pools.execute(()->{
				System.out.println(100/value);
			});
		}
	}
	/*java.lang.Exception: Client stack trace
			at myUtil.TraceThreadPoolExecutor.clientTrace(TraceThreadPoolExecutor.java:25)
			at myUtil.TraceThreadPoolExecutor.execute(TraceThreadPoolExecutor.java:17)
			at chapt3.MissStackDemo.main(MissStackDemo.java:69)
		Exception in thread "pool-1-thread-1" java.lang.ArithmeticException: / by zero
			at chapt3.MissStackDemo.lambda$3(MissStackDemo.java:70)
			at myUtil.TraceThreadPoolExecutor.lambda$0(TraceThreadPoolExecutor.java:30)
			at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
			at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
			at java.lang.Thread.run(Unknown Source)
	 * 完整的异常
	 * */
	
	//算了不装逼了，不用lambda了，用匿名内部类写一个…
	public static class Task implements Runnable{
		int a,b;
		public Task(int a, int b) {
			super();
			this.a = a;
			this.b = b;
		}
		@Override
		public void run() {
			double d=a/b;
			System.out.println(d);
		}
	}
	public static void main(String[] args) {
		ThreadPoolExecutor pools = new myUtil.TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, 
				new SynchronousQueue<Runnable>());
		for(int i=0;i<5;i++){
			//只能初始化静态的内部类，Task必须是static
			pools.execute(new Task(100, i));
		}
	}
	/*java.lang.Exception: Client stack trace
		at myUtil.TraceThreadPoolExecutor.clientTrace(TraceThreadPoolExecutor.java:25)
		at myUtil.TraceThreadPoolExecutor.execute(TraceThreadPoolExecutor.java:17)
		at chapt3.MissStackDemo.main(MissStackDemo.java:106)
	Exception in thread "pool-1-thread-1" java.lang.ArithmeticException: / by zero
		at chapt3.MissStackDemo$Task.run(MissStackDemo.java:97)
		at myUtil.TraceThreadPoolExecutor.lambda$0(TraceThreadPoolExecutor.java:30)
		at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
		at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
		at java.lang.Thread.run(Unknown Source)
		也没啥不一样……
	*/

}
