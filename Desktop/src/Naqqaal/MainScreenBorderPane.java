package Naqqaal;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.StatusBar;
import javafx.animation.Timeline;
//import javafx.animation.KeyFrame;

/**
 *
 * @author Lenovo 520
 */
public class MainScreenBorderPane extends BorderPane {

    private MenuBar menuBar;
    private SplitPane mainSplitPane;
    private Project project;
    private BorderPane videoTextBorderPane;
    private RangeSliderAnchorPane rangeSliderAnchorPane;
    private VideoControlsAnchorPane videoControlsAnchorPane;
    private SplitPane video_text_SplitPane;
    private VideoAreaPane videoAreaPane;
    private TextEditorAnchorPane textEditorAnchorPane;
    private StatusBar statusBar;
    private ProjectsTreeView projectsTreeView;
    public static Subtitle currentSubtitle;
    Timer timer = null;
    private boolean videoSliderClicked = false;
    private int indicesScanned = 0; // for find functionality
    private boolean edited = false; // for find functionality
    private ArrayList<int[]> occurenceIndices = new ArrayList(); //for find functionality: contains occurances indices in the array form {nodeNo , index of occurance}

    public MainScreenBorderPane(Project project_) {

        this.project = project_;

        menuBar = new MenuBar();
        videoTextBorderPane = new BorderPane();
        mainSplitPane = new SplitPane();
        rangeSliderAnchorPane = new RangeSliderAnchorPane();
        videoControlsAnchorPane = new VideoControlsAnchorPane();
        video_text_SplitPane = new SplitPane();
        videoAreaPane = new VideoAreaPane(this.project.getMediaPath());
        textEditorAnchorPane = new TextEditorAnchorPane(this.project.getSubtitleArrayList(), this.project.getSpeakerNamesArrayList());
        statusBar = new StatusBar();
        prepareMenuBar();

        rangeSliderAnchorPane.getRangeSlider().lowValueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                String timeRange = rangeSliderAnchorPane.getTimeRangeLabel().getText();
                String upperValue = timeRange.split("-->")[1].trim();
                int lowValue = (int) videoControlsAnchorPane.getVideoSlider().getValue()
                        + (int) rangeSliderAnchorPane.getRangeSlider().getLowValue();
                if (lowValue >= 0) {
                    rangeSliderAnchorPane.getTimeRangeLabel().setText(
                            millisToHMSM_String(lowValue) + " --> " + upperValue);
                } else {
                    rangeSliderAnchorPane.getTimeRangeLabel().setText(
                            millisToHMSM_String(0) + " --> " + upperValue);
                }

            }
        });
        rangeSliderAnchorPane.getRangeSlider().highValueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                String timeRange = rangeSliderAnchorPane.getTimeRangeLabel().getText();
                String lowerValue = timeRange.split("-->")[0].trim();
                int highValue = ((int) videoControlsAnchorPane.getVideoSlider().getValue())
                        + ((int) rangeSliderAnchorPane.getRangeSlider().getHighValue());
                int totalVideoDuration = (int) videoControlsAnchorPane.getVideoSlider().getMax();
                if (highValue <= totalVideoDuration) {
                    rangeSliderAnchorPane.getTimeRangeLabel().setText(lowerValue + " --> "
                            + millisToHMSM_String(highValue));
                } else {
                    rangeSliderAnchorPane.getTimeRangeLabel().setText(lowerValue + " --> "
                            + millisToHMSM_String(totalVideoDuration));

                }
            }
        });
        textEditorAnchorPane.getAddTimeStamp_Button().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                double totalStartMillis = videoControlsAnchorPane.getVideoSlider().getValue()
                        + rangeSliderAnchorPane.getRangeSlider().getLowValue();
                double totalStopMillis = videoControlsAnchorPane.getVideoSlider().getValue()
                        + rangeSliderAnchorPane.getRangeSlider().getHighValue();

                double startTime = totalStartMillis;
                double startHours = startTime / 3600000;
                int startHoursInt = (int) startHours;
                double startMins = ((startHours - startHoursInt) * 60);
                int startMinsInt = (int) startMins;
                double startSec = ((startMins - startMinsInt) * 60);
                int startSecInt = (int) startSec;
//                double startMilli = ((startSec - startSecInt) * 1000);
//                int startMilliInt = (int) startMilli;
                // milli seconds are extracted from range slider time range label otherwise milli seconds were not consistent (for accuracy purposes)
                String startMillis = rangeSliderAnchorPane.getTimeRangeLabel().getText().split("-->")[0].split(",")[1].trim();
                int startMilliInt = Integer.parseInt(startMillis);
                currentSubtitle.setStartHours(startHoursInt);
                currentSubtitle.setStartMin((int) startMins);
                currentSubtitle.setStartSec(startSecInt);
                currentSubtitle.setStartMilliSec(startMilliInt);
                currentSubtitle.setTotalStartMillis((int) totalStartMillis);
                textEditorAnchorPane.getListView().getItems().get(currentSubtitle.getId() - 1).getSubtitle().setTotalStartMillis((int) totalStartMillis);
                textEditorAnchorPane.getListView().getItems().get(currentSubtitle.getId() - 1).getSubtitle().setTotalStopMillis((int) totalStopMillis);
                double StopTime = totalStopMillis;
                double StopHours = StopTime / 3600000;
                int StopHoursInt = (int) StopHours;
                double StopMins = ((StopHours - StopHoursInt) * 60);
                int StopMinsInt = (int) StopMins;
                double StopSec = ((StopMins - StopMinsInt) * 60);
                int StopSecInt = (int) StopSec;
//                double StopMilli = ((StopSec - StopSecInt) * 1000);
//                int StopMilliInt = (int) StopMilli;
// milli seconds are extracted from range slider time range label otherwise milli seconds were not consistent (for accuracy purposes)
                String stopMillis = rangeSliderAnchorPane.getTimeRangeLabel().getText().split("-->")[1].split(",")[1].trim();
                int stopMilliInt = Integer.parseInt(stopMillis);

                currentSubtitle.setStopHours(StopHoursInt);
                currentSubtitle.setStopMin((int) StopMins);
                currentSubtitle.setStopSec(StopSecInt);
                currentSubtitle.setStopMilliSec(stopMilliInt);
                currentSubtitle.setTotalStopMillis((int) totalStopMillis);

                TextNode textNode = textEditorAnchorPane.getListView().getItems().get(currentSubtitle.getId() - 1);
                if (startTime >= 0) {
                    textNode.getLabelStartHours().setText(startHoursInt + "");
                    textNode.getLabelStartMin().setText((int) startMins + "");
                    textNode.getTextfieldStartSec().setText(startSecInt + "");
                    textNode.getTextfieldStartMilli().setText(startMilliInt + "");
                } else {
                    textNode.getLabelStartHours().setText(0 + "");
                    textNode.getLabelStartMin().setText(0 + "");
                    textNode.getTextfieldStartSec().setText(0 + "");
                    textNode.getTextfieldStartMilli().setText(0 + "");
                }

                textNode.getLabelStopHours().setText(StopHoursInt + "");
                textNode.getLabelStopMin().setText((int) StopMins + "");
                textNode.getTextfieldStopSec().setText(StopSecInt + "");
                textNode.getTextfieldStopMilli().setText(stopMilliInt + "");

