package ru.gusar1t0.archiver.utilities;

import ru.gusar1t0.archiver.models.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
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
        Pair<Set<Character>, ArrayList<Integer>> pairs = new Pair<>(new TreeSet<>(), new ArrayList<>());
        ArrayList<String> codes = new ArrayList<>();

        for (char c : text.toCharArray()) {
            if (pairs.getElement().add(c)) {
                for (int i = 0; i < pairs.getElement().size(); i++) {
                    if ((char) (pairs.getElement().toArray()[i]) == c) {
                        pairs.getValue().add(i, 1);
                        codes.add("");
                        break;
                    }
                }
            } else {
                for (int i = 0; i < pairs.getElement().size(); i++) {
                    if ((char) (pairs.getElement().toArray()[i]) == c) {
                        pairs.getValue().set(i, pairs.getValue().get(i) + 1);
                        break;
                    }
                }
            }
        }

        ArrayList<Data> data = new ArrayList<>();
        for (int i = 0; i < pairs.getElement().size(); i++) {
            data.add(new Data("\'" + pairs.getElement().toArray()[i] + "\'",
                    pairs.getValue().get(i),
                    (float) pairs.getValue().get(i) / text.length(),
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

        for (int i = 0; i < pairs.getElement().size(); i++) {
            data.get(i).setCode(codes.get(i));
        }

        return data;
    }
}
