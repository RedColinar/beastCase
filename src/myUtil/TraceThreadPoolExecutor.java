package myUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
//写一个可以打印堆栈信息的ThreadPoolExecutor
public class TraceThreadPoolExecutor extends ThreadPoolExecutor{

	public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	//重写executor()和submit()方法
	@Override
	public void execute(Runnable command) {
		super.execute(wrap(command, clientTrace(), Thread.currentThread().getName()));
	}
	@Override
	public  Future<?> submit(Runnable task) {
		return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
	}
	
	private Exception clientTrace(){
		return new Exception("Client stack trace");
	}
	private Runnable wrap(final Runnable r,final Exception clientStack,String clientThreadName){
		return ()->{
			try{
				r.run();
			}catch(Exception e){
				clientStack.printStackTrace();
				throw e;
			}
		};
	}
}
