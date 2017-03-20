package ru.gusar1t0.archiver.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.gusar1t0.archiver.models.Data;
import ru.gusar1t0.archiver.utilities.Utils;

/**
 * @author Roman Mashenkin
 * @since 16.03.2017
 */
public final class DataTable extends TableView<Data> {
    private ObservableList<Data> list = FXCollections.observableArrayList();

    @SuppressWarnings("unchecked")
    public DataTable() {
        TableColumn<Data, String> values = new TableColumn<>("Xᵢ");
        values.setCellValueFactory(new PropertyValueFactory<>("symbol"));

        TableColumn<Data, Integer> counts = new TableColumn<>("Nᵢ");
        counts.setCellValueFactory(new PropertyValueFactory<>("count"));

        TableColumn<Data, Float> probabilities = new TableColumn<>("Pᵢ");
        probabilities.setCellValueFactory(new PropertyValueFactory<>("probability"));

        TableColumn<Data, String> codes = new TableColumn<>("Codes");
        codes.setCellValueFactory(new PropertyValueFactory<>("code"));

        this.setItems(list);
        this.getColumns().setAll(values, counts, probabilities, codes);
        this.styleProperty().bind(Utils.getStyle());
    }

    public ObservableList<Data> getList() {
        return list;
    }
}
