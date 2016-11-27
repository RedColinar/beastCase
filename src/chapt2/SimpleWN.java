package chapt2;

import java.time.LocalTime;

public class SimpleWN {
	final static Object object=new Object();
	public static class T1 extends Thread{
		public void run(){
			synchronized (object){
				System.out.println("T1 START"+LocalTime.now());
				try {
					object.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("T1 END IN SYNCHRONIZED"+LocalTime.now());
			}
			System.out.println("T1 END OUT OF SYNCHRONIZED");
		}
	}
	
	public static class T2 extends Thread{
		public void run(){
			synchronized (object){
				System.out.println("T2 START"+LocalTime.now());
				object.notify();
				System.out.println("T2 END "+LocalTime.now());
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Thread t1 =new T1();
		Thread t2 =new T2();
		t1.start();
		t2.start();
	}
}
