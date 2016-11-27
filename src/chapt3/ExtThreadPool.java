package chapt3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//拓展线程池，打印信息,拓展线程池实质上就是改写ThreadPoolExecutor中的三个方法
public class ExtThreadPool {
	public static class MyTask implements Runnable{
		String name;
		
		public MyTask(String name) {
			super();
			this.name = name;
		}

		@Override
		public void run() {
			System.out.println("正在执行" + ":Thread ID:" 
					+ Thread.currentThread().getId()
					+",Task name="+name);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>()){
			@Override
			protected void beforeExecute(Thread t, Runnable r) {
				System.out.println("准备执行："+((MyTask)r).name);
				super.beforeExecute(t, r);
			}
			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				System.out.println("执行完成："+((MyTask)r).name);
				super.afterExecute(r, t);
			}
			@Override
			protected void terminated() {
				System.out.println("线程池退出");
				super.terminated();
			}
		};
		
		for(int i = 0;i<5;i++){
			MyTask task = new MyTask("Task-"+i);
			es.execute(task);//这里用execute而不用submit
			Thread.sleep(10);
		}
		es.shutdown();//关闭线程池后执行terminated方法
	}
}
