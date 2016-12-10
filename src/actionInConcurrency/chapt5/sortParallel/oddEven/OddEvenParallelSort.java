package actionInConcurrency.chapt5.sortParallel.oddEven;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//擦，搞了半天发现奇偶排序实际上没什么卵用，因为并行时需要创建和销毁很多线程，太弱了
public class OddEvenParallelSort {
	//需要排序的数组
	static int[] arr;
	static int flag = 1;
	//这个flag需要有synchronized 的 set和get方法
	static synchronized void setFlag(int f){
		flag = f;
	}
	static synchronized int getFlag(){
		return flag;
	}
	//任务类，实现Runnable接口
	public static class OddEvenSortTask implements Runnable{
		int i;
		/* CountDownLatch是一个多线程控制工具类，意为“倒计时器”
		 * 用来控制线程等待，可以让某一个线程等待直到倒计时结束，再开始执行
		 * */ 
		CountDownLatch latch;
		public OddEvenSortTask(int i,CountDownLatch latch){
			this.i = i;
			this.latch = latch;
		}
		@Override
		public void run() {
			if(arr[i] > arr[i+1]){
				int temp = arr[i];
				arr[i] = arr[i+1];
				arr[i+1] = temp;
				setFlag(1);
			}
			latch.countDown();
		}
	}
	//排序主体
	public static void oddEvenParallel(int[] arr) throws InterruptedException{
		int start = 0;
		int length = arr.length;
		ExecutorService pool = Executors.newCachedThreadPool();
		while (getFlag() == 1 || start == 1){
			setFlag(0);
			//偶数的数组的长度，当start为1时，只有len/2-1个线程
			//奇数和偶数线程的处理：
			CountDownLatch latch = new CountDownLatch(
					length/2-(length%2==0?start:0));
			//一口气把
			for(int i = start;i<arr.length;i+=2){
				pool.submit(new OddEvenSortTask(i, latch));
			}
			//要求主线程等待所有倒计时任务完成
			latch.await();
			if(start == 0)
				start=1;
			else
				start=0;
		}
	}
	//main方法
	public static void main(String[] args) throws InterruptedException {
		arr = new int[]{3,5,4,2,1,};
		oddEvenParallel(arr);
		for (int in : arr) {
			System.out.print(in+" ");
		}
	}
}