//              sorting wrt to start time
//                Collections.sort(project.getSubtitleArrayList()); //sort wrt totalStartMillis - code in Subtitle class
                Collections.sort(textEditorAnchorPane.getListView().getItems());//sort wrt totalStartMillis - code in TextNode class
                for (int i = 0; i < textEditorAnchorPane.getListView().getItems().size(); i++) {
                    // loop for reseting ids 
                    int index = i + 1;
                    System.out.println("index: " + index);
                    textEditorAnchorPane.getListView().getItems().get(i).getSubtitle().setId(index);
//                    project.getSubtitleArrayList().get(i).setId(index);
                    textEditorAnchorPane.getListView().getItems().get(i).getLabel_SubtitleID().setText(index + "");
                }

                textEditorAnchorPane.getListView().scrollTo((currentSubtitle.getId() - 1));
                textEditorAnchorPane.getListView().getSelectionModel().select((currentSubtitle.getId() - 1));
            }
        });
        videoControlsAnchorPane.getVideoSlider().setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                videoSliderClicked = false;
            }
        });
        videoControlsAnchorPane.getVideoSlider().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // if video slider clicked do:
                videoSliderClicked = true;
                // 1) seek video to that time
                videoAreaPane.getMediaPlayer().seek(Duration.millis(videoControlsAnchorPane.getVideoSlider().getValue()));
                // 2) search for the subtitle at that time and highlight that subtitle in listView
//                Subtitle subtitle = Subtitle.searchSubtitleByTime((int) Duration.seconds(
//                        videoControlsAnchorPane.getVideoSlider().getValue()).toMillis(), project.getSubtitleArrayList());
//                if (subtitle != null) {
//                    // if subtitle found at that time
//                    textEditorAnchorPane.getListView().getSelectionModel().select(subtitle.getId() - 1);
//                    textEditorAnchorPane.getListView().scrollTo(subtitle.getId() - 1);
//                } else {
//
//                    textEditorAnchorPane.getListView().getSelectionModel().select(project.getSubtitleArrayList().size() - 1);
//                    textEditorAnchorPane.getListView().scrollTo(project.getSubtitleArrayList().size() - 1);
//                }
//
//                // 3) change the current subtitle to subtitle
//                currentSubtitle = subtitle;

                // 4) change the time at time Labels on video controls anchorPane
                double currentTime = videoAreaPane.getMediaPlayer().getCurrentTime().toMillis();
                double currentHours = currentTime / 3600000;
                int currentHoursInt = (int) currentHours;
                double currentMins = ((currentHours - currentHoursInt) * 60);
                int currentMinsInt = (int) currentMins;
                int currentSec = (int) ((currentMins - currentMinsInt) * 60);
                videoControlsAnchorPane.getCurrentTimeLabel().setText(currentHoursInt + ":" + ((int) currentMins) + ":" + currentSec);

                // 5) update range slider anchor pane
                prepareRangeSliderPane();
            }
        });
        videoControlsAnchorPane.getVolumeSlider().setValue(videoAreaPane.getMediaPlayer().getVolume() * 100); // mediaPlayer.getVolume() gives value between 0 and 1 but max value for slider is set to 100
        videoControlsAnchorPane.getVolumeSlider().valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                videoAreaPane.getMediaPlayer().setVolume(videoControlsAnchorPane.getVolumeSlider().getValue() / 100);
            }
        });
        videoControlsAnchorPane.getPlayPauseButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                videoControlsAnchorPane.getStopButton().setDisable(false);
                ToggleButton playPauseButton = videoControlsAnchorPane.getPlayPauseButton();
                boolean selected = playPauseButton.isSelected();
                if (!selected) {
                    videoAreaPane.getMediaPlayer().play();
                    playPauseButton.setText("⏸");
                    playPauseButton.setTooltip(new Tooltip("Pause"));
                    rangeSliderAnchorPane.setDisable(true);
                } else {
                    videoAreaPane.getMediaPlayer().pause();
                    playPauseButton.setText("▶");
                    playPauseButton.setTooltip(new Tooltip("Play"));
                    rangeSliderAnchorPane.setDisable(false);
                    prepareRangeSliderPane();
                }
            }
        });
        videoControlsAnchorPane.getStopButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                videoAreaPane.getMediaPlayer().stop();
                ToggleButton playPauseButton = videoControlsAnchorPane.getPlayPauseButton();
                playPauseButton.setText("▶");
                playPauseButton.setSelected(false);
                videoControlsAnchorPane.getStopButton().setDisable(true);
            }
        });
        videoControlsAnchorPane.getSlowButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double rate = videoAreaPane.getMediaPlayer().getRate();
                if (rate != 0.25) {
                    videoAreaPane.getMediaPlayer().setRate(rate - 0.25);
                }
                showSpeedPopup(videoAreaPane.getMediaPlayer().getRate() + "x", videoControlsAnchorPane.getSlowButton());
            }
        });
        videoControlsAnchorPane.getFastButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double rate = videoAreaPane.getMediaPlayer().getRate();
                if (rate != 2) {
                    videoAreaPane.getMediaPlayer().setRate(rate + 0.25);
                }
                showSpeedPopup(videoAreaPane.getMediaPlayer().getRate() + "x", videoControlsAnchorPane.getFastButton());
            }
        });
        videoControlsAnchorPane.getSkipBackwardButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                videoAreaPane.getMediaPlayer().seek(Duration.millis(videoControlsAnchorPane.getVideoSlider().getValue() - 5000));
            }
        });
        videoControlsAnchorPane.getSkipForwardButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                videoAreaPane.getMediaPlayer().seek(Duration.millis(videoControlsAnchorPane.getVideoSlider().getValue() + 5000));
            }
        });
        videoAreaPane.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {

            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
//              1) Change value of video Slider
                videoControlsAnchorPane.getVideoSlider().setValue(newValue.toMillis());
//                System.out.println("newValue.toMillis(): " + ((int) newValue.toMillis() / 1000)
//                        + "   getTotalStartMillis(): " + ((int) subtitlesArrayList.get(currentSubtitleIndex).getTotalStartMillis() / 1000));
                // 2) search for the subtitle at that time and highlight that subtitle in listView
                Subtitle subtitle = Subtitle.searchSubtitleByTime((int) newValue.toMillis(), textEditorAnchorPane.getListView().getItems());
                if (!videoSliderClicked) { // if video Slider is not clicked
                    if (subtitle != null) {
                        // if subtitle found at that time
                        textEditorAnchorPane.getListView().getSelectionModel().select(subtitle.getId() - 1);
                        textEditorAnchorPane.getListView().scrollTo(subtitle.getId() - 1);
                    } else {
                        textEditorAnchorPane.getListView().getSelectionModel().select(textEditorAnchorPane.getListView().getItems().size() - 1);
                        textEditorAnchorPane.getListView().scrollTo(textEditorAnchorPane.getListView().getItems().size() - 1);
                    }

                    // 3) change the current subtitle to subtitle
                    currentSubtitle = subtitle;
                }
                // 4) change the time at time Labels on video controls anchorPane
                double currentTime = videoAreaPane.getMediaPlayer().getCurrentTime().toMillis();
                double currentHours = currentTime / 3600000;
                int currentHoursInt = (int) currentHours;
                double currentMins = ((currentHours - currentHoursInt) * 60);
                int currentMinsInt = (int) currentMins;
                double currentSec = ((currentMins - currentMinsInt) * 60);
                int currentSecInt = (int) currentSec;
                double currentMilli = ((currentSec - currentSecInt) * 1000);
                int currentMilliInt = (int) currentMilli;
                String timeText = String.format("%01d:%02d:%02d:%03d", (int) currentHoursInt, (int) currentMins, currentSecInt, currentMilliInt);
                videoControlsAnchorPane.getCurrentTimeLabel().setText(timeText);
            }
        });
        videoAreaPane.getMediaPlayer().setOnReady(() -> {
            double totalDurationMillis = videoAreaPane.getMediaPlayer().getTotalDuration().toMillis();
            videoControlsAnchorPane.getVideoSlider().setMax(totalDurationMillis);
            mainSplitPane.setDividerPosition(0, 0.10);

            double totalTime = totalDurationMillis;
            double totalHours = totalTime / 3600000;
            int totalHoursInt = (int) totalHours;
            double totalMins = ((totalHours - totalHoursInt) * 60);
            int totalMinsInt = (int) totalMins;
            double totalSec = ((totalMins - totalMinsInt) * 60);
            int totalSecInt = (int) totalSec;
            double totalMilli = ((totalSec - totalSecInt) * 1000);
            int totalMilliInt = (int) totalMilli;
            String timeText = String.format("%01d:%02d:%02d:%03d", (int) totalHoursInt, (int) totalMins, totalSecInt, totalMilliInt);
            videoControlsAnchorPane.getTotalTimeLabel().setText("   / " + timeText);
        });

        ObservableList<TextNode> items = textEditorAnchorPane.getListView().getItems();

        for (int i = 0; i < items.size(); i++) {
            TextNode textNode = items.get(i);
            Button playPauseSubtitleButton = textNode.getPlaySubtitleButton();
            playPauseSubtitleButton.setOnMousePressed(new PlayPauseSubtitleEventHandler(textNode.getSubtitle(), playPauseSubtitleButton));

//            textNode.getDeleteSubtitleButton().setOnMousePressed(new EventHandler() {
//                @Override
//                public void handle(Event event) {
//                    if (items.size() > 1) {
////                        currentSubtitle = textEditorAnchorPane.getListView().getItems().get(textNode.getSubtitle().getId() - 2).getSubtitle();
//                        items.remove(textNode);
//                        for (int i = 0; i < items.size(); i++) {
//                            // can be done in parallel to improve performance
//                            items.get(i).getLabel_SubtitleID().setText((i + 1) + "");
//                        }
//                    } else {
//                        items.get(0).clear();
//                    }
//                }
//            });
        }
        if ((ProjectsTreeView.getExtension(project.getMediaPath()).equals("mp4"))
                || (ProjectsTreeView.getExtension(project.getMediaPath()).equals("mkv"))) {
            video_text_SplitPane.getItems().add(videoAreaPane);
        }
        video_text_SplitPane.getItems().add(textEditorAnchorPane);
        video_text_SplitPane.setDividerPosition(0, 0.25);

        videoTextBorderPane.setTop(rangeSliderAnchorPane);
        videoTextBorderPane.setBottom(videoControlsAnchorPane);
        videoTextBorderPane.setCenter(video_text_SplitPane);

