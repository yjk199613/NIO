/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞NIO
 *
 * @author yangjikang
 * @date 2019/7/10 17:56
 * @modified By yangjikang
 */
public class demo09 {
    @Test
    public void Client() throws IOException, InterruptedException {
        //获取通道
        SocketChannel open = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8989));

        RandomAccessFile read = new RandomAccessFile("1.jpg", "rw");
        FileChannel channel = read.getChannel();

        //分配指定大小的缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(1024);

        //读取本地文件，并发送到服务器
        while (channel.read(allocate) != -1) {
            allocate.flip();
            open.write(allocate);
            allocate.clear();
        }
        open.shutdownOutput();
        //接收服务端的反馈
        while (open.read(allocate) != -1) {
            System.out.println("接收服务端反馈");
            allocate.flip();
            System.out.println(new String(allocate.array(), 0, allocate.limit()));
            allocate.clear();
        }
        read.close();
        open.close();

    }

    @Test
    public void Server() throws IOException {
        //获取通道
        ServerSocketChannel open = ServerSocketChannel.open();

        RandomAccessFile read = new RandomAccessFile("18.jpg", "rw");
        FileChannel channel = read.getChannel();

        //绑定连接
        open.bind(new InetSocketAddress(8989));

        //获取客服端连接的通道
        SocketChannel accept = open.accept();

        //分配一个指定大小的缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(1024);

        //接受客服端的数据保存到本地
        while (accept.read(allocate) != -1) {
            allocate.flip();
            channel.write(allocate);
            allocate.clear();
        }
        allocate.put("服务端接收数据成功！".getBytes());
        allocate.flip();
        accept.write(allocate);
        accept.shutdownOutput();
        //关闭通道
        channel.close();
        accept.close();
        open.close();
    }
}
