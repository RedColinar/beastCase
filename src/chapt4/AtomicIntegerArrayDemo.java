package chapt4;

import java.util.concurrent.atomic.AtomicIntegerArray;

//无锁的原子数组AtomicIntegerArray
public class AtomicIntegerArrayDemo {
	static AtomicIntegerArray arr = new AtomicIntegerArray(10);
	static Runnable AddThread = ()->{
		for(int k=0;k<10000;k++){
			arr.getAndIncrement(k%arr.length());
		}
	};
	public static void main(String[] args) throws InterruptedException {
		Thread[] ts =  new Thread[10];
		for(int k=0;k<10;k++){
			ts[k]=new Thread(AddThread);
		}
		for(int k=0;k<10;k++){ts[k].start();}
		for(int k=0;k<10;k++){ts[k].join();}
		System.out.println(arr);//打印不用遍历
	}
}
/*结果：[10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000]*/

/** 普通数组：	
 * static int[] arr = new int[10];//(10);
	static Runnable AddThread = ()->{
		for(int k=0;k<10000;k++){
			arr[k%arr.length]++;
		}
	};
	public static void main(String[] args) throws InterruptedException {
		Thread[] ts =  new Thread[10];
		for(int k=0;k<10;k++){
			ts[k]=new Thread(AddThread);
		}
		for(int k=0;k<10;k++){ts[k].start();}
		for(int k=0;k<10;k++){ts[k].join();}
		for(int k=0;k<arr.length;k++){System.out.println(arr[k]);}
	}
 
 */
/*结果9938
	9975
	9967
	9958
	9947
	9953
	9959
	9942
	9941
	9931

 */