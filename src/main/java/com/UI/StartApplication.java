package com.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("controller/MainController.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("反序列化生成工具");
        stage.setScene(scene);
        stage.show();
    }
}
