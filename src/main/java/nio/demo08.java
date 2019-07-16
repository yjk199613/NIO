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

/**
 * 阻塞和非阻塞
 *
 * 一、使用NIO完成网路通信的三个核心：
 * 1.通道：负责连接
 *  java.nio.channels.channel 接口：
 *  |-- SelectableChannel
 *      |-- SocketChannel
 *      |-- ServerSocketChannel
 *      |-- DatagramChannel
 *
 *      |-- Pipe.SinkChannel
 *      |-- Pipe.SourceChannel
 *
 * 2.缓冲区：负责数据存储
 *
 * 3.选择器： 是 SelectableChannel的多路复用器。用于监听SelectableChannel的IO状况。
 *
 * @author yangjikang
 * @date 2019/7/10 16:36
 * @modified By yangjikang
 */
public class demo08 {

    @Test
    public void Client() throws IOException {
        //获取通道
        SocketChannel open = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8989));

        RandomAccessFile read = new RandomAccessFile("1.jpg", "rw");
        FileChannel channel = read.getChannel();

        //分配指定大小的缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(1024);

        //读取本地文件，并发送到服务器
        while(channel.read(allocate) != -1){
            allocate.flip();
            open.write(allocate);
            allocate.clear();
        }
        read.close();
        open.close();

    }
    @Test
    public void Server() throws IOException {
        //获取通道
        ServerSocketChannel open = ServerSocketChannel.open();

        RandomAccessFile read = new RandomAccessFile("15.jpg", "rw");
        FileChannel channel = read.getChannel();

        //绑定连接
        open.bind(new InetSocketAddress(8989));

        //获取客服端连接的通道
        SocketChannel accept = open.accept();

        //分配一个指定大小的缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(1024);

        //接受客服端的数据保存到本地
        while(accept.read(allocate) != -1){
            allocate.flip();
            channel.write(allocate);
            allocate.clear();
        }

        //关闭通道
        channel.close();
        open.close();
    }
}
