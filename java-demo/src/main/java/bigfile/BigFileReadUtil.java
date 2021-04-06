package bigfile;

import java.io.*;  
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
public class BigFileReadUtil {
	 	
		public static void main(String[] args) throws Exception {
			String filePath= BigFileReadUtil.class.getResource("/113MB.txt").getFile();
			//readFileByNio(filePath);//861毫秒
			readFile(filePath);//9284毫秒
		}

		/**
		 * @throws FileNotFoundException
		 * @throws IOException
		 */
		private static void readFile(String filePath) throws FileNotFoundException, IOException {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath)); 
			String str = null; 
			long time = System.currentTimeMillis(); 
			List<String> list = new ArrayList<String>(); 
			while((str = bufferedReader.readLine()) != null){ 
				list.add(str); 
			} 
			bufferedReader.close();
			System.out.println(System.currentTimeMillis() - time+"毫秒");
		}

		/**
		 * @throws FileNotFoundException
		 * @throws IOException
		 */
		private static void readFileByNio(String filePath) throws FileNotFoundException, IOException {
					 
			RandomAccessFile file = new RandomAccessFile(filePath, "r");
			FileChannel fileChannel = file.getChannel();
			ByteBuffer buffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());
			CharBuffer charBuffer = Charset.forName("gbk").decode(buffer);
			file.close();
			
			List<String> list = new ArrayList<String>();

			long time = System.currentTimeMillis();
			
			BufferedReader bufferedReader = new BufferedReader(new StringReader(charBuffer.toString()));
			String str = null;
			while((str = bufferedReader.readLine()) != null){ 
				list.add(str); 
			}
			
			System.out.println(System.currentTimeMillis()-time+"毫秒");
		}
		
}
