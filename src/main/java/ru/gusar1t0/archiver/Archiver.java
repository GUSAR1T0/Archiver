package ru.gusar1t0.archiver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Roman Mashenkin
 * @since 15.03.2017
 */
public class Archiver extends Application {

    private static final String TITLE = "Archiver";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage stage = new Stage();
        VBox root = createStage();
        stage.setTitle(TITLE);
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    private VBox createStage() {
        return new VBox();
    }
}
