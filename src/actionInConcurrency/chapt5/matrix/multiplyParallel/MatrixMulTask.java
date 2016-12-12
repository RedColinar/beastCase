package actionInConcurrency.chapt5.matrix.multiplyParallel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.operator.MatrixOperator;
//用ForkJoin框架进行递归任务
@SuppressWarnings("serial")
public class MatrixMulTask extends RecursiveTask<Matrix>{
	//需要计算的两个矩阵
	Matrix m1;
	Matrix m2;
	//计算结果位于父矩阵相乘结果的位置
	String pos;
	//构造器
	public MatrixMulTask(Matrix m1, Matrix m2, String pos) {
		super();
		this.m1 = m1;
		this.m2 = m2;
		this.pos = pos;
	}

	@Override
	protected Matrix compute() {
		System.out.println(Thread.currentThread().getId()+":"+Thread.currentThread().getName()+" is start");
		if(m1.rows() <= Main.granularity || m2.cols() <= Main.granularity){
			Matrix m = MatrixOperator.multiply(m1, m2);
			return m;
		}else{
			//继续分割矩阵
			int rows;
			rows = m1.rows();
			//左乘的矩阵横向分割，先行数，再列数
			Matrix m11 = m1.getSubMatrix(1, 1, rows/2,m1.cols());
			Matrix m12 = m1.getSubMatrix(rows/2 + 1, 1, m1.rows(), m1.cols());
			//右乘的矩阵纵向分割
			Matrix m21 = m2.getSubMatrix(1, 1, m2.rows(), m2.cols()/2);
			Matrix m22 = m2.getSubMatrix(1, m2.cols()/2 + 1, m2.rows(), m2.cols());
			
			ArrayList<MatrixMulTask> subTasks = new ArrayList<MatrixMulTask>();
			MatrixMulTask tmp = null;
			tmp = new MatrixMulTask(m11, m21, "p1");
			subTasks.add(tmp);
			tmp = new MatrixMulTask(m11, m22, "p2");
			subTasks.add(tmp);
			tmp = new MatrixMulTask(m12, m21, "p3");
			subTasks.add(tmp);
			tmp = new MatrixMulTask(m12, m22, "p4");
			subTasks.add(tmp);
			//
			for(MatrixMulTask t:subTasks){
				t.fork();
			}
			Map<String,Matrix> matrixMap = new HashMap<String,Matrix>();
			for(MatrixMulTask t :subTasks){
				matrixMap.put(t.pos, t.join());
			}
			//水平矩阵拼接
			Matrix tmp1 = MatrixOperator.horizontalConcatenation(matrixMap.get("p1"),matrixMap.get("p2"));
			Matrix tmp2 = MatrixOperator.horizontalConcatenation(matrixMap.get("p3"),matrixMap.get("p4"));
			//垂直矩阵拼接
			Matrix m =  MatrixOperator.verticalConcatenation(tmp1, tmp2);
			//返回完成计算的矩阵
			return m;
		}
	}

}