//        mainSplitPane.setDividerPosition(0, 0.15);
        projectsTreeView = new ProjectsTreeView();
        projectsTreeView.getTreeView().setContextMenu(init_treeviewContextMenu());

        mainSplitPane.getItems().add(projectsTreeView);
        mainSplitPane.getItems().add(videoTextBorderPane);

        setTop(menuBar);
        setCenter(mainSplitPane);
        setBottom(statusBar);
        currentSubtitle = textEditorAnchorPane.getListView().getItems().get(0).getSubtitle(); // this line must be after initialization of subtitleArrayList 
    }

    private void prepareMenuBar() {

        Menu menu_File = new Menu("File");
        MenuItem menuItem_NewProject = new MenuItem("New Project...");
        MenuItem menuItem_OpenProject = new MenuItem("Open Project...");
//        Menu menuItem_OpenRecentProject = new Menu("Open Recent Project"); // a sub menu will open with names all recent projects
//        menuItem_OpenRecentProject.getItems().addAll(new MenuItem("dummy"), new MenuItem("dummy"), new MenuItem("dummy"), new MenuItem("dummy"), new MenuItem("dummy"));
        MenuItem menuItem_OpenTextFile = new MenuItem("Open txt or srt file...");
        MenuItem menuItem_Save = new MenuItem("Save");
        MenuItem menuItem_SaveAs = new MenuItem("Save As...");
        MenuItem menuItem_ExportSrtFile = new MenuItem("Export srt file...");
        MenuItem menuItem_ExportTextFile = new MenuItem("Export transcript file...");
        MenuItem menuItem_GenerateTranscript = new MenuItem("Generate Transcript...");
        MenuItem menuItem_Exit = new MenuItem("Exit");
        menu_File.getItems().addAll(menuItem_NewProject, menuItem_OpenProject, menuItem_OpenTextFile, menuItem_Save, menuItem_SaveAs, menuItem_ExportSrtFile, menuItem_ExportTextFile, menuItem_GenerateTranscript, menuItem_Exit);

        Menu menu_Edit = new Menu("Edit");
        MenuItem menuItem_Cut = new MenuItem("Cut");
        MenuItem menuItem_Copy = new MenuItem("Copy");
        MenuItem menuItem_Paste = new MenuItem("Paste");// ; greek question mark
        MenuItem menuItem_Find = new MenuItem("Find...");
        MenuItem menuItem_Replace = new MenuItem("Replace...");
        menu_Edit.getItems().addAll(menuItem_Cut, menuItem_Copy, menuItem_Paste, menuItem_Find, menuItem_Replace);

        Menu menu_Settings = new Menu("Settings");
// ********** CODE FOR DARK THEME IS IN RUNNER CLASS *************//
        MenuItem menuItem_removeSpeaker = new MenuItem("Remove a Speaker");
        MenuItem menuItem_removeFontStyle = new MenuItem("Remove a Font Style");
        menu_Settings.getItems().addAll(menuItem_removeSpeaker, menuItem_removeFontStyle);

        menuItem_NewProject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NewProjectStage newProjectScreen = new NewProjectStage();
                newProjectScreen.showAndWait();
            }
        });// New Project
        menuItem_OpenProject.setOnAction(new OpenProjectEventHandler()); // Open Project
        menuItem_OpenTextFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to change text file or srt file.\n"
                        + "Are you sure you want to continue ?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CANCEL) {
                    event.consume();
                } else if (alert.getResult() == ButtonType.OK) {

//                    project.getSubtitleArrayList().clear();
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(".txt/.srt", "*.txt", "*.srt");

                    fileChooser.getExtensionFilters().add(filter);

                    File file = fileChooser.showOpenDialog(Runner.primaryStage);

                    if (file != null) {
                        textEditorAnchorPane.getListView().getItems().clear();
                        String filePath = file.getPath();
                        String fileName = file.getName();
                        fileName = fileName.replace(".", "#");
                        String fileExtension = fileName.split("#")[1];
                        if (fileExtension.equalsIgnoreCase("srt")) {
                            ArrayList<Subtitle> readSrtFile = AllStaticMethods.readSrtFile(filePath);
                            project.setSubtitleArrayList(readSrtFile);

                            for (int i = 0; i < readSrtFile.size(); i++) {
                                textEditorAnchorPane.getListView().getItems().add(new TextNode(readSrtFile.get(i), project.getSpeakerNamesArrayList()));
                            }
                        } else if (fileExtension.equalsIgnoreCase("txt")) {
                            String text = AllStaticMethods.readTextFile(filePath);
//                            ArrayList<Subtitle> subtitleArrayList = new ArrayList();
                            Subtitle subtitle = new Subtitle(text);
//                            subtitleArrayList.add(subtitle);
//                            project.getSubtitleArrayList().clear();
//                            project.getSubtitleArrayList().add(subtitle);
                            currentSubtitle = subtitle;
                            textEditorAnchorPane.getListView().getItems().add(new TextNode(subtitle, project.getSpeakerNamesArrayList()));
                        }
                    }
                }

            }
        }); //Open txt or srt file...
        menuItem_Save.setOnAction(new EventHandler<ActionEvent>() {
//            needs testing....;
            @Override
            public void handle(ActionEvent event) {
                ObservableList<TextNode> items = textEditorAnchorPane.getListView().getItems();
                ArrayList<Subtitle> subtitleArrayList = new ArrayList();
                for (int i = 0; i < items.size(); i++) {
                    Subtitle subtitle = new Subtitle();
                    TextNode item = items.get(i);
                    subtitle.setId(Integer.parseInt(item.getLabel_SubtitleID().getText()));
                    subtitle.setCompleted(item.getCompletedCheckBox().isSelected());
                    subtitle.setNote(item.getSubtitle().getNote()); // already set at the time when the note is written
                    Object value = item.getSpeakerNames_ComboBox().getValue();
                    if (value != null) {
                        subtitle.setSpeaker(value.toString());
                    }
                    subtitle.setStartHours(Integer.parseInt(item.getLabelStartHours().getText()));
                    subtitle.setStartMilliSec(Integer.parseInt(item.getTextfieldStartMilli().getText()));
                    subtitle.setStartMin(Integer.parseInt(item.getLabelStartMin().getText()));
                    subtitle.setStartSec(Integer.parseInt(item.getTextfieldStartSec().getText()));
                    subtitle.setStopHours(Integer.parseInt(item.getLabelStopHours().getText()));
                    subtitle.setStopMilliSec(Integer.parseInt(item.getLabelStopHours().getText()));
                    subtitle.setStopMin(Integer.parseInt(item.getLabelStopMin().getText()));
                    subtitle.setStopSec(Integer.parseInt(item.getTextfieldStopSec().getText()));
                    subtitle.setText(item.getTextArea().getText());
                    subtitle.setTotalStartMillis(item.getSubtitle().getTotalStartMillis()); // alreadty set at the time when add time stamp button was clicked
                    subtitle.setTotalStopMillis(item.getSubtitle().getTotalStopMillis()); // alreadty set at the time when add time stamp button was clicked
                    subtitleArrayList.add(subtitle);
                }
                project.setSubtitleArrayList(subtitleArrayList);
                project.createProjectFile();

            }
        }); // Save
        menuItem_SaveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String projectPath = project.getProjectPath();
                String projectName = projectPath.substring(projectPath.lastIndexOf("\\") + 1);

                DirectoryChooser dir_chooser = new DirectoryChooser();
                File file = dir_chooser.showDialog(Runner.primaryStage);

                if (file != null) {
                    String newDirPath = file.toPath().toString() + "\\" + projectName;
                    System.out.println("projectPath: " + projectPath + "   newDirPath: " + newDirPath);
                    if (!newDirPath.equalsIgnoreCase(projectPath)) {
                        // copy directory to new dir path

                        AllStaticMethods.copyFile(projectPath, newDirPath);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING,
                                "A directory named '" + projectName + "' already exists at the destination folder !\n"
                                + "Are sure to replace it with project directory?",
                                ButtonType.OK, ButtonType.CANCEL);
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.OK) {
                            // copy directory to new dir path
                            boolean copyFile = AllStaticMethods.copyFile(projectPath, newDirPath);

                        } else if (alert.getResult() == ButtonType.CANCEL) {
                            event.consume();
                        }
                    }
                }
            }
        }); // Save As
        menuItem_ExportSrtFile.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                FileChooser fileChooser = new FileChooser();
                ExtensionFilter filter = new ExtensionFilter("srt file", "*.srt");
                fileChooser.getExtensionFilters().add(filter);
                String projectPath = project.getProjectPath();
                String projectName = projectPath.substring(projectPath.lastIndexOf("\\") + 1);
                File projectDirectory = new File(projectPath);
                fileChooser.setInitialDirectory(projectDirectory);
                fileChooser.setInitialFileName(projectName);
                File file = fileChooser.showSaveDialog(Runner.primaryStage);
                if (file != null) {
                    boolean writeSrtFile = Subtitle.writeSrtFile(textEditorAnchorPane.getListView().getItems(), (file.toPath().toString()));
                    if (writeSrtFile) {
                        statusBar.setText("Subtitle file saved successfully !");
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        statusBar.setText("Ok !");
                                    }
                                });
                            }
                        }, 3000);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "There was some error in saving the subtitle file !").show();
                    }
                }

            }
        }); //Export srt file...
        menuItem_ExportTextFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                FileChooser fileChooser = new FileChooser();
                ExtensionFilter filter = new ExtensionFilter("Text File", "*.txt");
                fileChooser.getExtensionFilters().add(filter);
                String projectPath = project.getProjectPath();
                String projectName = projectPath.substring(projectPath.lastIndexOf("\\") + 1);
                File projectDirectory = new File(projectPath);
                fileChooser.setInitialDirectory(projectDirectory);
                fileChooser.setInitialFileName(projectName);
                File file = fileChooser.showSaveDialog(Runner.primaryStage);
                if (file != null) {
                    boolean writeSrtFile = Subtitle.writeTxtFile(textEditorAnchorPane.getListView().getItems(), (file.toPath().toString()));
                    if (writeSrtFile) {
                        statusBar.setText("Transcript file saved successfully !");
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        statusBar.setText("Ok !");
                                    }
                                });
                            }
                        }, 3000);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "There was some error in saving the subtitle file !").show();
                    }
                }
            }
        });
        menuItem_GenerateTranscript.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Are you want to send the audio for transcription to the server?\n"
                        + "This might take a few minutes.", ButtonType.OK, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CANCEL) {
                    event.consume();
                } else if (alert.getResult() == ButtonType.OK) {
                    Alert alert1 = new Alert(AlertType.CONFIRMATION);
                    alert1.getButtonTypes().removeAll(ButtonType.OK);
                    Button cancelButton = (Button) alert1.getDialogPane().lookupButton(ButtonType.CANCEL);
                    cancelButton.setVisible(false);
                    alert1.show();
                    if ((ProjectsTreeView.getExtension(project.getMediaPath()).equals("mp4"))
                            || (ProjectsTreeView.getExtension(project.getMediaPath()).equals("mkv"))) {
//                        "Extracting Audio..."
                        alert1.setContentText("Extracting Audio...");
                        String audioPath = project.getProjectPath() + "\\extractedAudio.wav";
                        if (AllStaticMethods.videoToAudio(new File(project.getMediaPath()), new File(audioPath), "wav")) {
                            alert1.setContentText("Sending file to server for speech recognition...");
                            String routeURL = "http://192.168.10.6:5000/desktop";
                            String sendFileToServer = AllStaticMethods.sendFileToServer(routeURL, audioPath);
                            System.out.println("RESPONSE FROM SERVER: " + sendFileToServer);
                            textEditorAnchorPane.getListView().getItems().remove(1, textEditorAnchorPane.getListView().getItems().size() - 1);
                            textEditorAnchorPane.getListView().getItems().get(0).clear();
                            textEditorAnchorPane.getListView().getItems().get(0).getTextArea().setText(sendFileToServer);
                        } else {

                            new Alert(AlertType.ERROR, "There was some error in audio extraction !").show();
                        }
                        cancelButton.fire();
                    } else if ((ProjectsTreeView.getExtension(project.getMediaPath()).equals("mp3"))
                            || (ProjectsTreeView.getExtension(project.getMediaPath()).equals("wav"))) {
//                        "Extracting Audio..."
                        
                        alert1.setContentText("Sending file to server for speech recognition...");
                        String routeURL = "http://192.168.10.6:5000/desktop";
                        String sendFileToServer = AllStaticMethods.sendFileToServer(routeURL, project.getMediaPath());
                        System.out.println("RESPONSE FROM SERVER: " + sendFileToServer);
                        textEditorAnchorPane.getListView().getItems().remove(1, textEditorAnchorPane.getListView().getItems().size() - 1);
                        textEditorAnchorPane.getListView().getItems().get(0).clear();
                        textEditorAnchorPane.getListView().getItems().get(0).getTextArea().setText(sendFileToServer);
                        cancelButton.fire();
                    }

                }
            }
        });
        menuItem_Exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    System.exit(0);
                } else if (alert.getResult() == ButtonType.CANCEL) {
                    event.consume();
                }
            }
        });

        menuItem_Cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    Robot r = new Robot();
                    r.keyPress(KeyEvent.VK_CONTROL);
                    r.keyPress(KeyEvent.VK_X);
                    r.keyRelease(KeyEvent.VK_X);
                    r.keyRelease(KeyEvent.VK_CONTROL);
                } catch (AWTException ex) {
                    System.out.println("MainScreenBorderPane:menuItem_Cut.setOnAction: " + ex);
                }
            }
        });
        menuItem_Copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    Robot r = new Robot();
                    r.keyPress(KeyEvent.VK_CONTROL);
                    r.keyPress(KeyEvent.VK_C);
                    r.keyRelease(KeyEvent.VK_C);
                    r.keyRelease(KeyEvent.VK_CONTROL);
                } catch (AWTException ex) {
                    System.out.println("MainScreenBorderPane:menuItem_Copy.setOnAction: " + ex);
                }
            }
        });
        menuItem_Paste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    Robot r = new Robot();
                    r.keyPress(KeyEvent.VK_CONTROL);
                    r.keyPress(KeyEvent.VK_V);
                    r.keyRelease(KeyEvent.VK_V);
                    r.keyRelease(KeyEvent.VK_CONTROL);
                } catch (AWTException ex) {
                    System.out.println("MainScreenBorderPane:menuItem_Paste.setOnAction: " + ex);
                }
            }
        });
        menuItem_Find.setOnAction(new EventHandler<ActionEvent>() { //NOT WORKING ;
            @Override
            public void handle(ActionEvent actionEvent) {
                TextInputDialog inputDialog = new TextInputDialog();
                inputDialog.setContentText("Find what :");
                inputDialog.setTitle("Find");
                inputDialog.setHeaderText(null);
                inputDialog.show();

                Button findNext = (Button) inputDialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                findNext.setText("Next");
                Button findPre = (Button) inputDialog.getDialogPane().lookupButton(ButtonType.OK);
                findPre.setText("Prev.");

                inputDialog.setOnCloseRequest(e -> {
                    e.consume();
                });
//                inputDialog.getDialogPane().getScene().getWindow().setOnHidden(new EventHandler<WindowEvent>() {
//                    @Override
//                    public void handle(WindowEvent event) {
//                        System.out.println("Hidden !");
//                        indicesScanned = 0;
//                    }
//                });
                findNext.addEventFilter(ActionEvent.ACTION, new FindEventHandler(inputDialog.getEditor(), true)); // true means next button is clicked
                findPre.addEventFilter(ActionEvent.ACTION, new FindEventHandler(inputDialog.getEditor(), false));// false means previous button is clicked

            }
        });
        menuItem_Replace.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ReplaceInputDialog replaceInputDialog = new ReplaceInputDialog();
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(Runner.primaryStage);
                stage.setTitle("Replace");
                stage.setResizable(false);
                stage.setScene(new Scene(replaceInputDialog, 320, 200));

                replaceInputDialog.getCloseButton().setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        stage.close();
                    }
                });
                replaceInputDialog.getFindNextButton().setOnAction(new FindEventHandler(replaceInputDialog.getReplaceTextField(), true));
                replaceInputDialog.getReplaceButton().setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        TextArea textArea = textEditorAnchorPane.getListView().getItems().get(occurenceIndices.get(indicesScanned - 1)[0]).getTextArea();
                        String oldText = textArea.getText();
                        String newText = oldText.replaceAll(
                                oldText.substring(textArea.getAnchor(), textArea.getCaretPosition()), replaceInputDialog.getWithTextField().getText());
                        textArea.setText(newText);
                    }
                });
                replaceInputDialog.getReplaceAllButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!replaceInputDialog.getReplaceTextField().getText().isEmpty()) {
                            Alert alert = new Alert(AlertType.CONFIRMATION, "You are about to replace all the occurences of the word"
                                    + " \"" + replaceInputDialog.getReplaceTextField().getText() + "\" with \""
                                    + replaceInputDialog.getWithTextField().getText() + "\""
                                    + "\n Are you sure you want to continue ?");
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.CANCEL) {
                                event.consume();
                            } else if (alert.getResult() == ButtonType.OK) {
                                ObservableList<TextNode> listViewItems = textEditorAnchorPane.getListView().getItems();
                                for (int i = 0; i < listViewItems.size(); i++) {
                                    String oldText = listViewItems.get(i).getTextArea().getText().toLowerCase();
                                    String replaceText = replaceInputDialog.getReplaceTextField().getText().toLowerCase();

                                    String withText = replaceInputDialog.getWithTextField().getText();
                                    String newText = oldText.replaceAll(replaceText, withText);
                                    listViewItems.get(i).getTextArea().setText(newText);
                                }
                                new Alert(AlertType.INFORMATION, "Text Replaced Successfully !").show();
                            }
                        } else {
                            new Alert(AlertType.INFORMATION, "Replace text field cannot be empty !").show();
                        }
                    }
                });

                stage.show();
            }
        });

        menuItem_removeSpeaker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList speakers = FXCollections.observableArrayList(project.getSpeakerNamesArrayList());
                RemoveSpeakerAnchorPane removeSpeakerAnchorPane = new RemoveSpeakerAnchorPane(speakers);
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(Runner.primaryStage);
                removeSpeakerAnchorPane.getOkButton().setOnMousePressed(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        stage.close();
                    }
                });
                removeSpeakerAnchorPane.getDeleteButton().setOnMousePressed(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        String value = (String) removeSpeakerAnchorPane.getComboBox().getValue();
                        project.getSpeakerNamesArrayList().remove(value);
                        ObservableList<TextNode> items = textEditorAnchorPane.getListView().getItems();
                        for (int i = 0; i < items.size(); i++) {
                            ComboBox comboBox = items.get(i).getSpeakerNames_ComboBox();
                            if (comboBox.getValue() == (value)) {
                                comboBox.getSelectionModel().clearSelection();
                            }
                            comboBox.getItems().remove(value);
                        }
                        removeSpeakerAnchorPane.getComboBox().getItems().remove(value);
                    }
                });

                stage.setTitle("Delete Speaker");
                stage.setResizable(false);
                stage.setScene(new Scene(removeSpeakerAnchorPane, 320, 200));
                stage.show();
            }
        });
        menuItem_removeFontStyle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList fonts = textEditorAnchorPane.getFontStyle_ComboBox().getItems();
                RemoveFontAnchorPane removeFontsAnchorPane = new RemoveFontAnchorPane(fonts);
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(Runner.primaryStage);
                removeFontsAnchorPane.getOkButton().setOnMousePressed(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        stage.close();
                    }
                });
                removeFontsAnchorPane.getDeleteButton().setOnMousePressed(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        String valueToBeRemoved = (String) removeFontsAnchorPane.getComboBox().getValue();
                        fonts.remove(valueToBeRemoved);

//                        code to remove from fonts style text file;
                        String readTextFile = AllStaticMethods.readTextFile("text files\\Font Styles.txt");

                        String replace = readTextFile.replace(valueToBeRemoved + "\n", "");
                        AllStaticMethods.createFile("text files\\Font Styles.txt", replace);
                    }
                });

                stage.setTitle("Delete Font Style");
                stage.setResizable(false);
                stage.setScene(new Scene(removeFontsAnchorPane, 320, 200));
                stage.show();
            }
        });

        menuBar.getMenus().addAll(menu_File, menu_Edit, menu_Settings);
    }

    private void showSpeedPopup(final String message, final Node node) {
//        final Popup popup = createPopup(message);
        final Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);

        Label label = new Label(message);
        label.setMinSize(45, 25);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-background-color: white;-fx-padding: 0;"
                + "-fx-border-color: black;-fx-border-width: 1;-fx-font-size: 16;");
