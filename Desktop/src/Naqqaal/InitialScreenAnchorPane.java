package Naqqaal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class InitialScreenAnchorPane extends AnchorPane {

    private final Button newProjectButton, openProjectButton, recentProjectButton;
    private final ImageView imageView;
    private final Label versionLabel;

    public InitialScreenAnchorPane() {
        setStyle("-fx-background-color:white;");

        imageView = new ImageView(new Image(getClass().getResourceAsStream("images/logo.png")));
        versionLabel = new Label("Version 1.0");
        newProjectButton = new Button("Start a new Project !");
        openProjectButton = new Button("Open an existing Project !");
        recentProjectButton = new Button("Continue Working on Project: 'E:\\ASUG test files\\pakistan\\Project.ser'");

        imageView.setFitWidth(200);
        imageView.setFitHeight(100);
        AnchorPane.setTopAnchor(imageView, 10.0);
        AnchorPane.setLeftAnchor(imageView, 220.0);
        AnchorPane.setRightAnchor(imageView, 170.0);
//        AnchorPane.setTopAnchor(imageView, 10.0);

        versionLabel.setStyle("-fx-text-fill: #7d7d7d");
        AnchorPane.setTopAnchor(versionLabel, 110.0);
        AnchorPane.setLeftAnchor(versionLabel, 270.0);
//        AnchorPane.setRightAnchor(versionLabel, 180.0);

        newProjectButton.setPrefSize(100, 40);
        newProjectButton.setStyle("-fx-background-color: transparent;");
        AnchorPane.setLeftAnchor(newProjectButton, 20.0);
        AnchorPane.setRightAnchor(newProjectButton, 20.0);
        AnchorPane.setTopAnchor(newProjectButton, 140.0);
        newProjectButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newProjectButton.setStyle("-fx-background-color:#f2f2f2;-fx-border-color: #f2f2f2;");
            }
        });
        newProjectButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newProjectButton.setStyle("-fx-background-color: transparent;-fx-border-color: transparent;");
            }
        });

        openProjectButton.setPrefSize(100, 40);
        openProjectButton.setStyle("-fx-background-color: transparent;");
        AnchorPane.setLeftAnchor(openProjectButton, 20.0);
        AnchorPane.setRightAnchor(openProjectButton, 20.0);
        AnchorPane.setTopAnchor(openProjectButton, 200.0);
        openProjectButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openProjectButton.setStyle("-fx-background-color:#f2f2f2;-fx-border-color: #f2f2f2;");
            }
        });
        openProjectButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openProjectButton.setStyle("-fx-background-color: transparent;-fx-border-color: transparent;");
            }
        });

        recentProjectButton.setPrefSize(100, 40);
        recentProjectButton.setStyle("-fx-background-color: transparent;");
        AnchorPane.setLeftAnchor(recentProjectButton, 20.0);
        AnchorPane.setRightAnchor(recentProjectButton, 20.0);
        AnchorPane.setTopAnchor(recentProjectButton, 260.0);
        recentProjectButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                recentProjectButton.setStyle("-fx-background-color:#f2f2f2;-fx-border-color: #f2f2f2;");
            }
        });
        recentProjectButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                recentProjectButton.setStyle("-fx-background-color: transparent;-fx-border-color: transparent;");
            }
        });

        getChildren().addAll(imageView, versionLabel, newProjectButton, openProjectButton, recentProjectButton);
    }

    public Button getNewProjectButton() {
        return newProjectButton;
    }

    public Button getOpenProjectButton() {
        return openProjectButton;
    }

    public Button getRecentProjectButton() {
        return recentProjectButton;
    }

}
