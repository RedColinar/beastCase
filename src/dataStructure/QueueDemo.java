package dataStructure;
	/**
	 * 
	 * @author 一方乌鸦
	 *
	 */
public class QueueDemo {
	/**
	 * 一般队列结构的基本操作有：
	 * 1.入队列：将一个元素 添加至队尾
	 * 2.出队列：将队头的元素取出，同时删除该元素，使后一个元素成为队头
	 */
	/**
	 *队列的初始化:
	 *1.按符号常亮指定的大小申请一片内存空间，用来保存队列的中的数据
	 *2.设置 head=0和tail=0表示一个空栈
	 */
	final int LEN = 15;//static的变量或常亮不能用构造器初始化
	
	class Data{
		String name;
		int age;
	}
	
	Data[] data = new Data[LEN];
	int head;
	int tail;
	
	QueueDemo init(){
		QueueDemo q;
		if((q=new QueueDemo())!=null){
			q.head = 0;
			q.tail = 0;
		}
		return q;
	}
	
	/**
	 * 入队列
	 * 1.判断tail，如果tail等于LEN，则表示溢出，进行出错处理
	 * 2.设置tail=tail+1
	 * 3.将入队列元素保存到tail指向的位置
	 * 
	 */
	public int in(Data d){
		if(tail==LEN){
			return(0);
		}
		data[tail++]=d;
		return(1);
	}
	/**
	 * 出队列
	 * 1.判断队列head，如果head等于tail，则表示为空对列，进行出错处理
	 * 2.从队列首部返回队头元素的引用
	 * 3.设修改队头元素的序号，使其指向后一个元素
	 */
	public Data out(){
		if(head == tail){
			System.out.println("空对列");
		}else{
			return  data[head++];
		}
		return null;
	}
	/**
	 * 队列计算长度，用tail-head即可
	 */
}
