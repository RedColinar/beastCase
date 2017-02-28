package webFoundation.httpSocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;


public class HttpServer {
	public static void main(String[] args) throws IOException {
		//创建ServerSocketChannel，监听端口1111
				ServerSocketChannel ssc = ServerSocketChannel.open();
				ssc.socket().bind(new InetSocketAddress(1111));
				//设置为非阻塞
				ssc.configureBlocking(false);
				//为ssc注册 选择器
				Selector selector = Selector.open();
				ssc.register(selector, SelectionKey.OP_ACCEPT);
				//创建处理器
				while(true){
					if(selector.select(3000)  == 0){
						System.out.println("等待请求超时……");
						continue;
					}
					System.out.println("请求处理……");
					//获取待处理的SelectionKey
					Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
					
					while(keyIter.hasNext()){
						SelectionKey key = keyIter.next();
						new Thread(new HttpHandler(key)).run();
					}
			}
	}
	private static class HttpHandler implements Runnable{
		private int bufferSize = 1024;
		private String localCharset = "UTF-8";
		private SelectionKey key;
		
		public HttpHandler(SelectionKey key){
			this.key = key;
		}
		
		public void handleAccept() throws IOException{
			SocketChannel clientChannel = ((ServerSocketChannel)key.channel()).accept();
			clientChannel.configureBlocking(false);
			clientChannel.register(key.selector(), 
					SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
			
		}
		public void handleRead() throws IOException{
			SocketChannel sc = (SocketChannel)key.channel();
			ByteBuffer buffer = (ByteBuffer)key.attachment();
			buffer.clear();
			if(sc.read(buffer)==-1){
				sc.close();
			}else{
				buffer.flip();
				String receivedString = Charset.forName(localCharset).newDecoder()
						.decode(buffer).toString();
				String[] requestMessage = receivedString.split("\r\n");
				for(String s:requestMessage){
					System.out.println(s);
					//遇到空行说明报文头已经打印完
					if(s.isEmpty())
						break;
				}
				//控制台打印首行信息
				String[] firstLine = requestMessage[0].split(" ");
				System.out.println();
				System.out.println("Method:\t"+firstLine[0]);
				System.out.println("url:\t"+firstLine[1]);
				System.out.println("Http Version:\t"+firstLine[2]);
				System.out.println();
				//返回客户端
				StringBuilder sendString = new StringBuilder();
				sendString.append("Http/1.1 200 OK\r\n");//响应报文首行，200表示处理成功
				sendString.append("Content-Type:text/html;Charset="+
						localCharset+"\r\n");
				sendString.append("\r\n");
				sendString.append("<html><head><title>显示报文</title></head><body>");
				sendString.append("接收到请求报文是<br/>");
				for(String s:requestMessage){
					sendString.append(s + "<br/>");
				}
				sendString.append("</body></html>");
				buffer = ByteBuffer.wrap(sendString.toString().getBytes(localCharset));
				sc.write(buffer);
				sc.close();
			}
		}
		@Override
		public void run() {
			try{
				if(key.isAcceptable()){
					handleAccept();
				}
				if(key.isReadable()){
					handleRead();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
}
