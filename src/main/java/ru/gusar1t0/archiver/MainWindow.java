package ru.gusar1t0.archiver;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import ru.gusar1t0.archiver.models.Data;
import ru.gusar1t0.archiver.models.CodeTable;
import ru.gusar1t0.archiver.models.InfoTable;

import java.util.ArrayList;

import static ru.gusar1t0.archiver.utilities.Utils.WIDTH;
import static ru.gusar1t0.archiver.utilities.Utils.prepareDataForTable;

/**
 * @author Roman Mashenkin
 * @since 17.03.2017
 */
final class MainWindow {
    private String text = "";

    StackPane createScene() {
        TextArea area = new TextArea();
        Button start = new Button("Start");
        VBox view1 = getStartView(area, start);

        InfoTable infoTable = new InfoTable();
        CodeTable codeTable = new CodeTable();
        Button back = new Button("Back");
        Button another = new Button("Another table");
        VBox view2 = getFinalView(infoTable, back, another);

        StackPane root = new StackPane(view1);

        start.setOnAction(event -> {
            if (!text.equals(area.getText())) {
                infoTable.getList().clear();

                ArrayList<Data> data = prepareDataForTable(area.getText());
                infoTable.getList().addAll(data);
                if (view2.getChildren().contains(codeTable.getTable())) {
                    view2.getChildren().remove(codeTable.getTable());
                    codeTable.create(data);
                    view2.getChildren().add(0, codeTable.getTable());
                } else {
                    codeTable.create(data);
                }

                infoTable.refresh();
                text = area.getText();
            }

            root.getChildren().add(view2);
            double height = root.getHeight();
            KeyFrame startKeyFrame = new KeyFrame(Duration.ZERO,
                    new KeyValue(view2.translateYProperty(), height),
                    new KeyValue(view1.translateYProperty(), 0));
            KeyFrame endKeyFrame = new KeyFrame(Duration.seconds(0.75d),
                    new KeyValue(view2.translateYProperty(), 0),
                    new KeyValue(view1.translateYProperty(), -height));
            Timeline slide = new Timeline(startKeyFrame, endKeyFrame);
            slide.setOnFinished(e -> root.getChildren().remove(view1));
            slide.play();
        });

        back.setOnAction(event -> {
            root.getChildren().add(view1);
            double height = root.getHeight();
            KeyFrame startKeyFrame = new KeyFrame(Duration.ZERO,
                    new KeyValue(view1.translateYProperty(), -height),
                    new KeyValue(view2.translateYProperty(), 0));
            KeyFrame endKeyFrame = new KeyFrame(Duration.seconds(0.75d),
                    new KeyValue(view1.translateYProperty(), 0),
                    new KeyValue(view2.translateYProperty(), height));
            Timeline slide = new Timeline(startKeyFrame, endKeyFrame);
            slide.setOnFinished(e -> root.getChildren().remove(view2));
            slide.play();
        });

        another.setOnAction(event -> {
            if (view2.getChildren().contains(infoTable)) {
                view2.getChildren().remove(infoTable);
                view2.getChildren().add(0, codeTable.getTable());
            } else {
                view2.getChildren().remove(codeTable.getTable());
                view2.getChildren().add(0, infoTable);
            }
        });

        return root;
    }

    private VBox getStartView(TextArea area, Button button) {
        area.setWrapText(true);
        area.setFont(Font.font(20));
        area.setMinHeight(400);

        button.setMinWidth(WIDTH - 30);
        button.setMinHeight(50);
        button.setFont(Font.font(20));

        VBox view = new VBox();
        view.getChildren().add(area);
        view.getChildren().add(button);
        view.setPadding(new Insets(20));
        view.setAlignment(Pos.CENTER);
        view.setSpacing(20);

        return view;
    }

    private VBox getFinalView(InfoTable infoTable, Button... buttons) {
        infoTable.setMinHeight(400);

        for (Button button : buttons) {
            button.setMinWidth(WIDTH / 2f - 20);
            button.setMinHeight(50);
            button.setFont(Font.font(20));
        }

        HBox box = new HBox(buttons);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);

        VBox view = new VBox();
        view.getChildren().add(infoTable);
        view.getChildren().addAll(box);
        view.setPadding(new Insets(20));
        view.setAlignment(Pos.CENTER);
        view.setSpacing(20);

        return view;
    }
}
