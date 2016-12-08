package actionInConcurrency.chapt5.assemblyLine;
//一个在线程间携带结果进行信息交换的载体
public class Msg {
	public double i;
	public double j;
	public String orgStr = null;
	public Msg(double i, double j, String orgStr) {
		super();
		this.i = i;
		this.j = j;
		this.orgStr = orgStr;
	}
	
}
