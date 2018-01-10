package com.andlp.lib.my;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

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

/**
 * 717219917@qq.com      2017/12/25  16:26
 */

public class Test  extends Application {
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
            @Override public void handle(ActionEvent event) {
              new Thread(new Runnable() { @Override public void run() { socket(); } }).start();
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
             sendExit(notification.getText());
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

    private void socket(){
        System.out.println("单机事件--进入socket" );
        try {
            socket = new Socket("192.168.1.131", 5029);//192.168.1.131       www.bkksextoy.com
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









    public static void main(String[] args) {
        launch(args);
    }
}
