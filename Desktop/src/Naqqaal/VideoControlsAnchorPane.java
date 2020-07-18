package Naqqaal;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Lenovo 520
 */
public final class VideoControlsAnchorPane extends AnchorPane {

    private Slider videoSlider, volumeSlider;
    private Label currentTimeLabel, totalTimeLabel;
    private Button stopButton, fastButton, slowButton, skipForwardButton, skipBackwardButton;//⇤ ⇥
    private ToggleButton playPauseButton;

    public VideoControlsAnchorPane() {
        videoSlider = new Slider();
        volumeSlider = new Slider();
        playPauseButton = new ToggleButton("▶");//▶⏸ ‎▶▶▷
        stopButton = new Button("◼");//⏹◼
        slowButton = new Button("⏪"); //⏪
        fastButton = new Button("⏩"); //⏩
        skipForwardButton = new Button();//⇥
        skipBackwardButton = new Button();//⇤
        currentTimeLabel = new Label("0:00:00");
        totalTimeLabel = new Label("/0:00:00");

        videoSlider.setMin(0);
        AnchorPane.setLeftAnchor(videoSlider, 5.0);
        AnchorPane.setRightAnchor(videoSlider, 5.0);
        AnchorPane.setTopAnchor(videoSlider, 10.0);

        volumeSlider.setMin(0.0);
        volumeSlider.setMax(100);
        volumeSlider.setBlockIncrement(10);
        volumeSlider.setPrefWidth(100);
        AnchorPane.setRightAnchor(volumeSlider, 5.0);
        AnchorPane.setTopAnchor(volumeSlider, 55.0);

        playPauseButton.setTooltip(new Tooltip("Play"));
        playPauseButton.setPadding(new Insets(1));
        AnchorPane.setTopAnchor(playPauseButton, 40.0);
        AnchorPane.setBottomAnchor(playPauseButton, 10.0);
        AnchorPane.setLeftAnchor(playPauseButton, 10.0);
        playPauseButton.setMinSize(30, 30);
        playPauseButton.setMaxSize(30, 30);

        stopButton.setTooltip(new Tooltip("Stop"));
        stopButton.setPadding(new Insets(0));
        AnchorPane.setTopAnchor(stopButton, 40.0);
        AnchorPane.setBottomAnchor(stopButton, 10.0);
        AnchorPane.setLeftAnchor(stopButton, 50.0);
        stopButton.setMinSize(30, 30);
        stopButton.setMaxSize(30, 30);
        stopButton.setDisable(true);

        slowButton.setTooltip(new Tooltip("Slow Playback Speed"));
        slowButton.setPadding(new Insets(0));
        AnchorPane.setTopAnchor(slowButton, 40.0);
        AnchorPane.setBottomAnchor(slowButton, 10.0);
        AnchorPane.setLeftAnchor(slowButton, 90.0);
        slowButton.setMinSize(30, 30);
        slowButton.setMaxSize(30, 30);

        fastButton.setTooltip(new Tooltip("Speed up Playback"));
        fastButton.setPadding(new Insets(0));
        AnchorPane.setTopAnchor(fastButton, 40.0);
        AnchorPane.setBottomAnchor(fastButton, 10.0);
        AnchorPane.setLeftAnchor(fastButton, 130.0);
        fastButton.setMinSize(30, 30);
        fastButton.setMaxSize(30, 30);

        skipBackwardButton.setTooltip(new Tooltip("Skip Backward 5 seconds"));
        skipBackwardButton.setPadding(new Insets(0));
        AnchorPane.setTopAnchor(skipBackwardButton, 40.0);
        AnchorPane.setBottomAnchor(skipBackwardButton, 10.0);
        AnchorPane.setLeftAnchor(skipBackwardButton, 170.0);
        skipBackwardButton.setMinSize(30, 30);
        skipBackwardButton.setMaxSize(30, 30);
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("images/Back.png")));
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        skipBackwardButton.setGraphic(imageView);

        skipForwardButton.setTooltip(new Tooltip("Skip forward 5 seconds"));
        skipForwardButton.setPadding(new Insets(0));
        AnchorPane.setTopAnchor(skipForwardButton, 40.0);
        AnchorPane.setBottomAnchor(skipForwardButton, 10.0);
        AnchorPane.setLeftAnchor(skipForwardButton, 210.0);
        skipForwardButton.setMinSize(30, 30);
        skipForwardButton.setMaxSize(30, 30);
        ImageView imageView1 = new ImageView(new Image(getClass().getResourceAsStream("images/Forward.png")));
        imageView1.setFitWidth(25);
        imageView1.setFitHeight(25);
        skipForwardButton.setGraphic(imageView1);

        currentTimeLabel.setTooltip(new Tooltip("Elapsed Time"));
        AnchorPane.setRightAnchor(currentTimeLabel, 95.0);
        AnchorPane.setTopAnchor(currentTimeLabel, 30.0);

        totalTimeLabel.setTooltip(new Tooltip("Total Time"));
        AnchorPane.setRightAnchor(totalTimeLabel, 10.0);
        AnchorPane.setTopAnchor(totalTimeLabel, 30.0);

        getChildren().addAll(videoSlider, volumeSlider, playPauseButton, stopButton,
                fastButton, slowButton, skipBackwardButton, skipForwardButton, currentTimeLabel, totalTimeLabel);

    }

    public Slider getVideoSlider() {
        return videoSlider;
    }

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public Label getCurrentTimeLabel() {
        return currentTimeLabel;
    }

    public Label getTotalTimeLabel() {
        return totalTimeLabel;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public Button getFastButton() {
        return fastButton;
    }

    public Button getSlowButton() {
        return slowButton;
    }

    public ToggleButton getPlayPauseButton() {
        return playPauseButton;
    }

    public Button getSkipForwardButton() {
        return skipForwardButton;
    }

    public Button getSkipBackwardButton() {
        return skipBackwardButton;
    }

}
