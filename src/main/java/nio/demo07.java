/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;

/**
 * 六、字符集：Charset
 * 编码：字符串 --> 字节数组
 * 解码：字节数组 --> 字符串
 *
 * @author yangjikang
 * @date 2019/7/10 15:57
 * @modified By yangjikang
 */
public class demo07 {
    public static void main(String[] args) throws CharacterCodingException {
        //查看字符集有哪些
        SortedMap<String, Charset> stringCharsetSortedMap = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entries = stringCharsetSortedMap.entrySet();
        for (Map.Entry<String, Charset> entry : entries) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        //指定编码方式
        Charset gbk = Charset.forName("GBK");

        //获取编码器
        CharsetEncoder charsetEncoder = gbk.newEncoder();
        //获取解码器
        CharsetDecoder charsetDecoder = gbk.newDecoder();

        //创建字符缓冲区
        CharBuffer allocate = CharBuffer.allocate(1024);
        allocate.put("yangjikang");
        allocate.flip();

        //编码
        ByteBuffer encode = charsetEncoder.encode(allocate);
        for (int i = 0; i < encode.limit(); i++) {
            System.out.println(encode.get());
        }

        //解码
        encode.flip();
        CharBuffer decode = charsetDecoder.decode(encode);
        System.out.println(decode);

    }
}
