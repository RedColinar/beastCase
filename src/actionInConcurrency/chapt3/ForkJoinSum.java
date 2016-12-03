package actionInConcurrency.chapt3;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

@SuppressWarnings("serial")
public class ForkJoinSum extends RecursiveTask<Long>{
	private int start;
	private int end;
	private long[] numbers;
	public static final long THRESHOLD = 10_000;//10000的写法！

	public ForkJoinSum(long[] numbers) {
		this(0, numbers.length, numbers);//注意构造器的写法
	}


	private ForkJoinSum(int start, int end, long[] numbers) {
		super();
		this.start = start;
		this.end = end;
		this.numbers = numbers;
	}


	@Override
	protected Long compute() {
		int length=end-start;
		if(length<THRESHOLD){
			return computeSequentially();
		}
		ForkJoinSum leftSum = new ForkJoinSum(start, length/2, numbers);
		leftSum.fork();
		ForkJoinSum rightSum = new ForkJoinSum(length/2, end, numbers);
		long rightResult = rightSum.compute();
		long leftResult = leftSum.join();
		return leftResult+rightResult;
	}
	
	public long  computeSequentially() {
		long sum =0;
		for(int i = start;i<end;i++){
			sum+=numbers[i];
		}
		return sum;
	}
	//用并行编写前n个自然数求和
	public static long forkJoinSumParam(long n){
		//这里用eclipse的时候，LongStream加点没有出现rangeClosed(),看来eclipse对java8的支持不够好
		long[] numbers = LongStream.rangeClosed(1, n).toArray();
		ForkJoinTask<Long> task = new ForkJoinSum(numbers);
		return new ForkJoinPool().invoke(task);
	}

}
