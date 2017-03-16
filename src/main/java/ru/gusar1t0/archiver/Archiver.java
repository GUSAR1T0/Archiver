package ru.gusar1t0.archiver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static ru.gusar1t0.archiver.utilities.Utils.*;

/**
 * @author Roman Mashenkin
 * @since 15.03.2017
 */
public final class Archiver extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new MainWindow().createScene(), WIDTH, HEIGHT);
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
