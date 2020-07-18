/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Naqqaal;

import java.io.File;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 *
 * @author Lenovo 520
 */
public class OpenProjectEventHandler implements EventHandler {

    @Override
    public void handle(Event event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(".ser", "*.ser");
        String text = AllStaticMethods.readTextFile("text files/ProjectsDefaultLocation.txt");
        if (text != null) {
            fileChooser.setInitialDirectory(new File(text));
        }
//        open project krte waqt recentproject.txt ko update krne ka code likhna ha;

        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(Runner.primaryStage);

        if (file != null) {
            resetRecentProjects(file.getAbsoluteFile().getParent());

            String filePath = file.toPath().toString();
            System.out.println("filePath: " + filePath);
            Project project = Project.readProjectFile(filePath);
            if (new File(project.getMediaPath()).exists()) {
                MainScreenBorderPane mainScreenBorderPane = new MainScreenBorderPane(project);
                Scene scene = new Scene(mainScreenBorderPane, 1920, 990);
                mainScreenBorderPane.getMenuBar().getMenus().get(2).getItems().add(Runner.darkThemeMenuItem(scene));
                Runner.primaryStage.setResizable(true);
                Runner.primaryStage.setTitle("Naqqaal 1.0 - " + file.getAbsoluteFile().getParent());
                Runner.primaryStage.setScene(scene);
                Runner.primaryStage.setMaximized(true);
            } else {
                new Alert(Alert.AlertType.ERROR, "Media File not found !\nIt is either renamed, deleted or moved to another location !", ButtonType.OK).show();
            }
        }

    }

    public static void resetRecentProjects(String projectPath) {
        String recent = AllStaticMethods.readTextFile("text files/recentProjects.txt");
        String newRecent;
        if (recent != null) {
            int indexOf = recent.indexOf(projectPath);//file.getAbsoluteFile().getParent() returns parent directory of file
            if (indexOf >= 0) {
                String newRecent1 = projectPath;
                String newRecent2 = recent.substring(0, indexOf);//.replace("\n", "")
                newRecent2 = newRecent2.subSequence(0, newRecent2.length() - 1).toString();
                String newRecent3 = recent.substring((indexOf + projectPath.length()));
                newRecent = newRecent1 + "\n" + newRecent2 + newRecent3;
            } else { // agar path recentProjects.txt mein nai ha
                newRecent = projectPath + "\n" + recent;
            }
            AllStaticMethods.createFile("text files/recentProjects.txt", newRecent);

        }

    }

}
