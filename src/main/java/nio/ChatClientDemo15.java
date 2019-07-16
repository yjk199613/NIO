/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 *
 * 测试测试测试测试测试测试测试
 * @author yangjikang
 * @date 2019/7/16 10:50
 * @modified By yangjikang
 */
public class ChatClientDemo15 {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",8899));

        socketChannel.configureBlocking(false);

        if (!socketChannel.isConnected()) {
            while (!socketChannel.finishConnect()){
                continue;
            }
        }
        System.out.println(socketChannel.getLocalAddress().toString().substring(1) + "成功连接到服务器...");

        new Thread(){
            @Override
            public void run() {
                while (true){
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int read = 0;
                    try {
                        read = socketChannel.read(byteBuffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            socketChannel.socket().close();
                            socketChannel.close();
                            return;
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (read>0){
                        String s = new String(byteBuffer.array());
                        System.out.println("接收到数据"+s.trim());
                    }
                }
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.next();
            if (msg.equalsIgnoreCase("bye")) {
                socketChannel.close();
                return;
            }
            msg = socketChannel.getLocalAddress().toString().substring(1) + " 说了:" + msg;
            ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
            socketChannel.write(wrap);
        }
    }
}
