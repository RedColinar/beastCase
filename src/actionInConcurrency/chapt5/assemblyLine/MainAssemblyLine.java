package actionInConcurrency.chapt5.assemblyLine;

import java.text.MessageFormat;

public class MainAssemblyLine {
	public static void main(String[] args) {
		ThreadGroup tg = new ThreadGroup("AssemblyLine");
		
		new Thread(tg,new Plus(),"plus").start();
		new Thread(tg,new Multiply(),"multiply").start();
		new Thread(tg,new Div(),"div").start();
		
		int i = 1;
		int j = 1;
		for(i = 1;i<= 1000;i++){
			for(j = 1;j<=1000;j++){
				Msg msg = new Msg(i,j,
						MessageFormat.format("(({0}+{1}))*{2}/2",i,j,i));
				Plus.bq.add(msg);
			}
		}
	}
}
