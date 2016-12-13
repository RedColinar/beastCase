package actionInConcurrency.chapt5.sortParallel.insertionShellSort;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShellSortParallel {
	//串行的希尔排序实现
	public static void shellSort(int[] arr){
		//最大增量求法：希尔排序法增量的选取，除1外，无其它公约数，素数序列 刚好满足要求。
		/*原因：因为希尔排序要分组，如果增量序列之间的最大公约数大于1，
		 * 则可能会造成前面一趟（或者某趟）分在同一组已经比较过的关键字，在本趟继续分在同一组，
		 * 此时这些关键字相互的再次比较纯粹是浪费并无意义，只能增加算法的时间
		 * 因此当增量间的最大公约数为1（通称互质）时，就能尽可能地打乱原有的序列，让尽可能多的从未比较过的关键字进行比较
		 */		
		//生成最大增量值序列h，1,4,13,40……，同时保证h最接近arr的长度
		int h = 1;
		int len = arr.length;
		while(h <= len/3){
			//这里的增量序列是个等差数列
			h = h*3 +1;
		}
		//正式进入希尔排序
		//这个while循环是对缩小增量h的循环
		while(h > 0){
			//
			for(int i = h;i<arr.length;i++){
				if(arr[i-h] > arr[i]){
					int tmp = arr[i];
					int j = i - h;
					//把tmp插入到最合适的位置，相当于局部的插入排序
					//此时下标小于j的都是已经排好序，
					//如果前面有比tmp大的，就把所有比tmp大的数依次往后挪动一个位置，
					//在所有比tmp大的数前面腾出一个合适的位置，把tmp放到这个合适的位置。
					while(j >= 0 && arr[j] > tmp){
						arr[j+h] = arr[j];
						j -= h;
					}
					//具体把tmp插入到合适的位置
					arr[j + h] = tmp;
				}
			}
			//计算出下一个h值，缩小增量，直至增量为1
			h = (h - 1)/3;
			System.out.print(Arrays.toString(arr));
			Arrays.stream(arr).forEach(System.out::println);
		}
	}
	
	//并行的希尔排序实现
	//声明一个静态的 要排序的数组和线程池，然后写并行任务
	static int[] arr;
	static ExecutorService pool = Executors.newCachedThreadPool();
	public static class ShellSortTask implements Runnable{
		int i = 0;
		int h = 0;
		CountDownLatch l;
		//构造器
		public ShellSortTask(int i, int h, CountDownLatch l) {
			super();
			this.i = i;
			this.h = h;
			this.l = l;
		}

		@Override
		public void run() {
			if(arr[i] < arr[i-h]){
				int tmp = arr[i];
				int j = i - h;
				while(j >= 0 && arr[j] > tmp){
					arr[j + h] = arr[j];
					j -= h;
				}
				arr[j + h] = tmp;
			}
			System.out.println(Arrays.toString(arr));
			l.countDown();
		}
	}
	//并行的实体方法
	public static void shellSortPallel(int[] array) throws InterruptedException{
		//生成增量数列
		int h = 1;
		CountDownLatch latch = null;
		while(h <= array.length/3){
			h = h*3+1;
		}
		while(h>0){
			System.out.println("h="+h);
			//分组个数为arr.length - h
			if(h>=4)
				latch = new CountDownLatch(array.length - h);
			for(int i = h;i<arr.length;i++){
				//控制线程数量，如果h为1，那就用当前线程执行最后一趟排序，不用新增线程
				if(h >= 4){
					pool.execute(new ShellSortTask(i,h,latch));
				}else{
					if(array[i]<array[i-h]){
						int tmp = array[i];
						int j = i-h;
						while(j >= 0 && array[j] > tmp){
							array[j+h] = array[j];
							j -= h;
						}
						array[j+h] = tmp;
					}
					System.out.println(Arrays.toString(array));
					//更酷的写法
					//Arrays.stream(arr).forEach(System.out::print);
				}
			}
			latch.await();
			h = (h-1)/3;
		}
	}
	//main方法
	public static void main(String[] args) throws InterruptedException {
		arr = new int[]{20,18,13,14,11,10,9,8,7,6,5,4,2,1};
		//shellSort(arr);
		shellSortPallel(arr);
		
	}
}
