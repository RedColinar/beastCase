package actionInConcurrency.chapt2;

public class DaemonDemo {
	public static class DaemonT extends Thread{
		public void run(){
			 while(true){
				 System.out.println("DaemonT is on duty!");
				 try {
					Thread.sleep(999);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			 }
		}
	}
	public static class DaemonQ extends Thread{
		public void run(){
			 while(true){
				 System.out.println("DaemonQ is on duty!");
				 try {
					Thread.sleep(999);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			 }
		}
	}
	
	public static void main(String[] args) {
		Thread t =new DaemonT();
		Thread q= new DaemonQ();
		t.setDaemon(true);
		q.setDaemon(true);
		t.start();
		q.start();
		
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("You two can be off duty!");
	}
	
}
