/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import java.nio.ByteBuffer;

/**
 * mark: 标记, 表示记录当前position的位置。可以通过 reset() 恢复到 mark 的位置。
 *
 * @author yangjikang
 * @date 2019/7/10 9:58
 * @modified By yangjikang
 */
public class demo02 {
    public static void main(String[] args) {
        String str = "1234567";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());

        buffer.flip();

        //创建临时接收缓冲区数据的数组
        byte[] temp = new byte[buffer.limit()];
        //读取三个字符到temp从0号下标开始放
        buffer.get(temp, 0, 3);
        System.out.println("第一次读取内容: " + new String(temp, 0, temp.length));

        System.out.println("第一次读取后的position位置: " + buffer.position());
        //标记当前position位置
        buffer.mark();

        //读取两个字符到temp从1号下标开始放
        buffer.get(temp, 1, 2);
        System.out.println("第二次读取内容: " + new String(temp, 0, temp.length));

        System.out.println("第二次读取后的position位置: " + buffer.position());

        //通过reset恢复到mark位置
        buffer.reset();
        System.out.println("通过reset恢复后的position位置: " + buffer.position());
    }
}
