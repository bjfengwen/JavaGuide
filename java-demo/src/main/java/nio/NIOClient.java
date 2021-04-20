package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient {
	private int blockSize = 4096;
	private ByteBuffer sendBuffer = ByteBuffer.allocate(blockSize);
	private ByteBuffer receiveBuffer = ByteBuffer.allocate(blockSize);
	private final static InetSocketAddress SOCKET_ADDRESS = new InetSocketAddress("127.0.0.1", 3999);
	private int flag;
	private Selector selector;

	public NIOClient() throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		// 设置是否阻塞
		socketChannel.configureBlocking(false); 
		// 打开选择器
		selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		socketChannel.connect(SOCKET_ADDRESS);
	}

	// 监听
	public void listen() throws IOException {
		while (true) {
			selector.select();
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				handleKey(selectionKey);
			}
			selectionKeys.clear();
		}
	}

	private void handleKey(SelectionKey selectionKey) throws IOException { 
		SocketChannel client = null;
		String reciveText;
		String sendText;
		int count = 0;
		if (selectionKey.isConnectable()) {
			System.out.println("client connet");
			client = (SocketChannel) selectionKey.channel();
			if (client.isConnectionPending()) {
				client.finishConnect();
				System.out.println("客户端完成连接操作");
				sendBuffer.clear();
				sendBuffer.put("Hello ,Server".getBytes());
				sendBuffer.flip();
				client.write(sendBuffer);
			}
			client.register(selector, SelectionKey.OP_READ);
		} else if (selectionKey.isReadable()) {
			client = (SocketChannel) selectionKey.channel();
			receiveBuffer.clear();
			count = client.read(receiveBuffer);
			if (count > 0) {
				reciveText = new String(receiveBuffer.array(), 0, count);
				System.out.println("客户端接收服务端的信息:" + reciveText);
				client.register(selector, SelectionKey.OP_WRITE);
			}
		} else if (selectionKey.isWritable()) {
			sendBuffer.clear();
			client = (SocketChannel) selectionKey.channel();
			sendText = "message send to server" + (++flag);
			sendBuffer.put(sendText.getBytes());
			sendBuffer.flip();
			client.write(sendBuffer);
			System.out.println("客户端发送数据给服务端:" + sendText);
			client.register(selector, SelectionKey.OP_READ);
		}
	}

	public static void main(String[] args) throws IOException {
		NIOClient nioServer = new NIOClient( );
		nioServer.listen();
	}
}
