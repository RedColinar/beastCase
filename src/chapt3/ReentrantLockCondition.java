package chapt3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCondition implements Runnable{
	public static ReentrantLock lock =new ReentrantLock();
	public static Condition condition = lock.newCondition();//condition根据重入锁生成Condition条件
	
	@Override
	public void run() {
		lock.lock();//lock()不用放入try-catch
		try {
			condition.await();
			System.out.println("Thread is going on");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		ReentrantLockCondition t = new ReentrantLockCondition();
		Thread th = new Thread(t);
		th.start();
		Thread.sleep(2000);
		//start后不会打印"Thread is going on"
		//对contion操作前需要获得锁
		lock.lock();
		condition.signal();
		lock.unlock();
		
		
	}

}
