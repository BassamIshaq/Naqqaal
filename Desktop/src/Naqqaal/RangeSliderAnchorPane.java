package Naqqaal;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.RangeSlider;

/**
 *
 * @author Abdullah Shahid
 */
public class RangeSliderAnchorPane extends AnchorPane {

    private RangeSlider rangeSlider;
    private Label timeRangeLabel, lowValueLabel, highValueLabel;

    public RangeSliderAnchorPane() {
        rangeSlider = new RangeSlider();
        timeRangeLabel = new Label("0:00:00,000 --> 0:00:00,000");
        lowValueLabel = new Label("0:00:00,000");
        highValueLabel = new Label("0:00:00,000");

        timeRangeLabel.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(timeRangeLabel, 50.0);
        AnchorPane.setBottomAnchor(timeRangeLabel, 10.0);
        AnchorPane.setLeftAnchor(timeRangeLabel, 100.0);
        AnchorPane.setRightAnchor(timeRangeLabel, 100.0);

//        lowValueLabel.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setTopAnchor(lowValueLabel, 50.0);
        AnchorPane.setBottomAnchor(lowValueLabel, 10.0);
        AnchorPane.setLeftAnchor(lowValueLabel, 10.0);
//        AnchorPane.setRightAnchor(timeRangeLabel, 100.0);

        highValueLabel.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setTopAnchor(highValueLabel, 50.0);
        AnchorPane.setBottomAnchor(highValueLabel, 10.0);
//        AnchorPane.setLeftAnchor(highValueLabel, 100.0);
        AnchorPane.setRightAnchor(highValueLabel, 10.0);

        rangeSlider.setMin(-10000);
        rangeSlider.setMax(10000);
//        rangeSlider.setLowValue(-1);

        AnchorPane.setTopAnchor(rangeSlider, 10.0);

        AnchorPane.setLeftAnchor(rangeSlider, 0.0);
        AnchorPane.setRightAnchor(rangeSlider, 0.0);
//        rangeSlider.setShowTickLabels(true);
//        rangeSlider.setShowTickMarks(true);
//        rangeSlider.setMajorTickUnit(1);
//        rangeSlider.setMinorTickCount(4);
        rangeSlider.setBlockIncrement(1);

//        rangeSlider.lowValueProperty().addListener(new InvalidationListener() {
//            @Override
//            public void invalidated(Observable observable) {
////                double currentTime = videoAreaPane.getMediaPlayer().getCurrentTime().toMillis();
////                double lowVal = rangeSlider.getLowValue() * 1000;
////                System.out.println("currentTime: " + currentTime);
////                videoAreaPane.getMediaPlayer().seek(Duration.millis(currentTime + lowVal));
//
//            }
//        });
//        rangeSlider.highValueProperty().addListener(new InvalidationListener() {
//            @Override
//            public void invalidated(Observable observable) {
//                System.out.println("HIGH CHANGED");
//
//            }
//        });
        getChildren().addAll(rangeSlider, timeRangeLabel, lowValueLabel, highValueLabel);

    }

    public RangeSlider getRangeSlider() {
        return rangeSlider;
    }

    public Label getTimeRangeLabel() {
        return timeRangeLabel;
    }

    public Label getLowValueLabel() {
        return lowValueLabel;
    }

    public Label getHighValueLabel() {
        return highValueLabel;
    }

}
