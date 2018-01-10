package com.andlp.lib.my;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * 717219917@qq.com      2017/12/25  16:26
 */

public class Test  extends Application {
    DropShadow shadow = new DropShadow();

    Socket socket=null;
    @Override
    public void start(Stage primaryStage) {
        try {
            gui3(primaryStage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.print(e.getLocalizedMessage());
        }

    }


    private void gui(Stage primaryStage) {

        // 第一种：获取类加载的根路径   D:\git\daotie\daotie\target\classes
        File f = new File(this.getClass().getResource("/").getPath());
        System.out.println(f);

        // 获取当前类的所在工程路径; 如果不加“/”  获取当前类的加载目录  D:\git\daotie\daotie\target\classes\my
        File f2 = new File(this.getClass().getResource("/a.txt").getPath());
        System.out.println(f2);

        // 第二种：获取项目路径    D:\git\daotie\daotie
        File directory = new File("");// 参数为空
        String courseFile = null;
        try {  courseFile = directory.getCanonicalPath(); } catch (IOException e) { e.printStackTrace();  }
        System.out.println(courseFile);


        // 第三种：  file:/D:/git/daotie/daotie/target/classes/
        URL xmlpath = this.getClass().getClassLoader().getResource("");
        System.out.println(xmlpath);


        // 第四种： D:\git\daotie\daotie
        System.out.println(System.getProperty("user.dir"));
         /*
          * 结果： C:\Documents and Settings\Administrator\workspace\projectName
          * 获取当前工程路径
          */

        // 第五种：  获取所有的类路径 包括jar包的路径
        System.out.println(System.getProperty("java.class.path"));

        //        Image image = new Image("https://www.baidu.com/img/bd_logo1.png");
        Image image = new Image(new File("a.png").toURI().toString());
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        // Display image on screen
        StackPane root = new StackPane();
        root.getChildren().add(imageView);
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Image Read Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void gui2(Stage primaryStage){
        final TextField notification = new TextField ();
        notification.setText("发送");
        notification.setLayoutX(50);
        notification.setLayoutY(50);
        notification.setPadding(new Insets(15));
        notification.setTranslateY(-100);
        notification.clear();

        File f2 = new File(this.getClass().getResource("/a.txt").getPath());
        System.out.println(f2);

        try {
            InputStream is = this.getClass().getResourceAsStream("/a.txt");
            StringBuffer   out   =   new   StringBuffer();
            byte[]   b   =   new   byte[4096];
            for   (int   n;   (n   =   is.read(b))   !=   -1;)   {
                out.append(new   String(b,   0,   n));
            }
            System.out.println(out.toString());
            is.close();
        }catch (Exception e){e.printStackTrace();}

        Button btn = new Button();
        btn.setText("connect");
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


        ImageView img = new ImageView();
        discon.setLayoutX(50);
        discon.setLayoutY(50);
        discon.setTranslateX(50);
        discon.setTranslateY(-40);
        discon.setPadding(new Insets(15));
        img.setImage(new Image(new File("a.png").toURI().toString()));



        StackPane root = new StackPane();
        root.getChildren().add(notification);
        root.getChildren().add(btn);
        root.getChildren().add(send);
//        root.getChildren().add(discon);
        root.getChildren().add(img);



        Scene scene =new Scene(root, 300, 250);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void gui3(Stage primaryStage) throws URISyntaxException {
        // load the image
//        Image image = new Image("https://www.baidu.com/img/bd_logo1.png");
        Image image = new Image(getClass().getResource("/a.png").toURI().toString());

        // simple displays ImageView the image as is
        ImageView iv1 = new ImageView();
        iv1.setImage(image);

        // resizes the image to have width of 100 while preserving the ratio and using
        // higher quality filtering method; this ImageView is also cached to
        // improve performance
        ImageView iv2 = new ImageView();
        iv2.setImage(image);
        iv2.setFitWidth(100);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);

        // defines a viewport into the source image (achieving a "zoom" effect) and
        // displays it rotated
        ImageView iv3 = new ImageView();
        iv3.setImage(image);
        Rectangle2D viewportRect = new Rectangle2D(40, 35, 110, 110);
        iv3.setViewport(viewportRect);
        iv3.setRotate(90);

        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        HBox box = new HBox();
        box.getChildren().add(iv1);
        box.getChildren().add(iv2);
        box.getChildren().add(iv3);
        root.getChildren().add(box);

        primaryStage.setTitle("ImageView");
        primaryStage.setWidth(415);
        primaryStage.setHeight(200);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
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
