package myUtil;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class LockFreeVector<E> {
	//AtomicReferenceArray是无锁的 对象数组，使用Unsafe通过CAS的方式控制线程安全
	private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;
	//AtomicReference是无锁的 对象引用
	private final AtomicReference<Descriptor<E>> descriptor;
	
	private static final int N_BUCKET = 30;
	private final static int FIRST_BUCKET_SIZE = 8;
	//LockFreeVectoor的构造函数
	public LockFreeVector(){
		buckets = new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKET);
		buckets.set(0, new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));
		descriptor = new AtomicReference<Descriptor<E>>(new Descriptor<E>(0,null));
	}
	
	/*使用CAS操作写入数据
	 * */
	static class Descriptor<E>{
		public int size;
		volatile WriteDescriptor<E> writeop;
		//构造器
		public Descriptor(int size, WriteDescriptor<E> writeop) {
			this.size = size;
			this.writeop = writeop;
		}
		public void completeWrite(){
			WriteDescriptor<E> tmpOp = writeop;
			if(tmpOp != null){
				tmpOp.doIt();
				writeop = null;
			}
		}
	}
	//作用是 修改初始化的 数组 对应的索引位置的对象
	static class WriteDescriptor<E> {
		//要修改的原子数组
		public AtomicReferenceArray<E> addr;
		//要写入的数组索引位置
		public int addr_ind;
		public E oldV;
		public E newV;
		//构造器
		public WriteDescriptor(AtomicReferenceArray<E> addr, int addr_ind, E oldV, E newV) {
			this.addr = addr;
			this.addr_ind = addr_ind;
			this.oldV = oldV;
			this.newV = newV;
		}
		//如果下标为addr_ind的元素等于oldV，则把下标为addr_ind的元素设置为newV，并返回true
		public void doIt() {
			addr.compareAndSet(addr_ind, oldV, newV);
		}
	}
	
	public void push_back(E e){
		Descriptor<E> desc;
		Descriptor<E> newd;
		do{
			desc=descriptor.get();
			desc.completeWrite();
			
			int position = desc.size +FIRST_BUCKET_SIZE;
			int zeroNumPos = Integer.numberOfLeadingZeros(position);
			int zeroNumFirst = 0;
			int bucketInd = zeroNumFirst - zeroNumPos;
			if (buckets.get(bucketInd)==null){
				int newLen = 2 * buckets.get(bucketInd - 1).length();
				
				System.out.println("New Length is:" + newLen);
				buckets.compareAndSet(bucketInd, null, 
						new AtomicReferenceArray<E>(newLen));
			}
			int idx = (0x80000000>>>zeroNumPos)^position;
			newd = new Descriptor<>(desc.size +1, new WriteDescriptor<E>(
					buckets.get(bucketInd),idx,null,e));
			
			
		}while(!descriptor.compareAndSet(desc,newd));
	}
}
