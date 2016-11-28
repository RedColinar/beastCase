package chapt4;

import java.util.concurrent.atomic.AtomicInteger;
//使用AtomicInteger比锁有更好的性能
public class AtomicIntegerDemo {
	//之前用volatile的时候无法保证 i++的 原子性操作
	static AtomicInteger i = new AtomicInteger();
	//incrementAndGet() 会使用CAS操作自增1并返回
	 Runnable AddThread = () -> {for(int k=0;k<10000;k++) i.incrementAndGet();};
	
	public static void main(String[] args) throws InterruptedException {
		Thread[] ts= new Thread[10];
		//跑了10个线程,依然得到了想要的结果100000,但是volatile 得到的结果小于100000
		for(int k=0;k<10;k++){
			ts[k] = new Thread(new AtomicIntegerDemo().AddThread);
		}
		for (int k=0;k<10;k++){ts[k].start();}
		for (int k=0;k<10;k++){ts[k].join();}//start()不用检查异常,join()需要检查异常
		System.out.println(i);
	}
}
