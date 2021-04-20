package test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.Properties;

public class Test {
	public static void main(String[] args) throws Exception {
		String file = "c:/stream.txt"; 
 
//		 String charset = "gbk"; 
//		 // 写字符换转成字节流
//		 FileOutputStream outputStream = new FileOutputStream(file); 
//		 OutputStreamWriter writer = new OutputStreamWriter(   outputStream); 
//		 try { 
//		    writer.write("123456"); 
//		 } finally { 
//		    writer.close(); 
//		 } 
//		 // 读取字节转换成字符
//		 FileInputStream inputStream = new FileInputStream(file); 
//		 InputStreamReader reader = new InputStreamReader( inputStream); 
//		 StringBuffer buffer = new StringBuffer(""); 
//		 char[] buf = new char[3]; 
//		 int count = 0; 
//		 try { 
//		    while ((count = reader.read(buf)) != -1) {
//		    	System.out.println("--"+count);
//		        buffer.append(buf, 0, count);  
//		    } 
//		 } finally { 
//		    reader.close(); 
//		 }
//		  
//		 System.out.println(buffer);
//		Properties properties= System.getProperties();
//		Enumeration<Object> keys = properties.keys();
//		while(keys.hasMoreElements()){
//			String key=(String)keys.nextElement();
//			System.out.println(key+"="+properties.getProperty(key));
//		}
		
		/*RandomAccessFile aFile = new RandomAccessFile(file, "rw");
		FileChannel inChannel = aFile.getChannel();

		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {

			System.out.println("Read " + bytesRead);
			buf.flip();

			while (buf.hasRemaining()) {
				System.out.println((char) buf.get());
				// System.out.println(new String(buf.array(),"gbk"));
			}

			buf.clear();
			bytesRead = inChannel.read(buf);
		}
		aFile.close();*/
		ByteBuffer buffer = ByteBuffer.allocate(10);
		System.out.println("capacity="+buffer.capacity());
		System.out.println("position="+buffer.position());
		System.out.println("limit="+buffer.limit());
		buffer.put("fengwen".getBytes());
		System.out.println("capacity="+buffer.capacity());
		System.out.println("position="+buffer.position());
		System.out.println("limit="+buffer.limit());
		//buffer.flip();
		buffer.flip(); 
		System.out.println("buffer.flip() after");
		System.out.println("capacity="+buffer.capacity());
		System.out.println("position="+buffer.position());
		System.out.println("limit="+buffer.limit());
		byte b = buffer.get();
		System.out.println("得到一个字节"+(char)b);
		System.out.println("capacity="+buffer.capacity());
		System.out.println("position="+buffer.position());
		System.out.println("limit="+buffer.limit());
		//buffer.flip();
		 
		buffer.put("!123".getBytes());
		System.out.println("---------");
		System.out.println("capacity="+buffer.capacity());
		System.out.println("position="+buffer.position());
		System.out.println("limit="+buffer.limit()); 
		b = buffer.get();
		System.out.println("得到一个字节"+(char)b);//数据不正确啊
		System.out.println("capacity="+buffer.capacity());
		System.out.println("position="+buffer.position());
		System.out.println("limit="+buffer.limit());
	}
}
