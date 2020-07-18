package Naqqaal;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Lenovo 520
 */
public class Runner extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private InitialScreenAnchorPane initialScreenAnchorPane;
    private NewProjectStage newProjectScreen;
    static Stage primaryStage;
    private String recentProjectPath = null;

    @Override
    public void start(Stage primaryStage) {

        setUserAgentStylesheet(STYLESHEET_MODENA);
        Runner.primaryStage = primaryStage;

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    System.exit(0);
                } else if (alert.getResult() == ButtonType.CANCEL) {
                    event.consume();
                }

            }
        });
        initialScreenAnchorPane = new InitialScreenAnchorPane();

        initialScreenAnchorPane.getNewProjectButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newProjectScreen = new NewProjectStage();
                newProjectScreen.showAndWait();
//                //                           CODE TO REFRESH TREE VIEW
//                            mainSplitPane.getItems().remove(0);
//                            mainSplitPane.getItems().add(0, new ProjectsTreeView());
//                            mainSplitPane.setDividerPosition(0, 0.15);

            }
        });
        initialScreenAnchorPane.getOpenProjectButton().setOnMousePressed(new OpenProjectEventHandler());

        String text = AllStaticMethods.readTextFile("text files/recentProjects.txt");
        if (text != null) {
            String[] split = text.split("\n");
            recentProjectPath = split[0];
            initialScreenAnchorPane.getRecentProjectButton().setText("Continue working on " + recentProjectPath);
        } else {
            initialScreenAnchorPane.getRecentProjectButton().setDisable(true);
        }
        initialScreenAnchorPane.getRecentProjectButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Project project = Project.readProjectFile(recentProjectPath + "\\Project.ser");
                if (new File(project.getMediaPath()).exists()) {

                    System.out.println("recentProjectPath: " + recentProjectPath);
                    MainScreenBorderPane mainScreenBorderPane = new MainScreenBorderPane(project);
                    Scene scene = new Scene(mainScreenBorderPane, 1920, 990);
                    primaryStage.setResizable(true);
                    primaryStage.setTitle("Naqqaal 1.0 - " + recentProjectPath);
                    primaryStage.setScene(scene);
                    primaryStage.setMaximized(true);

                    mainScreenBorderPane.getMenuBar().getMenus().get(2).getItems().add(darkThemeMenuItem(scene));
                } else {
                    new Alert(Alert.AlertType.ERROR, "Media File not found !\nIt is either renamed, deleted or moved to another location !", ButtonType.OK).show();
                }
            }
        });

        Scene scene = new Scene(initialScreenAnchorPane, 600, 400);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Naqqaal 1.0 - Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static CustomMenuItem darkThemeMenuItem(Scene scene) {
        //                CODE FOR DARK THEME ......
        CheckBox checkBox = new CheckBox("Dark Mode");
        checkBox.setStyle("-fx-text-fill:black;");
        CustomMenuItem customMenuItem_DarkMode = new CustomMenuItem(checkBox);
        customMenuItem_DarkMode.setHideOnClick(false);
        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (checkBox.isSelected()) {
                    scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
                    checkBox.setStyle("-fx-text-fill:white;");
                } else {
                    scene.getStylesheets().remove(getClass().getResource("Style.css").toExternalForm());
                    checkBox.setStyle("-fx-text-fill:black;");
                }
            }
        });
        return customMenuItem_DarkMode;
    }

}
