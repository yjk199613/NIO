/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * @author yangjikang
 * @date 2019/7/11 11:43
 * @modified By yangjikang
 */
public class demo11 {

    @Test
    public void send() throws IOException {
        DatagramChannel dc = DatagramChannel.open();

        dc.configureBlocking(false);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(("来自send的数据:你好").getBytes());
        byteBuffer.flip();
        dc.send(byteBuffer, new InetSocketAddress("127.0.0.1", 8899));
        byteBuffer.clear();
        dc.close();
    }

    @Test
    public void receive() throws IOException {

        DatagramChannel dc = DatagramChannel.open();

        dc.configureBlocking(false);

        dc.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();

        dc.register(selector, SelectionKey.OP_READ);

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isReadable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    dc.receive(byteBuffer);

                    byteBuffer.flip();

                    System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));

                    byteBuffer.clear();
                }
            }
            iterator.remove();
        }
    }
}
