/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * 四、通道直接的数据传输
 * transferTo()
 * transferFrom()
 *
 * @author yangjikang
 * @date 2019/7/10 14:37
 * @modified By yangjikang
 */
public class demo05 {
    public static void main(String[] args) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            //使用直接缓冲区完成文件的复制（内存映射文件）
            inputChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            outputChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

//            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            outputChannel.transferFrom(inputChannel,0,inputChannel.size());

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
}
