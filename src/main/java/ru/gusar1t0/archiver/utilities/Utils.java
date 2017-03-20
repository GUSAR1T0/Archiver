package ru.gusar1t0.archiver.utilities;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.gusar1t0.archiver.models.Data;
import ru.gusar1t0.archiver.models.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static ru.gusar1t0.archiver.MainWindow.*;

/**
 * @author Roman Mashenkin
 * @since 15.03.2017
 */
public final class Utils {
    public static final String TITLE = "Archiver";
    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;

    private Utils() {
    }

    public static String encode(String text) throws Exception {
        if (text.length() <= 1) {
            throw new Exception("Count of symbols should be more than 1");
        }

        TreeSet<Data> data = new TreeSet<>(Comparator.comparing(Data::getSymbol));

        for (int i = 0; i < text.length(); i++) {
            if (!data.add(new Data("'" + text.charAt(i) + "'", 1, 0, ""))) {
                for (int j = 0; j < data.size(); j++) {
                    if (text.charAt(i) == ((Data) data.toArray()[j]).getSymbol().charAt(1)) {
                        ((Data) data.toArray()[j]).setCount(((Data) data.toArray()[j]).getCount() + 1);
                        break;
                    }
                }
            }
        }

        for (Data d : data) {
            d.setProbability((float) d.getCount() / text.length());
        }

        setData(new ArrayList<>());
        getData().addAll(data);
        getData().sort(Comparator.comparing(Data::getCount).reversed());

        setTree(data.stream().map(Node::new).collect(Collectors.toCollection(ArrayList::new)));
        do {
            getTree().sort(Comparator.comparing(Node::getN).reversed());

            Node node = (getTree().get(getTree().size() - 2).getN() >= getTree().get(getTree().size() - 1).getN()) ?
                    new Node(getTree().get(getTree().size() - 1), getTree().get(getTree().size() - 2)) :
                    new Node(getTree().get(getTree().size() - 2), getTree().get(getTree().size() - 1));

            getTree().remove(getTree().size() - 1);
            getTree().set(getTree().size() - 1, node);
        } while (getTree().size() > 1);

        setCodes(getTree().get(0), "");

        String result = "";

        for (int i = 0; i < text.length(); i++) {
            for (Data d : getData()) {
                if (d.getSymbol().charAt(1) == text.charAt(i)) {
                    result += d.getCode();
                }
            }
        }

        return result;
    }

    public static String decode(String text) throws Exception {
        if (text.length() <= 0) {
            throw new Exception("Count of symbols should be more than 0");
        }

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != '0' && text.charAt(i) != '1') {
                throw new Exception("Text isn't able to encode");
            }
        }

        String result = "";
        Node node = getTree().get(0);

        for (int i = 0; i < text.length(); i++) {
            if (node.getData() == null) {
                node = text.charAt(i) == '0' ? node.getLeft() : node.getRight();
            } else {
                result += node.getData().getSymbol().charAt(1);
                node = getTree().get(0);
                i--;
            }
        }

        if (node.getData() != null) {
            result += node.getData().getSymbol().charAt(1);
        } else {
            throw new Exception("Stream is broken");
        }

        return result;
    }

    private static void setCodes(Node node, String s) {
        if (node.getData() == null) {
            setCodes(node.getLeft(), s + '0');
            setCodes(node.getRight(), s + '1');
        } else {
            getData().stream().filter(data -> data.equals(node.getData())).forEach(data -> data.setCode(s));
        }
    }

    public static StringProperty getStyle() {
        StringProperty style = new SimpleStringProperty();
        style.bind(Bindings.createStringBinding(() -> "-fx-font-size:16px;\n"));
        return style;
    }
}
