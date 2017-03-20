package ru.gusar1t0.archiver.views;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ru.gusar1t0.archiver.models.Node;

import static ru.gusar1t0.archiver.MainWindow.getTree;
import static ru.gusar1t0.archiver.utilities.Utils.getStyle;

/**
 * @author Roman Mashenkin
 * @since 17.03.2017
 */
public final class CodeTree {
    private TreeView<String> tree;

    public void create() {
        TreeItem<String> root = check(getTree().get(0), "");
        root.setValue("Root Node");
        root.setExpanded(true);
        tree = new TreeView<>(root);
        tree.styleProperty().bind(getStyle());
    }

    private TreeItem<String> check(Node node, String s) {
        if (node.getData() == null) {
            TreeItem<String> item = new TreeItem<>(s);
            item.setExpanded(true);
            item.getChildren().add(check(node.getLeft(), s + '0'));
            item.getChildren().add(check(node.getRight(), s + '1'));
            return item;
        } else {
            TreeItem<String> item = new TreeItem<>(node.getData().getSymbol() + " (" + node.getData().getCode() + ")");
            item.setExpanded(true);
            return item;
        }
    }

    public TreeView<String> getTreeView() {
        return tree;
    }
}
