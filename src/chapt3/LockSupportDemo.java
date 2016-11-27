package chapt3;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
	public static Object u = new Object();
	static ChangedThread t1 = new ChangedThread("t1");
	static ChangedThread t2 = new ChangedThread("t2");
	
	public static  class  ChangedThread extends Thread{
		public ChangedThread(String name){
			super.setName(name);
		}
		@Override
		public void run(){
			synchronized (u) {
				System.out.println("in " + getName());
				LockSupport.park();
				if(Thread.interrupted()){
					System.out.println(getName() + "被中断了");
				}
			}
			System.out.println(getName() + "执行结束");
		}
	}
	public static void main(String[] args) throws InterruptedException {
		t1.start();
		Thread.sleep(1000);
		t2.start();
		t1.interrupt();
		LockSupport.unpark(t2);
	}
}
