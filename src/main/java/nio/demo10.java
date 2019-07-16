/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.util.Iterator;

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
public class demo10 {

    //客户端
    @Test
    public void client() throws IOException {
        //获取通道
        SocketChannel open = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8899));

        //切换成非阻塞模式
        open.configureBlocking(false);

        //分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //发送数据给服务端
        byteBuffer.put("来自客户端的一条数据".getBytes());
        byteBuffer.flip();
        open.write(byteBuffer);
        byteBuffer.clear();

        //关闭通道
        open.close();
    }

    //服务端
    @Test
    public void server() throws IOException {
        //获取通道
        ServerSocketChannel open = ServerSocketChannel.open();

        //切换非阻塞模式
        open.configureBlocking(false);

        //绑定连接
        open.bind(new InetSocketAddress(8899));

        //获取选择器
        Selector selector = Selector.open();

        //将通道注册到选择器上,并且指定”监听接收事件“
        open.register(selector, SelectionKey.OP_ACCEPT);

        //轮询获取选择器上已经准备就绪的事件
        while (selector.select() > 0){
            //获取当前选择器所有已经准备就绪的事件key
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                //获取准备就绪的事件
                SelectionKey next = iterator.next();

                //判断具体是什么事件
                if(next.isAcceptable()){//接收事件
                    //获取客户端连接
                    SocketChannel acceptChannel = open.accept();

                    //切换非阻塞模式
                    acceptChannel.configureBlocking(false);

                    //将通道注册到选择器上
                    acceptChannel.register(selector,SelectionKey.OP_READ);
                }else if(next.isReadable()){//读取事件
                    //获取选择器上”读就绪“状态的通道
                    SocketChannel readChannel = (SocketChannel)next.channel();

                    //读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024); //创建一个指定大小的缓冲器

                    while (readChannel.read(byteBuffer) != -1 ){
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(),0,byteBuffer.limit()));
                        byteBuffer.clear();
                    }
                }
                //取消选择键(SelectionKey)
                iterator.remove();
            }


        }
    }
}
