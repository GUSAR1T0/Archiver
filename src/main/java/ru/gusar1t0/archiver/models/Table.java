package ru.gusar1t0.archiver.models;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author Roman Mashenkin
 * @since 16.03.2017
 */
public final class Table extends TableView<Data> {
    private ObservableList<Data> list = FXCollections.observableArrayList();

    @SuppressWarnings("unchecked")
    public Table() {
        double d = 4;   //Number of dividing of columns

        TableColumn<Data, String> values = new TableColumn<>("Xᵢ");
        values.prefWidthProperty().bind(this.widthProperty().divide(d));
        values.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Data, Integer> counts = new TableColumn<>("Nᵢ");
        counts.prefWidthProperty().bind(this.widthProperty().divide(d));
        counts.setCellValueFactory(new PropertyValueFactory<>("count"));

        TableColumn<Data, Float> probabilities = new TableColumn<>("Pᵢ");
        probabilities.prefWidthProperty().bind(this.widthProperty().divide(d));
        probabilities.setCellValueFactory(new PropertyValueFactory<>("probability"));

        TableColumn<Data, String> codes = new TableColumn<>("Codes");
        codes.prefWidthProperty().bind(this.widthProperty().divide(d));
        codes.setCellValueFactory(new PropertyValueFactory<>("code"));

        this.setItems(list);
        this.getColumns().setAll(values, counts, probabilities, codes);

        StringProperty style = new SimpleStringProperty();
        style.bind(Bindings.createStringBinding(() -> "-fx-font-size:16px;\n"));
        this.styleProperty().bind(style);
    }

    public ObservableList<Data> getList() {
        return list;
    }
}
