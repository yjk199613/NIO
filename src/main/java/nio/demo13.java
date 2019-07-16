/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 测试flip()
 *
 * @author yangjikang
 * @date 2019/7/12 9:35
 * @modified By yangjikang
 */
public class demo13 {
    public static void main(String[] args) throws IOException {

        SocketChannel open = SocketChannel.open(new InetSocketAddress("127.0.0.1",8899));

        open.configureBlocking(false);

        String str = "hello word";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        open.write(byteBuffer);
        open.close();
    }
}
