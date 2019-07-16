/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author yangjikang
 * @date 2019/7/10 14:16
 * @modified By yangjikang
 */
public class demo04 {
    public static void main(String[] args) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            //使用直接缓冲区完成文件的复制（内存映射文件）
            inputChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            outputChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

            //内存映射文件
            MappedByteBuffer inputMap = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputChannel.size());
            MappedByteBuffer outputMap = outputChannel.map(FileChannel.MapMode.READ_WRITE, 0, inputChannel.size());

            //直接对缓冲区进行数据的读写操作
            byte[] bytes = new byte[inputMap.limit()];
            inputMap.get(bytes);
            outputMap.put(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
}
