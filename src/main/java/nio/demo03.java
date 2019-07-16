/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 一、通道（Channel）: 用于源节点和目标节点的连接。在Java NIO中负责缓冲区中数据的传输。Channel本身不存储数据，
 * 需要配合缓冲区（Buffer）进行数据的传输
 * <p>
 * 二、通道的主要是实现类
 * java.nio.channels.channel 接口：
 * |--FileChannel
 * |--SocketChannel
 * |--ServerSocketChannel
 * |--DatagramChannel
 * <p>
 * 三、获取通道
 * 1.Java 针对支持通道的类提供了 getChannel()方法
 * 本地IO:
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * <p>
 * 网络IO:
 * Socket
 * ServerSocket
 * Datagram
 * <p>
 * 2.在JDK1.7中的NIO.2针对各个通道提供了静态方法open()
 * 3.在JDK1.7中的NIO.2的Files 工具类 newByteChannel()
 *
 * @author yangjikang
 * @date 2019/7/10 13:47
 * @modified By yangjikang
 */
public class demo03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        int count = 0;
        try {
            fileInputStream = new FileInputStream("1.jpg");
            fileOutputStream = new FileOutputStream("2.jpg");

            //step.1 获取通道
            inChannel = fileInputStream.getChannel();
            outChannel = fileOutputStream.getChannel();

            //step.2 分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            //step.3 将通道中数据放入缓冲区中
            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();//切换读模式

                //step.4 将缓冲区中的数据读入通道中
                outChannel.write(byteBuffer);
                byteBuffer.clear();//清空当前缓冲区
                count++;
            }
            System.out.println(count);
            System.out.println(inChannel);
            System.out.println(outChannel);
            System.out.println(fileInputStream);
            System.out.println(fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inChannel.close();
            outChannel.close();
            fileInputStream.close();
            fileOutputStream.close();
        }

    }
}
