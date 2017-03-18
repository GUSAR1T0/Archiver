package ru.gusar1t0.archiver.utilities;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.gusar1t0.archiver.models.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author Roman Mashenkin
 * @since 15.03.2017
 */
public final class Utils {
    public static final String TITLE = "Archiver";
    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;

    public static ArrayList<Data> prepareDataForTable(String text) {
        TreeSet<Character> values = new TreeSet<>();
        ArrayList<Integer> counts = new ArrayList<>();
        ArrayList<String> codes = new ArrayList<>();

        for (char c : text.toCharArray()) {
            if (values.add(c)) {
                for (int i = 0; i < values.size(); i++) {
                    if ((char) (values.toArray()[i]) == c) {
                        counts.add(i, 1);
                        codes.add("");
                        break;
                    }
                }
            } else {
                for (int i = 0; i < values.size(); i++) {
                    if ((char) (values.toArray()[i]) == c) {
                        counts.set(i, counts.get(i) + 1);
                        break;
                    }
                }
            }
        }

        ArrayList<Data> data = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            data.add(new Data("\'" + values.toArray()[i] + "\'",
                    counts.get(i),
                    (float) counts.get(i) / text.length(),
                    codes.get(i)));
        }
        data.sort(Comparator.comparingDouble(Data::getProbability).reversed());

        double n = data.get(data.size() - 1).getProbability();
        for (int i = data.size() - 1; i > 0; i--) {
            if (data.get(i - 1).getProbability() >= n) {
                for (int j = data.size() - 1; j >= i; j--) {
                    codes.set(j, "0" + codes.get(j));
                }
                codes.set(i - 1, "1" + codes.get(i - 1));
            } else {
                for (int j = data.size() - 1; j >= i; j--) {
                    codes.set(j, "1" + codes.get(j));
                }
                codes.set(i - 1, "0" + codes.get(i - 1));
            }
            n += data.get(i - 1).getProbability();
        }

        for (int i = 0; i < values.size(); i++) {
            data.get(i).setCode(codes.get(i));
        }

        return data;
    }

    public static StringProperty getStyle() {
        StringProperty style = new SimpleStringProperty();
        style.bind(Bindings.createStringBinding(() -> "-fx-font-size:16px;\n"));
        return style;
    }
}
