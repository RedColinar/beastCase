package actionInConcurrency.chapt3;

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
	public static void main(String[] args) {
		ReadWriteLockDemo demo = new ReadWriteLockDemo();
		for(int i=0;i<18;i++){
			new  Thread(myUtil.MakeRunner.uncheck(() ->  demo.handleRead(readLock))).start();
		}
		for(int i=18;i<20;i++){
			new  Thread(myUtil.MakeRunner.uncheck(() -> demo.handleWrite(writeLock, new Random().nextInt()))).start();
		}
	}
			
}


