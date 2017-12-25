package com.andlp.lib.my;

 import java.net.Socket;
/**
 * @description 服务的启动的线程类
 * @author csc
 */
public class DstServiceImpl implements Runnable {
    Socket socket = null;
    public DstServiceImpl(Socket s) {
        this.socket = s;
    }
    public void run() {
        try {
            int index = 1;
            while (true) {
                System.out.println("服务端 开始！");
                // 5秒后中断连接
                if (index > 10) {
                    socket.close();
                    System.out.println("服务端已经关闭链接！");
                    break;
                }
                index++;
                System.out.println("服务端 循环中！"+index);
//                Thread.sleep(1 * 1000);//程序睡眠1秒钟
//                System.out.println("服务端 循环中！"+index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
