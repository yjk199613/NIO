/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * @author yangjikang
 * @date 2019/7/16 10:54
 * @modified By yangjikang
 */
public class ChatServerDemo16 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();

        serverSocketChannel.bind(new InetSocketAddress(8899));

        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("聊天程序服务端准备就绪...");

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //连接事件
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);

                    System.out.println(socketChannel.getRemoteAddress().toString().substring(1) + "上线了...");
                }
                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    if (socketChannel.read(byteBuffer) > 0) {
                        String msg = new String(byteBuffer.array());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String format = simpleDateFormat.format(new Date());
                        System.out.println("[" + format + "] - > " + msg);
                        System.out.println("服务器开始发送广播...");
                        for (SelectionKey key : selector.keys()) {
                            Channel channel = key.channel();
                            if (channel instanceof SocketChannel && socketChannel != channel) {
                                SocketChannel tempChannel = (SocketChannel) channel;
                                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                                tempChannel.write(buffer);
                            }
                        }
                    }else{
                        System.out.println(socketChannel.getRemoteAddress().toString().substring(1) + "关闭连接...");
                        selectionKey.cancel();
                    }
                }
                iterator.remove();
            }
        }
    }
}
