package actionInConcurrency.chapt5.future;

public class RealData implements Data{
	protected final String result;
	
	public RealData(String result) {
		StringBuffer sr =new StringBuffer();
		for(int i = 0;i<10;i++){
			sr.append(result);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.result = sr.toString();
	}

	@Override
	public String getResult() {
		return result;
	}

}
