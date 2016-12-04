package dataStructure;

/**
 * 
 * @author 一方乌鸦
 * 
 */
public class LinkedList<E> {
	Node<E> head;
	private int size;
	
	@SuppressWarnings("hiding")
	private class Node<E>{
		@SuppressWarnings("unused")
		E e;
		Node<E> nextNode;
		public Node() {
			super();
		}

		public Node(E e) {
			super();
			this.e = e;
		}
	}
	
	public LinkedList() {
		super();
		this.head = new Node<>();
	}
	/**
	 * 追加节点 
	 * 1.分配内存空间，保存新增结点，默认新增节点的地址部分为null
	 * 2.从头引用head开始逐个检查，直到最后一个结点
	 * 3.将表尾节点的地址部分设置为新增节点的地址
	 * @param e
	 * @return
	 */
	public boolean addLast(E e){
		Node<E> newNode = new Node<>(e);
		Node<E> temp = head;
		while(temp.nextNode != null){
			temp = temp.nextNode;
		}
		temp.nextNode = newNode;
		size++;
		return true;
	}
	/**
	 * 插入头结点
	 * 1.分配内存空间，保存新增结点
	 * 2.使新增结点指向头引用head所指向的结点
	 * 3.使头引用head指向新增结点
	 */
	public boolean addFirst(E e){
		Node<E> newNode = new Node<>(e);
		newNode.nextNode =  head.nextNode;
		head.nextNode =newNode;
		return true;
	}
	/**
	 * 查找节点
	 * 1.从头引用开始，通过 下标index遍历
	 * 
	 */
	public Node<E> get(int index){
		Node<E> t = head;
		for(int i =0;i<=index;i++){
			t=t.nextNode;
		}
		return t;
	}
	/**
	 * 在特定索引位置插入节点
	 * 
	 */
	public boolean insert(int index,E e){
		Node<E> newNode = new Node<E>(e);
		Node<E> t = head;
		if(index==0){
			newNode.nextNode = head.nextNode;
			head.nextNode = newNode;
			return true;
		}
		for(int i = 0;i<=index-1;i++){
			t=t.nextNode;
			newNode.nextNode =  t.nextNode;
			t.nextNode = newNode;
		}
		return true;
	}
	/**
	 * 删除结点
	 * @throws Exception 
	 * 
	 */
	@SuppressWarnings("unused")
	public boolean delete(int index) throws Exception{
		if(index<0 || index>=size){
			throw new Exception() ;
		}
		if(index==0){
			Node<E> t = head.nextNode;
			head.nextNode = head.nextNode.nextNode;
			t = null;
		}else{
			Node<E> pre = get(index-1);
			Node<E> t = pre.nextNode;
			pre.nextNode = pre.nextNode.nextNode;
			t = null;		
		}
		return false;
		
	}
	/**
	 * 获取长度
	 */
	public int size(){
		return size;
	}
//java.util.LinkedList<E> e = new java.util.LinkedList<E>().;
}
