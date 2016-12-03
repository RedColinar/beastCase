package actionInConcurrency.chapt3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//计划任务
public class ScheduledExecutorServiceDemo {
	public static void main(String[] args) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
		ses.scheduleAtFixedRate(myUtil.MakeRunner.uncheck(()->{
			Thread.sleep(1000);
			System.out.println(System.currentTimeMillis()/1000);
		}), 0, 2, TimeUnit.SECONDS);
	}
}
