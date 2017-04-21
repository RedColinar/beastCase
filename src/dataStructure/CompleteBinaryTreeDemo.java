package dataStructure;

import java.util.Scanner;

//完全二叉树
public class CompleteBinaryTreeDemo {
	public static void main(String[] args) {
		CBT root = initTree();
		int depth = treeDepth(root);
		System.out.println("树的深度为："+depth);
		System.out.println("按层遍历：");
		levelTra(root);
		System.out.println("\n前序遍历：");
		DLR(root);
		System.out.println("\n中序遍历：");
		LDR(root);
		System.out.println("\n后序遍历：");
		LRD(root);
	}
	static Scanner input = new Scanner(System.in);
	static int MAXNUM = 20;
	//初始化一个经典的完全二叉树
	public static CBT initTree(){
		CBT root = new CBT("A");
		CBT b = new CBT("B",root);
		CBT c = new CBT("C",root);
		CBT d = new CBT("D",b);
		CBT e = new CBT("E",b);
		CBT f = new CBT("F",c);
		CBT g = new CBT("G",c);
		CBT h = new CBT("H",d);
		CBT i = new CBT("I",d);
		CBT j = new CBT("J",e);
		root.left = b;
		root.right = c;
		b.left = d;
		b.right = e;
		c.left = f;
		c.right = g;
		d.left = h;
		d.right = i;
		e.left = j;
		return root;
	}
	//计算树的深度
	public static int treeDepth(CBT node){
		int depLeft,depRight;
		if(node == null){
			return 0;
		}else{
			depLeft = treeDepth(node.left);
			depRight = treeDepth(node.right);
			if(depLeft > depRight){
				return depLeft + 1;
			}else{
				return depRight + 1;
			}
		}
	}
	//显示遍历路径
	private static void show(CBT node){
		System.out.printf("->%s",node.data);
	}
	//按层遍历
	public static void levelTra(CBT root){
		CBT p;
		CBT[] queue = new CBT[MAXNUM];
		int head = 0,tail = 0;
		if(root !=null){
			tail = (tail + 1)%MAXNUM;
			queue[tail] = root;
		}
		while(head!=tail){
			head = (head + 1)%MAXNUM;
			p = queue[head]; 
			show(p);
			if(p.left!=null){
				tail = (tail+1)%MAXNUM;
				queue[tail] = p.left;
			}
			if(p.right!=null){
				tail = (tail+1)%MAXNUM;
				queue[tail] = p.right;
			}
		}
	}
	//先序遍历 DLR
	public static void DLR(CBT node){
		if(node != null){
			show(node);
			DLR(node.left);
			DLR(node.right);
		}
	}
	//中序遍历 LDR
	public static void LDR(CBT node){
		if(node != null){
			LDR(node.left);
			show(node);
			LDR(node.right);
		}
	}
	//后序遍历 LRD
	public static void LRD(CBT node){
		if(node != null){
			LRD(node.left);
			LRD(node.right);
			show(node);
		}
	}
}
//完全二叉树结点
class CBT{
	String data;
	CBT left;	//左子树结点引用
	CBT right;	//右子树结点引用
	CBT parent; //父节点
	
	public CBT(){
	}
	//构造根节点
	public CBT(String data) {
		super();
		this.data = data;
	}
	//构造树节点，保存父类结点
	public CBT(String data,CBT parent){
		this.data = data;
		this.parent = parent;
	}
}