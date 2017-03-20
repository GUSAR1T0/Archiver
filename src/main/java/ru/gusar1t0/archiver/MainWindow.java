package ru.gusar1t0.archiver;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import ru.gusar1t0.archiver.models.Data;
import ru.gusar1t0.archiver.models.Node;
import ru.gusar1t0.archiver.views.CodeTree;
import ru.gusar1t0.archiver.views.DataTable;

import java.util.ArrayList;

import static ru.gusar1t0.archiver.utilities.Utils.WIDTH;
import static ru.gusar1t0.archiver.utilities.Utils.decode;
import static ru.gusar1t0.archiver.utilities.Utils.encode;

/**
 * @author Roman Mashenkin
 * @since 17.03.2017
 */
public final class MainWindow {
    private String text = "";
    private static ArrayList<Data> data;
    private static ArrayList<Node> tree;

    StackPane createScene() {
        TextArea area = new TextArea();
        RadioButton encode = new RadioButton("Encode");
        RadioButton decode = new RadioButton("Decode");
        Button start = new Button("Start");
        HBox hBox = new HBox();
        VBox view1 = getStartView(area, hBox, encode, decode, start);

        DataTable dataTable = new DataTable();
        CodeTree codeTree = new CodeTree();
        TextField field = new TextField();
        Button back = new Button("Back");
        Button another = new Button("Another table");
        VBox view2 = getFinalView(dataTable, field, back, another);

        StackPane root = new StackPane(view1);

        decode.setOnAction(event -> addInfoTableOnView(view2, hBox, area, dataTable));
        encode.setOnAction(event -> removeInfoTableOnView(hBox, area, dataTable));

        start.setOnAction(event -> {
            try {
                if (encode.isSelected()) {
                    dataTable.getList().clear();

                    field.setText(encode(area.getText()));

                    dataTable.getList().addAll(data);
                    if (view2.getChildren().contains(codeTree.getTreeView())) {
                        view2.getChildren().remove(codeTree.getTreeView());
                        codeTree.create();
                        codeTree.getTreeView().setMinHeight(350);
                        view2.getChildren().add(0, codeTree.getTreeView());
                    } else {
                        if (!view2.getChildren().contains(dataTable)) {
                            view2.getChildren().add(0, dataTable);
                        }
                        codeTree.create();
                    }

                    dataTable.refresh();
                    text = area.getText();
                    another.setDisable(false);
                }
                if (decode.isSelected()) {
                    removeInfoTableOnView(hBox, area, dataTable);
                    if (!view2.getChildren().contains(codeTree.getTreeView())) {
                        if (!view2.getChildren().contains(dataTable)) {
                            view2.getChildren().add(0, dataTable);
                        }
                    }
                    field.setText(decode(area.getText()));
                }
            } catch (Exception e) {
                field.setText(e.toString());
                if (view2.getChildren().contains(dataTable)) {
                    view2.getChildren().remove(dataTable);
                }
                if (view2.getChildren().contains(codeTree.getTreeView())) {
                    view2.getChildren().remove(codeTree.getTreeView());
                }
                another.setDisable(true);
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
            if ("".equals(text)) {
                decode.setDisable(true);
            } else {
                decode.setDisable(false);
            }

            if (decode.isSelected()) {
                addInfoTableOnView(view2, hBox, area, dataTable);
            }

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
            if (view2.getChildren().contains(dataTable)) {
                view2.getChildren().remove(dataTable);
                view2.getChildren().add(0, codeTree.getTreeView());
                codeTree.getTreeView().setMinHeight(350);
            } else {
                view2.getChildren().remove(codeTree.getTreeView());
                view2.getChildren().add(0, dataTable);
            }
        });

        return root;
    }

    private VBox getStartView(TextArea area, HBox hBox, RadioButton encode, RadioButton decode, Button button) {
        area.setWrapText(true);
        area.setFont(Font.font(20));
        area.setMinHeight(350);

        hBox.getChildren().add(area);
        hBox.setAlignment(Pos.CENTER);

        ToggleGroup group = new ToggleGroup();
        encode.setToggleGroup(group);
        encode.setSelected(true);
        encode.setFont(Font.font(20));
        decode.setToggleGroup(group);
        decode.setDisable(true);
        decode.setFont(Font.font(20));

        HBox box = new HBox(encode, decode);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);

        button.setMinWidth(WIDTH - 30);
        button.setMinHeight(50);
        button.setFont(Font.font(20));

        VBox view = new VBox();
        view.getChildren().add(hBox);
        view.getChildren().add(box);
        view.getChildren().add(button);
        view.setPadding(new Insets(20));
        view.setAlignment(Pos.CENTER);
        view.setSpacing(20);

        return view;
    }

    private VBox getFinalView(DataTable dataTable, TextField field, Button... buttons) {
        dataTable.setMinHeight(350);

        field.setFont(Font.font(20));
        field.setEditable(false);

        for (Button button : buttons) {
            button.setMinWidth(WIDTH / 2d - 25);
            button.setMinHeight(50);
            button.setFont(Font.font(20));
        }

        HBox box = new HBox(buttons);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);

        VBox view = new VBox();
        view.getChildren().add(dataTable);
        view.getChildren().add(field);
        view.getChildren().add(box);
        view.setPadding(new Insets(20));
        view.setAlignment(Pos.CENTER);
        view.setSpacing(20);

        return view;
    }

    private void addInfoTableOnView(VBox view, HBox hBox, TextArea area, DataTable dataTable) {
        if (view.getChildren().contains(dataTable)) {
            view.getChildren().remove(dataTable);
        }
        hBox.getChildren().add(1, dataTable);
        hBox.setSpacing(20);
        area.setMinWidth(WIDTH / 2d - 25);
        dataTable.setMinWidth(WIDTH / 2d - 25);
    }

    private void removeInfoTableOnView(HBox hBox, TextArea area, DataTable dataTable) {
        if (hBox.getChildren().contains(dataTable)) {
            hBox.getChildren().remove(dataTable);
            hBox.setSpacing(0);
            area.setMinWidth(0);
            dataTable.setMinWidth(0);
        }
    }

    public static ArrayList<Data> getData() {
        return data;
    }

    public static void setData(ArrayList<Data> data) {
        MainWindow.data = data;
    }

    public static ArrayList<Node> getTree() {
        return tree;
    }

    public static void setTree(ArrayList<Node> tree) {
        MainWindow.tree = tree;
    }
}
