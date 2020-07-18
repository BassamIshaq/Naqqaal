package Naqqaal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

/**
 *
 * @author Lenovo 520
 */
public final class VideoAreaPane extends Pane {

    private MediaPlayer mediaPlayer;
    private String mediaPath;
    private MediaView mediaView;
    private Media media;

    public VideoAreaPane(String mediaPath) {
        setBorder(new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.mediaPath = mediaPath;

        mediaView = new MediaView();
        media = new Media(("file:/" + this.mediaPath.replace("\\", "/")).replaceAll(" ", "%20"));
        mediaPlayer = new MediaPlayer(media);
        BorderPane.setAlignment(mediaView, Pos.CENTER);
        mediaView.setMediaPlayer(mediaPlayer);
        ChangeListener changeListener = new ChangeListener() {
            // listener for detecting change in size of "Pane videoPane"
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (Double.parseDouble(oldValue.toString()) == 0.0) {// initialy when no change detected in size of videoPane
                    mediaView.setFitWidth(getWidth());
                    mediaView.setFitHeight(getHeight());
                    mediaView.setTranslateY(Double.parseDouble(newValue.toString()) / 3.46);
                } else { // when change in size detected
                    mediaView.setFitWidth(getWidth());
                    mediaView.setFitHeight(getHeight());
                    // After setting a big fit width and height, the layout bounds match the video size. Not sure why and this feels fragile.
                    Bounds actualVideoSize = mediaView.getLayoutBounds();
                    mediaView.setTranslateX((mediaView.getFitWidth() - actualVideoSize.getWidth()) / 2);
                    mediaView.setTranslateY((mediaView.getFitHeight() - actualVideoSize.getHeight()) / 2);
                }
            }
        };

        heightProperty().addListener(changeListener);
        widthProperty().addListener(changeListener);

        getChildren().add(mediaView);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public MediaView getMediaView() {
        return mediaView;
    }

    public Media getMedia() {
        return media;
    }

}
