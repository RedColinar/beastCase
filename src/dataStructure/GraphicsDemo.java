package dataStructure;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
//图
public class GraphicsDemo {
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) {
		GraphMatrix gm = new GraphMatrix();
		CreateGraph(gm);
		OutGraph(gm);
		DeepTraGraph(gm);//深度优先
		BroadTraGraph(gm);//广度优先
	}
	//创建邻接矩阵图
	public static void CreateGraph(GraphMatrix gm){
		int i,j,k;
		int weight;
		char EstarV,EendV;
		System.out.print("输入生成图的类型：");
		gm.GType = input.nextInt();
		System.out.print("输入图的顶点数量：");
		gm.VertexNum = input.nextInt();
		System.out.print("输入图的边数量：");
		gm.EdgeNum = input.nextInt();
		System.out.println("请输入图中各顶点信息:");
		for(i=0;i<gm.VertexNum;i++){
			System.out.printf("第%d个顶点:",i+1);
			gm.Vertex[i] = (input.next().toCharArray())[0];
 		}
		System.out.printf("输入构成各边的顶点及权值:\n");
		for(k=0;k<gm.EdgeNum;k++){
			System.out.printf("第%d条边:",k+1);
			//输入格式为： a b 3
			EstarV = input.next().charAt(0);
			EendV = input.next().charAt(0);
			weight = input.nextInt();
			for(i=0;EstarV!=gm.Vertex[i];i++);//在已有顶点中查找开始点
			for(j=0;EendV !=gm.Vertex[j];j++);//在已有顶点中查找终结点
			gm.EdgeWeight[i][j] = weight;
			//在对角位置保存权值
			if(gm.GType==0){
				gm.EdgeWeight[j][i]=weight;
			}
		}
	}
	//输出邻接矩阵
	public static void OutGraph(GraphMatrix gm){
		System.out.println("该图的邻接矩阵数据如下：");
		int i,j;
		for(j=0;j<gm.VertexNum;j++){
			System.out.printf("\t%c",gm.Vertex[j]);//在第一行输出顶点信息
		}
		System.out.printf("\n");
		for(i=0;i<gm.VertexNum;i++){
			System.out.printf("%c",gm.Vertex[i]);
			for(j=0;j<gm.VertexNum;j++){
				if(gm.EdgeWeight[i][j]==GraphMatrix.MaxValue){
					System.out.printf("\tZ");//以Z表示无穷大
				}else{
					System.out.printf("\t%d",gm.EdgeWeight[i][j]);//输出边的权值
				}
			}
			System.out.println();
		}
	}
	//从第n个节点开始，深度遍历图
	private static void DeepTraOne(GraphMatrix gm,int n){
		int i;
		gm.isTrav[n] = 1;
		System.out.printf("->%c",gm.Vertex[n]);//输出结点数据
		//添加处理节点的操作
		for(i=0;i<gm.VertexNum;i++){
			if(gm.EdgeWeight[n][i] != GraphMatrix.MaxValue && gm.isTrav[n] == 0){
				DeepTraOne(gm,i);
			}
		}
	}
	//深度优先遍历
	public static void DeepTraGraph(GraphMatrix gm){
		int i;
		for(i=0;i<gm.VertexNum;i++){
			gm.isTrav[i]=0;
		}
		System.out.println("深度优先遍历节点：");
		for(i=0;i<gm.VertexNum;i++){
			if(gm.isTrav[i]==0){ //如果该点未遍历
				DeepTraOne(gm,i);//调用函数遍历
			}
		}
		System.out.println();
	}
	//广度优先遍历
	public static void BroadTraGraph(GraphMatrix gm){
		int i;
		for(i=0;i<gm.VertexNum;i++){
			gm.isTrav[i]=0;
		}
		System.out.println("广度优先遍历节点：");		
		Queue<Integer> queue = new LinkedList<>();
		for(int v = 0;v < gm.VertexNum;v++){
			if(gm.isTrav[v] == 0){
				gm.isTrav[v] = 1;
				System.out.printf("->%c",gm.Vertex[v]);//输出结点数据
				queue.add(v);
				while(!queue.isEmpty()){
					int w = queue.remove();
					//for循环中输出的结点是同一层级的
					for(int k = gm.firstVex(w);k>=0;k=gm.nextVex(v,k)){
						if(gm.isTrav[k]==0){
							queue.add(k);
							gm.isTrav[k] = 1;
							System.out.printf("->%c",gm.Vertex[k]);//输出结点数据
						}
					}
				}
			}
		}	
	}
}
class GraphMatrix{
	static final int MaxNum = 20;//图的最大顶点数
	static final int MaxValue = 65535;
	char[] Vertex = new char[MaxNum];
	int GType;//0:无向图 1:有向图
	int VertexNum;//顶点个数
	int EdgeNum;//边的个数
	int[][] EdgeWeight = new int[MaxNum][MaxNum];//保存边的权
	int[] isTrav = new int[MaxNum];//遍历标志
	//根据当前结点，找到第一个未访问过的结点
	public int firstVex(int i){
		for (int j = 0; j < VertexNum; j++) {
			if (EdgeWeight[i][j] != 0)
				return j;
		}
		return -1;
	}
	//根据当前结点，找到下一个未访问过的结点
	public int nextVex(int i, int k) {
		for (int j = k + 1; j < VertexNum; j++) {
			if (EdgeWeight[i][j] != 0)
				return j;
		}
		return -1;
	}
}