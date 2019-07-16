/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;


import java.nio.ByteBuffer;

/**
 *
 * 一、缓冲区（Buffer）: 在Java NIO 中负责数据的存取。缓冲区就数组。用于存储不同数据类型
 *
 *根据数据类型不同（Boolean 除外），提供相应类型的缓冲区:
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FoaltBuffer
 * DoubleBuffer
 *
 * 上述缓冲区的管理方式几乎一致，通过  allocate()  获取缓冲区
 *
 * 二、缓冲区获取数据的两个核心的方法:
 * put(): 存入数据到缓冲区
 * get(): 从缓冲区获取数据
 *
 * 三、缓冲区中的四个核心的属性:
 * capacity: 容量, 表示缓冲区中最大存储的容量。一旦声明,不可改变。
 * limit: 界限, 表示缓冲区中可以操作数据的大小。（limit 后面的数据不能进行读写）
 * position: 位置, 表示缓冲区中正在操作数据的位置。
 * mark: 标记, 表示记录当前position的位置。可以通过 reset() 恢复到 mark 的位置。
 *
 * 0 <= mark <= position <= limit <= capacity
 *
 * @author yangjikang
 * @date 2019/7/9 16:30
 * @modified By yangjikang
 */
public class demo01 {
    public static void main(String[] args) {

        String s = "abcdef";
        //1.分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(10);
        System.out.println("==================allocate====================");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //2.利用put()存入数据到缓冲区
        buf.put(s.getBytes());
        System.out.println("==================put()====================");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //3.切换读取数据模式
        buf.flip();
        System.out.println("==================flip()====================");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //4.利用get()方法读取缓冲区中的数据
        byte[] temp = new byte[buf.limit()];
        buf.get(temp);
        System.out.println("==================get()====================");
        System.out.println(new String(temp,0,temp.length));
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //5.rewind() : 可重复读
        buf.rewind();
        System.out.println("==================rewind()====================");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //6.clear() : 清空缓冲区,但是缓冲区里的数据还存在，只是处于“被遗忘”状态
        buf.clear();
        System.out.println("==================clear()====================");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println((char) buf.get());

    }
}
