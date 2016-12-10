package actionInConcurrency.chapt5.sortParallel.oddEven;

import java.util.concurrent.CountDownLatch;

public class OddEvenParallelSort {
	static int[] arr;
	static int flag = 1;
	static synchronized void setFlag(int f){
		flag = f;
	}
	static synchronized int getFlag(){
		return flag;
	}
	
	public static class OddEvenSortTask implements Runnable{
		int i;
		/* A synchronization aid that allows one or more threads to wait 
		 * until a set of operations being performed in other threads completes.
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
	public static void oddEvenParallel(int[] arr){
		int start = 0;
		while (getFlag() == 1 || start == 1){
			setFlag(0);
		}
	}
}
