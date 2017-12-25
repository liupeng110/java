package com.andlp.lib.my;

/**
 * 717219917@qq.com      2017/12/22  16:46
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DstService extends Application {
    DropShadow shadow = new DropShadow();

    Socket socket=null;
    @Override
    public void start(Stage primaryStage) {

        final TextField notification = new TextField ();
        notification.setText("发送");
        notification.setLayoutX(50);
        notification.setLayoutY(50);
        notification.setPadding(new Insets(15));
        notification.setTranslateY(-100);
        notification.clear();

        Button btn = new Button();
        btn.setText("connect ");
        btn.setLayoutX(50);
        btn.setLayoutY(50);
        btn.setTranslateX(-100);
        btn.setTranslateY(-40);
        btn.setPadding(new Insets(15));
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                new Thread(new Runnable() { @Override public void run() { socket(); } }).start();//防止ui卡顿
//                test();
                client();

            }
        });


        Button send = new Button();
        send.setText("send");
        send.setLayoutX(50);
        send.setLayoutY(50);
        send.setTranslateX(-20);
        send.setTranslateY(-40);
        send.setPadding(new Insets(15));
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                sendExit(notification.getText());
                System.out.print("点击 send");
                for(Iterator<TestClient> it = list.iterator(); it.hasNext();    )    {
                  try{  it.next().send("发送消息");}catch (Throwable t){t.printStackTrace();}
                }
            }
        });

        Button discon = new Button();
        discon.setText("discon");
        discon.setLayoutX(50);
        discon.setLayoutY(50);
        discon.setTranslateX(50);
        discon.setTranslateY(-40);
        discon.setPadding(new Insets(15));
        discon.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                   System.out.print("点击 断开链接");
                for(Iterator<TestClient> it = list.iterator(); it.hasNext();    )    {
                      it.next().close();
                }
//                   sendExit("exit");
//                try{socket.close();}catch (Throwable t){t.printStackTrace();}
            }
        });


        StackPane root = new StackPane();
        root.getChildren().add(notification);
        root.getChildren().add(btn);
        root.getChildren().add(send);
        root.getChildren().add(discon);


        Scene scene =new Scene(root, 300, 250);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();







    }

 private  void sendExit(String str){
     try {
         OutputStream os = socket.getOutputStream();
         os.write(str.getBytes("GBK"));
     }catch (Exception e){e.printStackTrace();}
 }


  public static void main(String[] args) {
        launch(args);
    }

private void send(String str){
    OutputStream os = null;
    InputStream is = null;
    try {
        os = socket.getOutputStream();//
        System.out.println("正在发送信息...");
        os.write(str.getBytes());//发送信息
        os.flush();//注意:如果不使用flush方法,服务端将收到客户端发送的信息
        socket.shutdownOutput();
        is = socket.getInputStream();//
        byte[] buf = new byte[1024*8];//缓冲区
        String msg="";
        for(int len=is.read(buf);len>0;len=is.read(buf)){
            msg+=new String(buf, 0, len);
        }
        socket.shutdownInput();
        System.out.println("正在接收回复信息...");
        System.out.println("服务器返回的信息: "+msg);
        System.out.println("接收回复信息完成");
    } catch (Exception e) {
        e.printStackTrace();
    }finally{
        //关闭输入输出流
        try {os.close();} catch (Exception e) {e.printStackTrace(); }
        try {is.close();} catch (Exception e) {e.printStackTrace(); }
    }
}

    private void socket(){
        System.out.println("单机事件--进入socket" );
        try {
              socket = new Socket("127.0.0.1", 30000);
            socket.setKeepAlive(true);
            socket.setSoTimeout(10);
            while (true) {
                socket.sendUrgentData(0xFF); // 发送心跳包
                System.out.println(socket.isBound());
                System.out.println(socket.isClosed());
                System.out.println(socket.isConnected());
                System.out.println(socket.isInputShutdown());
                System.out.println(socket.isOutputShutdown());
                System.out.println("目前处于链接状态！");
                System.out.println(socket.isOutputShutdown());
                System.out.println("新连接:"+socket.getInetAddress()+":"+socket.getPort());
                System.out.println("------------我是分割线------------");

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));
                    String str = wt.readLine();
                    out.println(str);
                    out.flush();
                    if (str.equals("end")) {
                        break;
                    }
                    System.out.println(in.readLine());
            }


            Thread.sleep(3 * 1000);//线程睡眠3秒

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("单机事件--进入异常"+e.getLocalizedMessage() );
        }
    }

private void test(){
//    Socket socket=null;
    try {
        //对服务端发起连接请求
        socket=new Socket("localhost", 8080);

        //接受服务端消息并打印
        InputStream is=socket.getInputStream();
        byte b[]=new byte[1024];
        is.read(b);
        String bb= new String(b);
        System.out.println(bb.substring(0,bb.lastIndexOf("/n")));

        //给服务端发送响应信息
        OutputStream os=socket.getOutputStream();
        os.write("yes,I have received you message!".getBytes());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

 List<TestClient> list = new ArrayList();

private void client() {
    // 开启三个客户端，一个线程代表一个客户端
    for (int i = 0; i < 3; i++) {
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    TestClient client = TestClientFactory.createClient();
                    client.send(String.format("Hello,Server!I'm %d.这周末天气如何。", client.client.getLocalPort()));
                    client.receive();
                    list.add(client);
                } catch (Exception e) { e.printStackTrace(); }
            }
        }).start();
    }

}

    static class TestClientFactory {
        public static TestClient createClient() throws Exception { return new TestClient("127.0.0.1", 8080); }
    }

    /**
     * 测试客户端
     */
    static class TestClient {
        public TestClient(String host, int port) throws Exception {  // 与服务端建立连接
            this.client = new Socket(host, port);
            System.out.println("Cliect[port:" + client.getLocalPort() + "] 与服务端建立连接...");
        }
        private Socket client;
        private Writer writer;

        public void send(String msg) throws Exception {  // 建立连接后就可以往服务端写数据了
            if(writer == null) {
                writer = new OutputStreamWriter(client.getOutputStream(), "gbk");
            }
            writer.write(msg);
            writer.write("eof\n");
            writer.flush();// 写完后要记得flush
            System.out.println("Cliect[port:" + client.getLocalPort() + "] 消息发送成功");
        }

        public void receive() throws Exception { // 写完以后进行读操作
            Reader reader = new InputStreamReader(client.getInputStream(), "gbk");
            client.setSoTimeout(10*1000);// 设置接收数据超时间为10秒
            char[] chars = new char[64];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = reader.read(chars)) != -1) {
                sb.append(new String(chars, 0, len));
            }
            System.out.println("Cliect[port:" + client.getLocalPort() + "] 消息收到了，内容:" + sb.toString());
//            reader.close();
            // 关闭连接
//            writer.close();
//            client.close();
        }

        public void close(){
           try{ client.close();}catch (Throwable t){t.printStackTrace();}
        }

    }


}
