package myUtil;

public class Handle {
	//一定要抛出异常，
	//在接口中定义异常，就是告诉调用者，必须在运行方法后处理异常。如果不定义的话。所有的异常必须由接口的实现类 自己处理，这在很多时候并不合适。
	@FunctionalInterface
	public interface RunnableEx {
		public abstract void run() throws Exception;
	}

	//接受一个可抛异常的接口,把这个借口套在try catch中，返回这个lambda表达式	
	public static Runnable uncheck(RunnableEx rx){
		return ()->{
			try{
				rx.run();
			}catch(Exception e){
				
			}
		};
	}
}
