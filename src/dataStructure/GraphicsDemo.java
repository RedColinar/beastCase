package dataStructure;

import java.util.Scanner;

//图
public class GraphicsDemo {
	static final int MaxNum = 20;
	static final int MaxValue = 65535;
	
	class GraphMatrix{
		char[] Vertex = new char[MaxNum];//图的最大顶点数
		int GType;//0:无向图 1:有向图
		int VertexNum;//顶点个数
		int EdgeNum;//边的个数
		int[][] EdgeWeight = new int[MaxNum][MaxNum];//保存边的权
		int[] isTrav = new int[MaxNum];//遍历标志
	}
	
	public void CreateGraph(GraphMatrix gm){
		int i,j,k;
		int weight;
		char EstarV,EendV;
		Scanner input = new Scanner(System.in);
		
		System.out.println("请输入图中各顶点信息\n");
		for(i=0;i<gm.VertexNum;i++){
			System.out.printf("第%d个顶点",i+1);
			gm.Vertex[i] = (input.next().toCharArray())[0];
 		}
		
		System.out.println("输入构成各边的顶点及权值:\n");
		for(k=0;k<gm.EdgeNum;i++){
			
		}
	}
}
