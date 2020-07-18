package Naqqaal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Lenovo 520
 */
public class NewProjectStage extends Stage {

    private final Button browseMediaButton, browseTextFileButton, browseLocationButton, cancelButton, finishButton;
    private final TextField projectNameTextField;
    private final Label titleLabel, projectNameLabel, projectNameErrorLabel, mediaLabel, mediaPathLabel, mediaErrorLabel, textFileLabel,
            textFilePathLabel, projectLocationLabel, projectLocationPathLabel;
    private final CheckBox copyMediaCheckbox, copyTextFileCheckbox;
    private AnchorPane anchorPane;
    private boolean mediaSelected = false;

    public NewProjectStage() {

        initModality(Modality.WINDOW_MODAL);
        initOwner(Runner.primaryStage);
        setTitle("New Project - Naqqaal 1.0");
        setResizable(false);

        anchorPane = new AnchorPane();

//        setStyle("-fx-background-color:white;");
        titleLabel = new Label("Name and Location\t\t\t\t\t\t\t\t\t\t\t\t\t  ");

        projectNameLabel = new Label("Project Name*:");
        projectNameTextField = new TextField();
        projectNameErrorLabel = new Label();

        mediaLabel = new Label("Media File*:");
        mediaPathLabel = new Label("No Media File Selected !");
        browseMediaButton = new Button(" Browse... ");
        mediaErrorLabel = new Label();

        textFileLabel = new Label("Text File:");
        textFilePathLabel = new Label("No .txt or .srt file selected !");
        browseTextFileButton = new Button(" Browse... ");

        projectLocationLabel = new Label("Project Location:");
        projectLocationPathLabel = new Label(getProjectsDefaultLocation());
        browseLocationButton = new Button(" Browse... ");

        copyMediaCheckbox = new CheckBox("Copy Media File to Project Folder");
        copyTextFileCheckbox = new CheckBox("Copy Text File to Project Folder");

        cancelButton = new Button("Cancel");

        finishButton = new Button("Finish");

        titleLabel.setStyle("-fx-underline: true;-fx-font-weight: bold;");
        AnchorPane.setTopAnchor(titleLabel, 20.0);
        AnchorPane.setLeftAnchor(titleLabel, 20.0);
        AnchorPane.setRightAnchor(titleLabel, 0.0);
//        tilleLabel.setMinHeight(33);

        AnchorPane.setTopAnchor(projectNameLabel, 60.0);
        AnchorPane.setLeftAnchor(projectNameLabel, 20.0);

        projectNameTextField.setPadding(new Insets(0));
        projectNameTextField.setPrefHeight(25);
        projectNameTextField.setStyle("-fx-border-color:gray; -fx-faint-focus-color: transparent;-fx-focus-color: transparent;");
        AnchorPane.setTopAnchor(projectNameTextField, 60.0);
        AnchorPane.setLeftAnchor(projectNameTextField, 140.0);
        AnchorPane.setRightAnchor(projectNameTextField, 20.0);
        projectNameTextField.setOnKeyReleased(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!projectNameTextField.getText().trim().isEmpty() && mediaSelected) {
                    finishButton.setDisable(false);
                } else {
                    finishButton.setDisable(true);
                }
            }
        });

        projectNameErrorLabel.setStyle("-fx-text-fill:red;");
        AnchorPane.setTopAnchor(projectNameErrorLabel, 82.0);
        AnchorPane.setLeftAnchor(projectNameErrorLabel, 140.0);

        AnchorPane.setTopAnchor(mediaLabel, 100.0);
        AnchorPane.setLeftAnchor(mediaLabel, 20.0);

        mediaPathLabel.setPrefHeight(25);
        mediaPathLabel.setPadding(new Insets(0, 5, 0, 5));
        mediaPathLabel.setStyle("-fx-text-fill:#b8b8b8;-fx-border-color:gray; -fx-faint-focus-color: transparent;-fx-focus-color: transparent;");//-fx-text-fill:black;
        AnchorPane.setTopAnchor(mediaPathLabel, 100.0);
        AnchorPane.setLeftAnchor(mediaPathLabel, 140.0);
        AnchorPane.setRightAnchor(mediaPathLabel, 95.0);

        mediaErrorLabel.setStyle("-fx-text-fill:red;");
        AnchorPane.setTopAnchor(mediaErrorLabel, 121.0);
        AnchorPane.setLeftAnchor(mediaErrorLabel, 140.0);

        browseMediaButton.setPrefHeight(25);
        browseMediaButton.setPadding(new Insets(0));
        AnchorPane.setTopAnchor(browseMediaButton, 100.0);
        AnchorPane.setRightAnchor(browseMediaButton, 20.0);
        browseMediaButton.setStyle("-fx-border-color:gray;");
        browseMediaButton.setTooltip(new Tooltip("Select Audio or Video file. *Required"));
        browseMediaButton.setOnMousePressed(getBrowseMediaButtonEventHandler(Runner.primaryStage));

        AnchorPane.setTopAnchor(textFileLabel, 140.0);
        AnchorPane.setLeftAnchor(textFileLabel, 20.0);

        textFilePathLabel.setPrefHeight(25);
        textFilePathLabel.setPadding(new Insets(0, 5, 0, 5));
        textFilePathLabel.setStyle("-fx-text-fill:#b8b8b8;-fx-border-color:gray; -fx-faint-focus-color: transparent;-fx-focus-color: transparent;");//-fx-text-fill:black;
        AnchorPane.setTopAnchor(textFilePathLabel, 140.0);
        AnchorPane.setLeftAnchor(textFilePathLabel, 140.0);
        AnchorPane.setRightAnchor(textFilePathLabel, 95.0);

        browseTextFileButton.setPrefHeight(25);
        browseTextFileButton.setPadding(new Insets(0));
        AnchorPane.setTopAnchor(browseTextFileButton, 140.0);
        AnchorPane.setRightAnchor(browseTextFileButton, 20.0);
        browseTextFileButton.setTooltip(new Tooltip("Select .txt or .srt file. (Optional)"));
        browseTextFileButton.setOnMousePressed(getBrowseTextFileButtonEventHandler(Runner.primaryStage));

        AnchorPane.setTopAnchor(projectLocationLabel, 180.0);
        AnchorPane.setLeftAnchor(projectLocationLabel, 20.0);

        projectLocationPathLabel.setPrefHeight(25);
        projectLocationPathLabel.setStyle("-fx-border-color:gray;");
        projectLocationPathLabel.setPadding(new Insets(0, 5, 0, 5));
        AnchorPane.setTopAnchor(projectLocationPathLabel, 180.0);
        AnchorPane.setLeftAnchor(projectLocationPathLabel, 140.0);
        AnchorPane.setRightAnchor(projectLocationPathLabel, 95.0);

        browseLocationButton.setPrefHeight(25);
        browseLocationButton.setPadding(new Insets(0));
        AnchorPane.setTopAnchor(browseLocationButton, 180.0);
        AnchorPane.setRightAnchor(browseLocationButton, 20.0);
        browseLocationButton.setStyle("-fx-border-color:gray;");
        browseLocationButton.setTooltip(new Tooltip("Select Project path..."));
        browseLocationButton.setOnMousePressed(getBrowseLocationButtonEventHandler(Runner.primaryStage));

        AnchorPane.setLeftAnchor(copyMediaCheckbox, 20.0);
        AnchorPane.setTopAnchor(copyMediaCheckbox, 220.0);
        copyMediaCheckbox.setDisable(true);
        copyMediaCheckbox.setStyle("-fx-faint-focus-color: transparent;-fx-focus-color: gray;");

        AnchorPane.setLeftAnchor(copyTextFileCheckbox, 20.0);
        AnchorPane.setTopAnchor(copyTextFileCheckbox, 250.0);
        copyTextFileCheckbox.setDisable(true);
        copyTextFileCheckbox.setStyle("-fx-faint-focus-color: transparent;-fx-focus-color: gray;");

        finishButton.setPrefHeight(25);
        finishButton.setPadding(new Insets(0));
        AnchorPane.setBottomAnchor(finishButton, 15.0);
        AnchorPane.setRightAnchor(finishButton, 115.0);
        finishButton.setStyle("-fx-border-color:gray;");
        finishButton.setPadding(new Insets(0, 20, 0, 20));
        finishButton.setDisable(true);
        finishButton.setOnMousePressed(getFinishButtonEventHandler());

        cancelButton.setPrefHeight(25);
        cancelButton.setPadding(new Insets(0));
        AnchorPane.setBottomAnchor(cancelButton, 15.0);
        AnchorPane.setRightAnchor(cancelButton, 20.0);
        cancelButton.setStyle("-fx-border-color:gray;");
        cancelButton.setPadding(new Insets(0, 20, 0, 20));
        cancelButton.setOnMousePressed(new EventHandler() {
            @Override
            public void handle(Event event) {
                close();
            }
        });

        anchorPane.getChildren().addAll(titleLabel, projectNameLabel, projectNameTextField, projectNameErrorLabel,
                mediaLabel, mediaPathLabel, browseMediaButton, mediaErrorLabel, textFileLabel,
                textFilePathLabel, browseTextFileButton, projectLocationLabel, projectLocationPathLabel,
                browseLocationButton, copyMediaCheckbox, copyTextFileCheckbox, finishButton, cancelButton
        );
        setScene(new Scene(anchorPane, 600, 400));
    }

    private static String getProjectsDefaultLocation() {
        String path = "";
        File file = new File("text files/ProjectsDefaultLocation.txt");
        Scanner fileInput;
        try {
            if (file.exists()) {
                fileInput = new Scanner(file); // Scanner object to get input from text file_
                if (fileInput.hasNext()) {
                    path = fileInput.nextLine();
                } else {
                    PrintWriter pw = new PrintWriter(file);
                    pw.print("E:\\ASUG test files");
                    pw.close();
                    path = "E:\\ASUG test files";
                }
                fileInput.close();
            } else {
                PrintWriter pw = new PrintWriter(file);
                pw.print("E:\\ASUG test files");
                pw.close();
                path = "E:\\ASUG test files";
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return path;
    }

    public EventHandler getBrowseMediaButtonEventHandler(Stage primaryStage) {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(".mp4/.wav/.mp3", "*.mp4", "*.wav", "*.mp3");

                fileChooser.getExtensionFilters().add(filter);
                File file = fileChooser.showOpenDialog(primaryStage);

                if (file != null) {
                    String filePath = file.toPath().toString();
                    mediaPathLabel.setText(filePath);
                    mediaPathLabel.setStyle("-fx-border-color:gray; -fx-faint-focus-color: transparent;-fx-focus-color: transparent;");//-fx-text-fill:black;
                    copyMediaCheckbox.setDisable(false);
                    mediaErrorLabel.setText("");
                    mediaSelected = true;
                    if (!projectNameTextField.getText().isEmpty()) {
                        finishButton.setDisable(false);
                    }
                }
            }
        };

    }

    public EventHandler getBrowseTextFileButtonEventHandler(Stage primaryStage) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(".txt/.srt", "*.txt", "*.srt");

                fileChooser.getExtensionFilters().add(filter);

                File file = fileChooser.showOpenDialog(primaryStage);

                if (file != null) {
                    String filePath = file.toPath().toString();
                    textFilePathLabel.setText(filePath);
                    textFilePathLabel.setStyle("-fx-text-fill:black;-fx-border-color:gray; -fx-faint-focus-color: transparent;-fx-focus-color: transparent;");//-fx-text-fill:black;
                    copyTextFileCheckbox.setDisable(false);
                }
            }
        };
    }

    public EventHandler getBrowseLocationButtonEventHandler(Stage primaryStage) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DirectoryChooser dir_chooser = new DirectoryChooser();

                File file = dir_chooser.showDialog(primaryStage);

                if (file != null) {
                    projectLocationPathLabel.setText(file.getAbsolutePath());
                }
            }
        };
    }

    private EventHandler<MouseEvent> getFinishButtonEventHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String projectName = projectNameTextField.getText().trim();
                if (!projectName.isEmpty()) {
                    String projectPath = projectLocationPathLabel.getText() + "\\" + projectName;
                    File projectFile = new File(projectPath);
//                    System.out.println("projectPath: " + projectPath);
                    if (!projectFile.exists()) {
                        if (!projectName.matches(".*[/\n\r\t\0\f`?*\\<>|\":].*")) { // if project name is valid
                            if (projectFile.mkdir()) {
                                projectNameErrorLabel.setText("");
                                if (!copyMediaCheckbox.isDisable()) { // if media file is selected
                                    String mediaPath = mediaPathLabel.getText();
                                    ArrayList<Subtitle> subtitlesArrayList = null;
                                    if (copyMediaCheckbox.isSelected()) {
                                        File mediaFile = new File(mediaPath);
//                                        System.out.println("mediaFile.getName(): " + mediaFile.getName());
                                        String fileName = mediaFile.getName();
                                        fileName = fileName.replace(".", "#");
                                        String fileExtension = fileName.split("#")[1];
                                        fileName = fileName.split("#")[0];
                                        TextInputDialog dialog = new TextInputDialog();
                                        dialog.setTitle("New Name");
                                        dialog.setHeaderText("Destination Media File Name");
                                        dialog.getEditor().setText(fileName);
                                        dialog.showAndWait();
                                        System.out.println("dialog.getResult(): " + dialog.getResult());

                                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please wait while the media file is being copied...");
                                        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                                        okButton.setVisible(false);
                                        alert.show();

                                        if (AllStaticMethods.copyFile(mediaPath, projectPath + "\\" + dialog.getResult() + "." + fileExtension)) {
                                            okButton.fire();
                                            mediaPath = projectPath + "\\" + dialog.getResult() + "." + fileExtension; //projectPath + "\\" + dialog.getResult() + "." + fileExtension
                                        } else {
                                            new Alert(Alert.AlertType.ERROR, "There was some error while copying media file !\n"
                                                    + "(may be you have used illegal characters [ \\, /, :, *, ?, <, >, |, ] in file name)", ButtonType.OK).showAndWait();

                                        }
                                    }
                                    if (!copyTextFileCheckbox.isDisable()) { // if text file is selected
                                        String textFilePath = textFilePathLabel.getText();
                                        File textFile = new File(textFilePath);
                                        String fileName = textFile.getName();
                                        fileName = fileName.replace(".", "#");
                                        String fileExtension = fileName.split("#")[1];
                                        fileName = fileName.split("#")[0];
                                        if (copyTextFileCheckbox.isSelected()) {
                                            TextInputDialog dialog = new TextInputDialog();
                                            dialog.setTitle("New Name");
                                            dialog.setHeaderText("Destination Text File Name");
                                            dialog.getEditor().setText(fileName);
                                            dialog.showAndWait();

                                            System.out.println("dialog.getResult(): " + dialog.getResult());

                                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please wait while the text file is being copied...");
                                            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                                            okButton.setVisible(false);
                                            alert.show();

                                            if (AllStaticMethods.copyFile(textFilePath, projectPath + "\\" + dialog.getResult() + "." + fileExtension)) {

                                                okButton.fire();
                                                textFilePath = projectPath + "\\" + dialog.getResult() + "." + fileExtension; // new text file path

                                            } else {
                                                new Alert(Alert.AlertType.ERROR, "There was some error while copying text file !\n"
                                                        + "(may be you have used illegal characters [ \\ / : * ? < > | ] in file name)", ButtonType.OK).showAndWait();
                                            }
                                        }
                                        if (fileExtension.equalsIgnoreCase("srt")) {
                                            subtitlesArrayList = AllStaticMethods.readSrtFile(textFilePath);
                                        } else if (fileExtension.equalsIgnoreCase("txt")) {
                                            subtitlesArrayList = new ArrayList<>();
                                            subtitlesArrayList.add(new Subtitle(AllStaticMethods.readTextFile(textFilePath)));
                                        }

                                    } else { // if text file not selected
                                        subtitlesArrayList = new ArrayList<>();
                                        subtitlesArrayList.add(new Subtitle("")); // adding a dummy subtitle 
                                    }
                                    Project project = new Project(projectPath, mediaPath, subtitlesArrayList);
                                    project.createProjectFile();

//                                   RESETTING RECENT PROJECTS FILE
                                    String text = AllStaticMethods.readTextFile("text files/recentProjects.txt");
                                    if (text != null) {
                                        AllStaticMethods.createFile("text files/recentProjects.txt", text + projectPath + "\n");
                                    } else {
                                        AllStaticMethods.createFile("text files/recentProjects.txt", projectPath + "\n");
                                    }
                                    MainScreenBorderPane mainScreenBorderPane = new MainScreenBorderPane(project);
                                    Scene scene = new Scene(mainScreenBorderPane, 1920, 990);
                                    mainScreenBorderPane.getMenuBar().getMenus().get(2).getItems().add(Runner.darkThemeMenuItem(scene));
                                    Runner.primaryStage.setResizable(true);
                                    Runner.primaryStage.setTitle("Naqqaal 1.0 - " + projectName);
                                    Runner.primaryStage.setScene(scene);
                                    Runner.primaryStage.setMaximized(true);

                                    close(); // closes new Project window

                                } else {//  media file is not selected
                                    projectFile.delete();
                                    mediaErrorLabel.setText("*Required");
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error while creating project directory !\n"
                                        + "You may have used an invalid character  \\ / : * ? < > |  in project name", ButtonType.OK);
                                alert.showAndWait();
                            }
                        } else {
                            projectNameErrorLabel.setText("\\ / : * ? < > |   are not allowed !");
                        }
                    } else {
                        projectNameErrorLabel.setText("The project '" + projectName + "' already exists !");
                    }
                } else {
                    projectNameErrorLabel.setText("*Required");
                }
            }
        };
    }

    public Button getBrowseMediaButton() {
        return browseMediaButton;
    }

    public Button getBrowseTextFileButton() {
        return browseTextFileButton;
    }

    public Button getBrowseLocationButton() {
        return browseLocationButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getFinishButton() {
        return finishButton;
    }

    public TextField getProjectNameTextField() {
        return projectNameTextField;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public Label getProjectNameLabel() {
        return projectNameLabel;
    }

    public Label getProjectNameErrorLabel() {
        return projectNameErrorLabel;
    }

    public Label getMediaLabel() {
        return mediaLabel;
    }

    public Label getMediaPathLabel() {
        return mediaPathLabel;
    }

    public Label getMediaErrorLabel() {
        return mediaErrorLabel;
    }

    public Label getTextFileLabel() {
        return textFileLabel;
    }

    public Label getTextFilePathLabel() {
        return textFilePathLabel;
    }

    public Label getProjectLocationLabel() {
        return projectLocationLabel;
    }

    public Label getProjectLocationPathLabel() {
        return projectLocationPathLabel;
    }

    public CheckBox getCopyMediaCheckbox() {
        return copyMediaCheckbox;
    }

    public CheckBox getCopyTextFileCheckbox() {
        return copyTextFileCheckbox;
    }

}
