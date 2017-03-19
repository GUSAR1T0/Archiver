package ru.gusar1t0.archiver.models;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.gusar1t0.archiver.utilities.Utils;

import java.util.ArrayList;

/**
 * @author Roman Mashenkin
 * @since 17.03.2017
 */
public class CodeTable {
    private TableView<ObservableList<Character>> table;

    public void create(ArrayList<Data> data) {
        table = new TableView<>();

        for (int i = 0; i < data.size() - 1; i++) {
            final int finalI = i;
            TableColumn<ObservableList<Character>, Character> column = new TableColumn<>(i + "");
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalI)));
            table.getColumns().add(column);
        }
        table.styleProperty().bind(Utils.getStyle());

        for (int i = 0; i < data.size(); i++) {
            ArrayList<Character> characters = new ArrayList<>();
            for (int j = 0; j < data.size() - 1; j++) {
                characters.add(j, '-');
            }
            for (int j = 0; j < data.size() - 1; j++) {
                if (j < data.get(i).getCode().length()) {
                    characters.set(data.size() - 2 - j, data.get(i).getCode().charAt(j));
                }
            }
            table.getItems().add(FXCollections.observableArrayList(characters));
        }

        table.refresh();
    }

    public TableView<ObservableList<Character>> getTable() {
        return table;
    }
}
