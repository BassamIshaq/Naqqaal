package Naqqaal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 *
 * @author Lenovo 520
 */
public final class ProjectsTreeView extends AnchorPane {

    private TreeView<String> treeView;

    public ProjectsTreeView() {

        String[] split = AllStaticMethods.readTextFile("text files\\recentProjects.txt").split("\n");
        File[] files = new File[split.length];
        for (int i = 0; i < files.length; i++) {
            files[i] = new File(split[i]);
        }
        TreeItem parentTree = new TreeItem("root");
        parentTree.setExpanded(true);
        treeView = new TreeView<>(parentTree);

        treeView.setShowRoot(false);
        createTree(files, parentTree);
        AnchorPane.setBottomAnchor(treeView, 0.0);
        AnchorPane.setTopAnchor(treeView, 0.0);
        AnchorPane.setLeftAnchor(treeView, 0.0);
        AnchorPane.setRightAnchor(treeView, 0.0);
        getChildren().add(treeView);

    }

    private void createTree(File[] projectDirectories, TreeItem parent) {
        for (int i = 0; i < projectDirectories.length; i++) {
            if (projectDirectories[i].exists()) {

                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("images/folder.png")));
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                CustomTreeItem treeItem = new CustomTreeItem(projectDirectories[i], projectDirectories[i].getName());
                treeItem.setGraphic(imageView);
////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////
                parent.getChildren().add(treeItem);
                File[] listFiles1 = projectDirectories[i].listFiles();
                for (int j = 0; j < listFiles1.length; j++) {
                    if (!listFiles1[j].isDirectory()) {
                        if ((getExtension(listFiles1[j].getName()).equalsIgnoreCase("txt")) || (getExtension(listFiles1[j].getName()).equalsIgnoreCase("srt"))) {
                            ImageView imageView1 = new ImageView(new Image(getClass().getResourceAsStream("images/textFile_icon.png")));
                            imageView1.setFitWidth(25);
                            imageView1.setFitHeight(25);
                            CustomTreeItem treeItem1 = new CustomTreeItem(listFiles1[j], listFiles1[j].getName());
                            treeItem1.setGraphic(imageView1);
                            treeItem.getChildren().add(treeItem1);
                        } else if ((getExtension(listFiles1[j].getName()).equalsIgnoreCase("mp3")) || (getExtension(listFiles1[j].getName()).equalsIgnoreCase("wav"))) {
                            ImageView imageView1 = new ImageView(new Image(getClass().getResourceAsStream("images/audio_icon.png")));
                            imageView1.setFitWidth(25);
                            imageView1.setFitHeight(25);
                            CustomTreeItem treeItem1 = new CustomTreeItem(listFiles1[j], listFiles1[j].getName());
                            treeItem1.setGraphic(imageView1);
                            treeItem.getChildren().add(treeItem1);
                        } else if ((getExtension(listFiles1[j].getName()).equalsIgnoreCase("mp4")) || (getExtension(listFiles1[j].getName()).equalsIgnoreCase("mkv"))) {
                            ImageView imageView1 = new ImageView(new Image(getClass().getResourceAsStream("images/video_icon.png")));
                            imageView1.setFitWidth(25);
                            imageView1.setFitHeight(25);
                            CustomTreeItem treeItem1 = new CustomTreeItem(listFiles1[j], listFiles1[j].getName());
                            treeItem1.setGraphic(imageView1);
                            treeItem.getChildren().add(treeItem1);
                        } else if ((getExtension(listFiles1[j].getName()).equalsIgnoreCase("ser"))) {
                            ImageView imageView1 = new ImageView(new Image(getClass().getResourceAsStream("images/pen.png")));
                            imageView1.setFitWidth(25);
                            imageView1.setFitHeight(25);
                            CustomTreeItem treeItem1 = new CustomTreeItem(listFiles1[j], listFiles1[j].getName());
                            treeItem1.setGraphic(imageView1);
                            treeItem.getChildren().add(treeItem1);
                        }
                    }
                }
            }

        }
    }

    public TreeView<String> getTreeView() {
        return treeView;
    }

    public static String getExtension(String fileName) {
//        fileName = fileName.replace(".", "#");
//        return (fileName.split("#")[1]);
        int lastIndexOf = fileName.lastIndexOf(".");
        return (fileName.substring(lastIndexOf + 1));
    }

    public class CustomTreeItem extends TreeItem<String> {

        private File file;

        public CustomTreeItem(File file, String value) {
            super(value);
            this.file = file;
        }

        public File getFile() {
            return file;
        }
    }

}
