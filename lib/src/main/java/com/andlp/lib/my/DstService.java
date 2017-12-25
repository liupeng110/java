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
import java.io.PrintWriter;
import java.net.*;


public class DstService extends Application {
    DropShadow shadow = new DropShadow();

    Socket socket=null;
    @Override
    public void start(Stage primaryStage) {

        final TextField notification = new TextField ();
        notification.setText("����");
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
//                new Thread(new Runnable() { @Override public void run() { socket(); } }).start();//��ֹui����
                test();
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
                try {
                    OutputStream os = socket.getOutputStream();
                    os.write(notification.getText().getBytes("GBK"));
                }catch (Exception e){e.printStackTrace();}
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
            @Override
            public void handle(ActionEvent event) {
                   System.out.print("��� �Ͽ�����");
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




    public static void main(String[] args) {
        launch(args);
    }

private void send(String str){
    OutputStream os = null;
    InputStream is = null;
    try {
        os = socket.getOutputStream();//
        System.out.println("���ڷ�����Ϣ...");
        os.write(str.getBytes());//������Ϣ
        os.flush();//ע��:�����ʹ��flush����,����˽��յ��ͻ��˷��͵���Ϣ
        socket.shutdownOutput();
        is = socket.getInputStream();//
        byte[] buf = new byte[1024*8];//������
        String msg="";
        for(int len=is.read(buf);len>0;len=is.read(buf)){
            msg+=new String(buf, 0, len);
        }
        socket.shutdownInput();
        System.out.println("���ڽ��ջظ���Ϣ...");
        System.out.println("���������ص���Ϣ: "+msg);
        System.out.println("���ջظ���Ϣ���");
    } catch (Exception e) {
        e.printStackTrace();
    }finally{
        //�ر����������
        try {os.close();} catch (Exception e) {e.printStackTrace(); }
        try {is.close();} catch (Exception e) {e.printStackTrace(); }
    }
}

    private void socket(){
        System.out.println("�����¼�--����socket" );
        try {
              socket = new Socket("127.0.0.1", 30000);
            socket.setKeepAlive(true);
            socket.setSoTimeout(10);
            while (true) {
                socket.sendUrgentData(0xFF); // ����������
                System.out.println(socket.isBound());
                System.out.println(socket.isClosed());
                System.out.println(socket.isConnected());
                System.out.println(socket.isInputShutdown());
                System.out.println(socket.isOutputShutdown());
                System.out.println("Ŀǰ��������״̬��");
                System.out.println(socket.isOutputShutdown());
                System.out.println("������:"+socket.getInetAddress()+":"+socket.getPort());
                System.out.println("------------���Ƿָ���------------");

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


            Thread.sleep(3 * 1000);//�߳�˯��3��

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("�����¼�--�����쳣"+e.getLocalizedMessage() );
        }
    }

private void test(){
//    Socket socket=null;
    try {
        //�Է���˷�����������
        socket=new Socket("localhost", 8080);

        //���ܷ������Ϣ����ӡ
        InputStream is=socket.getInputStream();
        byte b[]=new byte[1024];
        is.read(b);
        String bb= new String(b);
        System.out.println(bb.substring(0,bb.lastIndexOf("/n")));

        //������˷�����Ӧ��Ϣ
        OutputStream os=socket.getOutputStream();
        os.write("yes,I have received you message!".getBytes());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
