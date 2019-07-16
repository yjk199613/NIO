/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

/**
 * @author yangjikang
 * @date 2019/7/12 14:27
 * @modified By yangjikang
 */
public class demo14 {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();

        serverSocketChannel.bind(new InetSocketAddress(8899));

        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        int select = selector.select();

        boolean a = select > 0;

        while (a) {
            System.out.println("select"+select);
            System.out.println("a:" + a);
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    System.out.println("连接信息////");
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (next.isReadable()) {
                    System.out.println("读取信息////");
                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    channel.read(byteBuffer);
                    System.out.println("来自客服端传输的数据内容:" + new String(byteBuffer.array()));
                    channel.close();
                }
                iterator.remove();
            }
        }
    }
}
