package actionInConcurrency.chapt5.searchByFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	static int[] arr;
	static ExecutorService pool = Executors.newCachedThreadPool();
	static final int Thread_num = 3;
	static AtomicInteger flag = new AtomicInteger(-1);
	
	public static int search(int searchValue,int begin,int end){
		int i = 0;
		for(i=begin;i<end;i++){
			if(flag.get()>=0){
				return flag.get();
			}
			if(arr[i] == searchValue){
				//如果设置失败，说明其他线程已经找到了
				if(!flag.compareAndSet(-1, i)){
					return flag.get();
				}
				return i;
			}
		}
		return -1;
	}
	
	public static int searchByFuture(int searchValue){
		int step = arr.length/Thread_num;
		List<Future<Integer>> re = new ArrayList<Future<Integer>>();
		//分组，
		for(int i = 0;i<arr.length;i+=step){
			int end = i+step;
			if(end>=arr.length) end = arr.length;
			//给future添加线程池返回的整数，如果整数=-1，说明该线程搜索的部分数组不含searchValue，
			//如果下标大于0，则该整数即为原数组所含searchValue对应的下标。
			//Future模式会立即返回一个Future对象，添加到List<Future<Integer>>中去
			re.add(pool.submit(new SearchTask(searchValue, i, end)));
		}
		
		for(Future<Integer> future:re){
			try {
				if(future.get()>=0) return future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		
	}
}
