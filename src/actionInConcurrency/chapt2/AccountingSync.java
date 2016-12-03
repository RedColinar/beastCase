package actionInConcurrency.chapt2;

public class AccountingSync implements Runnable{
	static AccountingSync instant =new AccountingSync();
	static int i=0;
	public static synchronized void increase(){
		i++;
	}
/*	@Override
	public void run() {
		for(int j=0;j<10000000;j++){
			synchronized(instant){
				i++;
			}
		}
	}*/
/*	@Override
	public void run() {
		for(int j=0;j<10000000;j++){
			increase();
		}
		
	}*/
	@Override
	public void run() {
		for(int j=0;j<10000000;j++){
			increase();
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		
		Thread a1 =new Thread(new AccountingSync());
		Thread a2 =new Thread(new AccountingSync());
		a1.start();a2.start();
		a1.join();a2.join();
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Long.MAX_VALUE);
		System.out.println(i);
	}

}
