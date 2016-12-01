package myUtil;

import java.util.function.Function;
//写一个测试工具，参数是一个Function<Object,Object>和Object,返回 这个函数运行10次的最快时间
public class MeasureSpeed {
	public static long perform(Function<Object,Object> func,Object param){
		long  fastest =  Long.MAX_VALUE;
		for(int i = 0;i<10;i++){
			long start = System.nanoTime();
			Object result = func.apply(param);
			long duration = (System.nanoTime()-start)/1_000_000;
			System.out.println("resulr="+result);
			if(duration < fastest)  fastest = duration;
		}
		return  fastest;
	}
}
