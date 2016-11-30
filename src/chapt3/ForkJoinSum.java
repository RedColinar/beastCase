package chapt3;

import java.util.concurrent.RecursiveTask;

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

}
