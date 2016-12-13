package actionInConcurrency.chapt5.NIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//基于Socket的服务端的多线程模式
//Echo服务器
public class MultiThreadEchoServer {
	//线程池用来处理每一个客户端连接
	private static ExecutorService tp = Executors.newCachedThreadPool();
	//定义线程，由一个客户端Socket构造成，任务是读取这个Socket的内容并将其返回，返回后任务完成，客户端Socket就正常关闭
	static class HandleMsg implements Runnable{
		//java.net.Socket包下的
		Socket clientSocket;
		//构造器
		public HandleMsg(Socket clientSocket) {
			super();
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			BufferedReader is = null;
			PrintWriter os = null;
			try {
				is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				os = new PrintWriter(clientSocket.getOutputStream(),true);
				//从InputStream当中读取客户端所发送的数据
				String inputLine = null;
				long b = System.currentTimeMillis();
				while((inputLine = is.readLine())!=null){
					os.println(inputLine);
				}
				long e = System.currentTimeMillis();
				System.out.println("spend:"+(e-b)+"ms");
						
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(is!=null) is.close();
					if(os!=null) os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//主要任务是在8080端口上进行等待，一旦有新的客户端连接，他就根据这个连接创建HandleMsg线程进行处理
	public static void main(String[] args) throws IOException {
		ServerSocket echoServer = null;
		Socket clientSocket = null;
		try {
			echoServer = new ServerSocket(8000);
			while(true){
				clientSocket = echoServer.accept();
				System.out.println(clientSocket.getRemoteSocketAddress() + "connect!");
				tp.execute(new HandleMsg(clientSocket));
			}
			
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
}
