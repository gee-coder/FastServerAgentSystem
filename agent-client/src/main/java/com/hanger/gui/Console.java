package com.hanger.gui;

import com.sun.imageio.plugins.common.ReaderUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * @author hanger
 * 2020-01-15 16:15
 */
public class Console extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //设置程序名称
        primaryStage.setTitle("代理者_v0.0.1");
        //加载图标资源
        Image image = new Image("static/agent-32.png");
        //设置程序图标
        primaryStage.getIcons().add(image);
        //设置窗口大小
        primaryStage.setWidth(350);
        primaryStage.setHeight(600);
        //固定窗口大小
        primaryStage.setResizable(false);
        //设置窗口初始位置距离左上角的距离
//        primaryStage.setX(1500);
//        primaryStage.setY(200);

        //标签
        Label label = new Label("IP：");
        //输入框
        TextField text = new TextField();
//        text.setText("daed问大三");
        //设置提示
//        text.setTooltip(new Tooltip(""));
        text.setPromptText("目标局域网IPV4地址");
        text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //限制只能输入15个字符
                if (newValue.length() > 15) {
                    text.setText(oldValue);
                }
//                System.out.println(newValue);
            }
        });


        Button button = new Button("连接dsdsadas");
        button.setPrefWidth(160);
        button.setPrefHeight(20);
        button.setCursor(Cursor.HAND);
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("点击");
            }
        });


        //关闭窗口不退出，在后台运行
//        Platform.setImplicitExit(false);
        //完全退出
//        Platform.exit();
        Tray instance = Tray.getInstance();
        instance.listen(primaryStage);

        AnchorPane root = new AnchorPane();
        root.setStyle(
                "-fx-background-color: #FFFAE8;"+ //背景颜色
                "-fx-text-fill: #50ff23;"+ //文本颜色
                "-fx-font-size: 15;"+ //字体大小
                "-fx-font-family: 'Segoe Print';" //字体
        );


        //创建节点组
//        Group root = new Group();
        //挂载节点

        root.getChildren().addAll(button, label, text);
        //创建并场景挂载节点组
        Scene scene = new Scene(root);
        //挂载场景
        primaryStage.setScene(scene);
        //显示窗口
        primaryStage.show();
    }

//    @Override
//    public void run() {
//        while (true) {
//            System.out.print("请输入命令 : ");
//            String cmd = reader.
//            handler(cmd);
//        }
//    }

//    private void handler(String cmd) {
//        switch (cmd) {
//            case "status":
//                System.out.println("当前时间 : " + dateFormat.format(new Date()));
//                System.out.println("总连接数 : " + clientList.size());
//                for (Socket socket : clientList.keySet()) {
//                    long time = new Date().getTime() - clientList.get(socket).getTime();
//                    System.out.println("<" + socket.getRemoteSocketAddress().toString() + "> " + time / 1000);
//                }
//                break;
//        }
//    }

}
