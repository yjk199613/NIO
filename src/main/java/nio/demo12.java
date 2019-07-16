/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 3
 *
 * @author yangjikang
 * @date 2019/7/11 11:58
 * @modified By yangjikang
 */
public class demo12 {

    @Test
    public void pipe() throws IOException {
        //获取管道
        Pipe pipe = Pipe.open();

        //将缓冲区的数据写入管道
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        Pipe.SinkChannel sink = pipe.sink();
        byteBuffer.put("向单向管道发送数据".getBytes());
        byteBuffer.flip();
        sink.write(byteBuffer);

        //读取缓冲区里面的数据
        Pipe.SourceChannel source = pipe.source();

        //创建一个缓冲区用来接收数据
        ByteBuffer accpetByteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.flip();
        source.read(accpetByteBuffer);

        System.out.println(new String(accpetByteBuffer.array(),0,accpetByteBuffer.limit()));
    }

    @Test
    public void server() throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();

        open.bind(new InetSocketAddress(8899));

        SocketChannel accept = open.accept();

        ByteBuffer accpetByteBuffer = ByteBuffer.allocate(1024);

        while (accept.read(accpetByteBuffer) != -1){
            accpetByteBuffer.flip();
            System.out.println(new String(accpetByteBuffer.array(),0,accpetByteBuffer.limit()));
            accpetByteBuffer.clear();
        }
        accept.close();
        open.close();
    }
}
