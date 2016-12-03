package actionInConcurrency.chapt2;

public class JoinMain {
	public  volatile static int i=0;
	public static class AddThread extends Thread{
		@Override
		public void run(){
			for(i=0;i<10000000;i++);
		}
	}
	public static void main(String[] args) throws InterruptedException {
		AddThread  ad=new AddThread();
		ad.start();
		ad.join();//表示无线等待，直到目标线程结束
		for(int j=0;j<50;j++){
			System.out.println(i);
		}
	}
}
