package chapt3;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("serial")
//递推任务：ForkJoinPoolDemo，分支合并框架
public class CountTask extends RecursiveTask<Long>{
	private static final int SIZE = 10000;
	 private long start;
	 private long end;
	
	public CountTask(long start, long end) {
		super();
		this.start = start;
		this.end = end;
	}
	//这个例子并不好。拆分子任务时，只有一次拆分，而且用的还是 int……，更好的办法是用递归拆分子任务，见ForkJoinSum()
	@Override
	protected Long compute() {
		long sum = 0;
		boolean canCompute = (end-start)<SIZE;
		if(canCompute){
			for(long i=start;i<=end;i++){
				sum+=i;
			}
		}else{
			long step=(start+end)/100;
			//放进ArrayList是为了循环 join()
			ArrayList<CountTask> subTasks=new ArrayList<CountTask>();
			long pos=start;
			for(int i=0;i<100;i++){
				long last=pos+step;
				//处理 最后一组
				if(last>end) last=end;
				CountTask subTask=new CountTask(pos,last);
				pos+=step+1;
				subTask.fork();
				subTasks.add(subTask);
			}
			for(CountTask t:subTasks){
				sum+=t.join();
			}
		}
		return sum;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		CountTask task = new CountTask(0, 200000L);
		ForkJoinTask<Long> result = forkJoinPool.submit(task);
		long res = result.get();
		System.out.println("sum="+res);
	}
}
