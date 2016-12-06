package actionInConcurrency.chapt5.futureInJDK;

import java.util.concurrent.Callable;

public class RealData implements Callable<String> {
	private String para;
	public RealData(String para){
		this.para = para;
	}
	@Override
	public String call() throws Exception {
		StringBuffer sr =new StringBuffer();
		for(int i = 0;i<10;i++){
			sr.append(para);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return sr.toString();
	}
}
