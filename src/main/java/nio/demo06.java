/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 五、分散(Scatter)和聚集(Gather)
 * 分散读取(Scattering Reads): 将通道中的数据分散到多个缓冲区中
 * 聚集写入(Gathering Writes): 将多个缓冲区中的数据聚集到通道中
 *
 * @author yangjikang
 * @date 2019/7/10 14:53
 * @modified By yangjikang
 */
public class demo06 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile read = new RandomAccessFile("1.txt", "rw");

        //step.1 获取通道
        FileChannel channel = read.getChannel();

        //step.2 分配指定大小的缓冲区
        ByteBuffer allocate1 = ByteBuffer.allocate(50);
        ByteBuffer allocate2 = ByteBuffer.allocate(100);

        //step.3 分散读取数据到多个缓冲区
        ByteBuffer[] byteBuffers = {allocate1, allocate2};
        channel.read(byteBuffers);
        for (ByteBuffer byteBuffer : byteBuffers) {
            byteBuffer.flip();
        }

        //step.4 打印结果
        System.out.println(new String(byteBuffers[0].array(), 0, byteBuffers[0].limit()));
        System.out.println("========================================");
        System.out.println(new String(byteBuffers[1].array(), 0, byteBuffers[1].limit()));

        //聚集写入
        RandomAccessFile write = new RandomAccessFile("3.txt", "rw");
        FileChannel writeChannel = write.getChannel();
        writeChannel.write(byteBuffers);
    }
}
