package actionInConcurrency.chapt5.future;
//Future模式
public class FutureData implements Data{
	//FutureData是RealData的包 装 
	protected RealData realdata = null;
	protected boolean isReady = false;
	
	public  synchronized void setRealData(RealData realdata){
		if(isReady){
			return;
		}
		this.realdata  =  realdata;
		isReady = true;
		//一旦RealData已经被注入，通知getResult()
		notifyAll();
	}
	

	@Override
	public synchronized String getResult() {
		while(!isReady){
			try {
				wait();//一直等待直到realdata被注入
			} catch (InterruptedException e) {
			}
		}
		return realdata.result;
	}
}
