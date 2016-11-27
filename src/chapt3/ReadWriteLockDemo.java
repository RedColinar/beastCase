package chapt3;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
	@SuppressWarnings("unused")
	private static Lock  lock = new ReentrantLock();
	private static ReentrantReadWriteLock readWrite = new ReentrantReadWriteLock();
	private static Lock readLock = readWrite.readLock();
	private static Lock writeLock = readWrite.writeLock();
	private int value;
	
	public Object handleRead(Lock lock) throws InterruptedException {
		try{
			lock.lock();
			Thread.sleep(1000);
			System.out.println("read value = "+value);
			return value;
		}finally{
			lock.unlock();
		}
	}
	public void handleWrite(Lock lock,int i){
		try{
			lock.lock();
			this.value = i;
			System.out.println("write value = "+value);
			System.out.println(Integer.MAX_VALUE);
		}finally{
			lock.unlock();
		}
	}
	//一定要抛出异常，
	//在接口中定义异常，就是告诉调用者，必须在运行方法后处理异常。如果不定义的话。所有的异常必须由接口的实现类 自己处理，这在很多时候并不合适。
	@FunctionalInterface
	interface RunnableEx{
		public abstract void run() throws Exception;
	}
	//接受一个可抛异常的接口,把这个借口套在try catch中，返回这个lambda表达式
	public static Runnable uncheck (RunnableEx rx){
		return ()->{
			try{
				rx.run();
			}catch(Exception e){
				e.printStackTrace();
			}
		};
	}
	public static void main(String[] args) {
		ReadWriteLockDemo demo = new ReadWriteLockDemo();
		
		
		for(int i=0;i<18;i++){
			new  Thread(uncheck(() ->  demo.handleRead(readLock))).start();
		}
		for(int i=18;i<20;i++){
			new  Thread(uncheck(() -> demo.handleWrite(writeLock, new Random().nextInt()))).start();
		}
	}
			
}


