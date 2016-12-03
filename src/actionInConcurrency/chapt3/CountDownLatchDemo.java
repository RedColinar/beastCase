package actionInConcurrency.chapt3;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo implements Runnable{
	public final static CountDownLatch end = new CountDownLatch(10);
	public final static CountDownLatchDemo demo = new CountDownLatchDemo();
	@Override
	public void run() {
		try {
			Thread.sleep(new Random().nextInt(10)*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("check complete");
		end.countDown();
	}
	public static void main(String[] args) throws InterruptedException {
		//
		ExecutorService exec = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			exec.submit(demo);
		}
		end.await();
		System.out.println("fire");
		exec.shutdown();
	}
}