//        label.getStylesheets().add("/css/styles.css");
        label.getStyleClass().add("popup");
        popup.getContent().add(label);
        Bounds boundsInScreen = node.localToScreen(node.getBoundsInLocal());
        popup.show(node, boundsInScreen.getMinX(), (boundsInScreen.getMinY() - 30));

        PauseTransition wait = new PauseTransition(Duration.seconds(1));
        wait.setOnFinished((e) -> {
            popup.hide();
        });
        wait.play();
    }

    private String millisToHMSM_String(int milliSeconds__) {
        double currentTime = milliSeconds__;
        double currentHours = currentTime / 3600000;
        int currentHoursInt = (int) currentHours;
        double currentMins = ((currentHours - currentHoursInt) * 60);
        int currentMinsInt = (int) currentMins;
        double currentSec = ((currentMins - currentMinsInt) * 60);
        int currentSecInt = (int) currentSec;
        double currentMilli = ((currentSec - currentSecInt) * 1000);
        int currentMilliInt = (int) currentMilli;
        String timeText = String.format("%01d:%02d:%02d,%03d", (int) currentHoursInt, (int) currentMins, currentSecInt, currentMilliInt);
        return timeText;
    }

    private void prepareRangeSliderPane() {
        int currentTime = (int) videoControlsAnchorPane.getVideoSlider().getValue();
        int totalTime = (int) videoControlsAnchorPane.getVideoSlider().getMax();

        int lowerRange = currentTime - 10000;
        int upperRange = currentTime + 10000;
        if (lowerRange >= 0) {
            if (lowerRange < totalTime) {
                rangeSliderAnchorPane.getLowValueLabel().setText(millisToHMSM_String((int) lowerRange));
            } else {
                rangeSliderAnchorPane.getLowValueLabel().setText(millisToHMSM_String(totalTime));
            }
        } else {
            rangeSliderAnchorPane.getLowValueLabel().setText(millisToHMSM_String(0));
        }
        if (upperRange >= 0) {
            if (upperRange < totalTime) {
                rangeSliderAnchorPane.getHighValueLabel().setText(millisToHMSM_String((int) upperRange));
            } else {
                rangeSliderAnchorPane.getHighValueLabel().setText(millisToHMSM_String(totalTime));
            }
        } else {
            rangeSliderAnchorPane.getHighValueLabel().setText(millisToHMSM_String(0));
        }

        Subtitle subtitle = Subtitle.searchSubtitleByTime(currentTime, textEditorAnchorPane.getListView().getItems());
        if (subtitle != null) {
            //  5) change time on range slider time label
            rangeSliderAnchorPane.getTimeRangeLabel().setText(
                    currentSubtitle.getStartHours() + ":"
                    + currentSubtitle.getStartMin() + ":"
                    + currentSubtitle.getStartSec() + ","
                    + currentSubtitle.getStartMilliSec() + " --> "
                    + currentSubtitle.getStopHours() + ":"
                    + currentSubtitle.getStopMin() + ":"
                    + currentSubtitle.getStopSec() + ","
                    + currentSubtitle.getStopMilliSec());

            // 6) change low and high values of range slider
            int lowValue = currentSubtitle.getTotalStartMillis() - currentTime; // will return a negative value
            int highValue = currentSubtitle.getTotalStopMillis() - currentTime; // will return a positive value
            rangeSliderAnchorPane.getRangeSlider().setLowValue(lowValue);
            rangeSliderAnchorPane.getRangeSlider().setHighValue(highValue);

        } else {
//            double a = rangeSliderAnchorPane.getRangeSlider().getLowValue() + rangeSliderAnchorPane.getRangeSlider().getLowValue();
//            a = a / 2;
            double a = Double.MIN_VALUE;
            rangeSliderAnchorPane.getRangeSlider().setLowValue(a);
            rangeSliderAnchorPane.getRangeSlider().setHighValue(a);

        }
        // 7) change min and max values of range slider
//        double lowerRange = videoControlsAnchorPane.getVideoSlider().getValue() - 10000;
////                    System.out.println("Video Slider Value : " + (lowerRange + 10000));
////                    System.out.println("currentSubtitle.getTotalStartMillis(): " + currentSubtitle.getTotalStartMillis());
//        if (lowerRange >= 0) {
//            rangeSliderAnchorPane.getRangeSlider().setMin(lowerRange);
//        } else {
//            rangeSliderAnchorPane.getRangeSlider().setMin(lowerRange + 10000);
//        }
//        double totalDuration = videoAreaPane.getMediaPlayer().getTotalDuration().toMillis();
//        double upperRange = videoControlsAnchorPane.getVideoSlider().getValue() + 10000;
//        if (upperRange <= totalDuration) {
//            rangeSliderAnchorPane.getRangeSlider().setMax(upperRange);
//        } else {
//            rangeSliderAnchorPane.getRangeSlider().setMax(upperRange - 10000);
//        }
    }

    private class PlayPauseSubtitleEventHandler implements EventHandler<MouseEvent> {

        Subtitle subtitle;
        Button playPauseSubtitleToggleButton;

        public PlayPauseSubtitleEventHandler(Subtitle subtitle, Button playPauseSubtitleButton) {
            this.subtitle = subtitle;
            this.playPauseSubtitleToggleButton = playPauseSubtitleButton;
        }

        @Override
        public void handle(MouseEvent event) {
            MediaPlayer mediaPlayer = videoAreaPane.getMediaPlayer();
            videoControlsAnchorPane.getStopButton().setDisable(false);
            mediaPlayer.seek(Duration.millis(subtitle.getTotalStartMillis()));
            videoAreaPane.getMediaPlayer().play();
            ToggleButton playPauseButton = videoControlsAnchorPane.getPlayPauseButton();
            playPauseButton.setSelected(true);
            playPauseButton.setText("⏸");
            playPauseButton.setTooltip(new Tooltip("Pause"));

            timer = new Timer();
            timer.schedule(new TimerTask() { // tasks to do after subtitle is played

                @Override
                public void run() {
                    playPauseButton.setSelected(false);
                    Platform.runLater(new Runnable() { // runs code on javafx Application thread
                        @Override
                        public void run() {
                            playPauseButton.setText("▶");//▶
                            playPauseButton.setTooltip(new Tooltip("Play"));
                            playPauseSubtitleToggleButton.setText("▶");
                            playPauseSubtitleToggleButton.setTooltip(new Tooltip("Play this subtitle"));
                            mediaPlayer.pause();
//                            mediaPlayer.seek(Duration.millis(subtitle.getTotalStopMillis()));
                        }
                    });
                }
            }, (subtitle.getTotalStopMillis() - subtitle.getTotalStartMillis()));
        }
    }

    private class FindEventHandler implements EventHandler<ActionEvent> {

        private TextField textField;
        private boolean nextClicked;

        public FindEventHandler(TextField textField, boolean nextClicked) {
            this.textField = textField;
            this.nextClicked = nextClicked;
            this.textField.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
                @Override
                public void handle(javafx.scene.input.KeyEvent event) {
                    System.out.println("Key typed !");
                    edited = true;
                    indicesScanned = 0;
                }
            });
        }

        @Override
        public void handle(ActionEvent event) {
            String textToFind = textField.getText().toLowerCase();

            if (!textToFind.isEmpty()) {
                ListView listView = textEditorAnchorPane.getListView();
                int listViewSize = listView.getItems().size();
                for (int i = 0; i < listViewSize; i++) {
                    // clear previous selection
                    TextNode textNode = (TextNode) listView.getItems().get(i);
                    textNode.getTextArea().selectRange(0, 0);
                }
                if (edited) {
                    System.out.println("text to find Edited !");
                    occurenceIndices.clear();
                    for (int i = 0; i < listViewSize; i++) {

                        TextNode textNode = (TextNode) listView.getItems().get(i);

                        // go through all text nodes and find the occurences and save textNode no and index of occurences on array
                        String nodeText = textNode.getTextArea().getText().toLowerCase();
                        if (nodeText.contains(textToFind)) {
                            ArrayList allIndices = TextEditorAnchorPane.allIndicesOf(nodeText, textToFind);
                            for (int j = 0; j < allIndices.size(); j++) {
                                int index = (int) allIndices.get(j);
                                int[] a = {i, index};// {nodeNo , indexOfOccurance}
                                occurenceIndices.add(a);
                            }

                        }
                    }
                    edited = false;
                }
                System.out.println("occurenceIndices.size(): " + occurenceIndices.size());

                if (!occurenceIndices.isEmpty()) {
                    if (!nextClicked) { // if prev button is clicked
                        System.out.println("Prev.: " + indicesScanned);
                        if (indicesScanned > 0) {
                            indicesScanned -= 1;
                        } else {
                            indicesScanned = occurenceIndices.size() - 1;
                            Toolkit.getDefaultToolkit().beep();
                            showStatusOnStatusBar("Start reached while searching !", 3000);
                        }
                    }

                    int nodeNo = occurenceIndices.get(indicesScanned)[0];
                    int indexOfOccurance = occurenceIndices.get(indicesScanned)[1];
                    listView.scrollTo(nodeNo);
                    TextNode textNode = (TextNode) listView.getItems().get(nodeNo);
                    textNode.getTextArea().selectRange(indexOfOccurance, (indexOfOccurance + textToFind.length()));
                    if (nextClicked) {
                        System.out.println("Next: " + indicesScanned);
                        if (indicesScanned < occurenceIndices.size() - 1) {
                            indicesScanned += 1;
                        } else {
                            indicesScanned = 0;

                            Toolkit.getDefaultToolkit().beep();
                            showStatusOnStatusBar("End reached while searching !", 3000);

                        }
                    }
                } else {
                    new Alert(AlertType.ERROR, "\"" + textToFind + "\" not found !").show();
                }
            }

        }
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public ProjectsTreeView getProjectsTreeView() {
        return projectsTreeView;
    }

    private void showStatusOnStatusBar(String message, int durationInMillis) {

        statusBar.setText(message);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        statusBar.setText("Ok !");
                    }
                });
            }
        }, durationInMillis);
    }

    private ContextMenu init_treeviewContextMenu() {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem openProject = new MenuItem("Open Project");
        MenuItem deleteProject = new MenuItem("Delete Project");
        MenuItem renameProject = new MenuItem("Rename Project");
        MenuItem removeFromWS = new MenuItem("Remove Project from work space");

        contextMenu.getItems().addAll(openProject, deleteProject, renameProject, removeFromWS);
        openProject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProjectsTreeView.CustomTreeItem treeItem = (ProjectsTreeView.CustomTreeItem) projectsTreeView.getTreeView().getSelectionModel().getSelectedItem();
                if (treeItem != null) {
                    File file = treeItem.getFile();
                    String projectPath = file.getPath();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to open project '" + projectPath + "'.\nAny unsaved changes will be lost.\nAre you sure you want to continue?", ButtonType.OK, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.CANCEL) {
                        event.consume();
                    } else if (alert.getResult() == ButtonType.OK) {

                        if (!file.isDirectory()) {
                            projectPath = projectPath.substring(0, projectPath.lastIndexOf(File.separator));
                        }
                        String filePath = projectPath + "\\Project.ser";
                        Project project = Project.readProjectFile(filePath);
                        MainScreenBorderPane mainScreenBorderPane = new MainScreenBorderPane(project);
                        Scene scene = new Scene(mainScreenBorderPane, 1920, 990);
                        mainScreenBorderPane.getMenuBar().getMenus().get(2).getItems().add(Runner.darkThemeMenuItem(scene));
                        Runner.primaryStage.setResizable(true);
                        Runner.primaryStage.setTitle("Naqqaal 1.0 - " + projectPath.substring(projectPath.lastIndexOf(File.separator) + 1));
                        Runner.primaryStage.setScene(scene);
                        Runner.primaryStage.setMaximized(true);

                        OpenProjectEventHandler.resetRecentProjects(projectPath);
                    }
                } else {
                    event.consume();
                }
            }
        });
        deleteProject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ProjectsTreeView.CustomTreeItem treeItem = (ProjectsTreeView.CustomTreeItem) projectsTreeView.getTreeView().getSelectionModel().getSelectedItem();
                if (treeItem != null) {
                    File file = treeItem.getFile();
                    String projectPath = file.getPath();
                    if (file.getPath().equalsIgnoreCase(project.getProjectPath())) {
                        new Alert(AlertType.ERROR, "You must open another project before deleting this project !", ButtonType.OK).show();
                    } else {
                        Alert alert = new Alert(AlertType.CONFIRMATION, "You are about to permanently delete project '" + projectPath + "'.\nAre you sure you want to continue?", ButtonType.OK, ButtonType.CANCEL);
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.CANCEL) {
                            event.consume();
                        } else if (alert.getResult() == ButtonType.OK) {
                            String path = file.getPath();
                            deleteFolder(path);
                            String recent = AllStaticMethods.readTextFile("text files/recentProjects.txt");
                            System.out.println("recent: \n" + recent);
                            int indexOf = recent.indexOf("\n" + path);
                            String recent1 = recent.substring(0, indexOf);
                            String recent2 = recent.substring((indexOf + path.length() + 1));
                            String newRecent = recent1 + recent2;
                            AllStaticMethods.createFile("text files/recentProjects.txt", newRecent);
                            new Alert(AlertType.INFORMATION, "Project deleted successfully !", ButtonType.OK).show();

//                           CODE TO REFRESH TREE VIEW
                            refreshTreeView();

                        }
                    }
                } else {
                    event.consume();
                }
            }
        });
        renameProject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ProjectsTreeView.CustomTreeItem treeItem = (ProjectsTreeView.CustomTreeItem) projectsTreeView.getTreeView().getSelectionModel().getSelectedItem();

                if (treeItem != null) {
                    File file = treeItem.getFile();
                    TextInputDialog dialog = new TextInputDialog();

                    dialog.setTitle("Rename Project");
                    dialog.setHeaderText("New Name:");
//                    dialog.getEditor().setText(treeItem.getValue());
                    dialog.showAndWait();
                    System.out.println("* dialog.getResult(): " + dialog.getResult());
                    System.out.println("* new path: " + file.getParent() + "\\" + dialog.getResult());
                    String result = dialog.getResult();
                    if (result == null) {
                        event.consume();
                    } else {
                        String newPath = file.getParent() + "\\" + dialog.getResult();
                        File newFile = new File(newPath);
                        if (newFile.exists() && (!file.equals(newFile))) {
                            Alert alert = new Alert(AlertType.ERROR, "The project '" + newPath + "' already exists !", ButtonType.OK);
                            alert.showAndWait();
                        } else {
                            String readTextFile = AllStaticMethods.readTextFile("text files\\recentProjects.txt");
                            String replace = readTextFile.replace(file.getPath(), newFile.getPath());
                            AllStaticMethods.createFile("text files\\recentProjects.txt", replace);

                            Runner.primaryStage.setTitle("Naqqaal - " + result);
                            project.setProjectPath(newPath);
                            file.renameTo(newFile);
                            project.createProjectFile();

                            refreshTreeView();
                            showStatusOnStatusBar("Project renamed successfully !", 3000);
                        }

                    }
                } else {
                    event.consume();
                }
            }
        });
        removeFromWS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProjectsTreeView.CustomTreeItem treeItem = (ProjectsTreeView.CustomTreeItem) projectsTreeView.getTreeView().getSelectionModel().getSelectedItem();

                if (treeItem != null) {
                    File file = treeItem.getFile();
                    String filePath = file.getPath();
                    if (!file.isDirectory()) {
                        filePath = file.getParent();
                        treeItem = (ProjectsTreeView.CustomTreeItem) treeItem.getParent();
                    }
                    if (filePath.equals(project.getProjectPath())) {
                        new Alert(AlertType.ERROR, "You must open another project before removing this project from the workspace !", ButtonType.OK).show();
                    } else {
                        boolean remove = treeItem.getParent().getChildren().remove(treeItem);
                        if (remove) {

                            String readTextFile = AllStaticMethods.readTextFile("text files\\recentProjects.txt");
                            String replace = readTextFile.replace(filePath + "\n", "");
                            System.out.println("before: \n" + readTextFile);
                            System.out.println("after: \n" + replace);
                            AllStaticMethods.createFile("text files\\recentProjects.txt", replace);
                            showStatusOnStatusBar("Project removed from work space successfully !", 3000);
                        } else {
                            showStatusOnStatusBar("There was an error in removing project from work space !", 3000);
                        }
                    }
                } else {
                    event.consume();
                }
            }
        });
        return contextMenu;
    }

    /**
     * Deletes Non-empty folders.
     *
     * @param srcPath
     */
    public void deleteFolder(String srcPath) {
        File file = new File(srcPath);
//        boolean returnValue = false;
        if (file.isDirectory()) {
            String[] contents = file.list();
            if (contents.length == 0) {
                file.delete(); // this delete method of 'File' class only deletes a directory if it is empty
            } else {
                for (int i = 0; i < contents.length; i++) {
                    deleteFolder(srcPath + "\\" + contents[i]); // delete all child folders 
                    deleteFolder(srcPath);                      // then delete parent folder
                }
            }
        } else {
            file.delete();
        }
    }

    private void refreshTreeView() {
        //                           CODE TO REFRESH TREE VIEW
        mainSplitPane.getItems().remove(0);
        ProjectsTreeView projectsTreeView1 = new ProjectsTreeView();
        mainSplitPane.getItems().add(0, projectsTreeView1);
        projectsTreeView1.getTreeView().setContextMenu(init_treeviewContextMenu());
        mainSplitPane.setDividerPosition(0, 0.10);
    }
//    private class

}
