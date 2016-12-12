package actionInConcurrency.chapt5.matrix.multiplyParallel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;

public class Main {
	//粒度
	public static final int granularity = 3; 
	//Main方法
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool pool =  new ForkJoinPool();
		/*MatrixFactory.getRandomMatrix(int rows,int cols,Matrix hint)
		 *参数：行数，列数，接口矩阵的实现 
		 * */
		/*生成两个随机矩阵	*/
		Matrix m1 = MatrixFactory.getRandomMatrix(300, 300, null);
		Matrix m2 = MatrixFactory.getRandomMatrix(300, 300, null);
		MatrixMulTask task= new MatrixMulTask(m1, m2, null);
		
		ForkJoinTask<Matrix> result = pool.submit(task);
		Matrix m = result.get();
		System.out.println(m);
	}
}
