package ru.gusar1t0.archiver.models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Roman Mashenkin
 * @since 16.03.2017
 */
public final class Data {
    private SimpleStringProperty symbol;
    private SimpleIntegerProperty count;
    private SimpleFloatProperty probability;
    private SimpleStringProperty code;

    public Data(String symbol, int count, float probability, String code) {
        this.symbol = new SimpleStringProperty(symbol);
        this.count = new SimpleIntegerProperty(count);
        this.probability = new SimpleFloatProperty(probability);
        this.code = new SimpleStringProperty(code);
    }

    public String getSymbol() {
        return symbol.get();
    }

    public SimpleStringProperty symbolProperty() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol.set(symbol);
    }

    public int getCount() {
        return count.get();
    }

    public SimpleIntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public float getProbability() {
        return probability.get();
    }

    public SimpleFloatProperty probabilityProperty() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability.set(probability);
    }

    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }
}
