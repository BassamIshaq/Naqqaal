package Naqqaal;

import java.util.EventListener;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Lenovo 520
 */
public class RangeInputDialog extends AnchorPane {

    private Label titleLabel, errLabel, toLabel;
    private TextField lowerRangeTextField, upperRangeTextField;
    private Button okButton, cancelButton;
    private int lowerRange, upperRange;

    public RangeInputDialog() {
//        this.lowerRange = lowerRange;
//        this.upperRange = upperRange;

        titleLabel = new Label("Enter range to be marked as completed !");
        errLabel = new Label();
        toLabel = new Label(" to ");
        lowerRangeTextField = new TextField();
        upperRangeTextField = new TextField();
        okButton = new Button("Ok");
        cancelButton = new Button("Cancel");

        titleLabel.setStyle("-fx-underline: true;-fx-font-weight: bold;");
        AnchorPane.setTopAnchor(titleLabel, 20.0);
        AnchorPane.setLeftAnchor(titleLabel, 20.0);

//        lowerRangeTextField.setPromptText("");
        lowerRangeTextField.setPadding(new Insets(0));
        lowerRangeTextField.setPrefSize(100, 25);
        lowerRangeTextField.setStyle("-fx-border-color:gray; -fx-faint-focus-color: transparent;-fx-focus-color: transparent;");
        AnchorPane.setTopAnchor(lowerRangeTextField, 70.0);
        AnchorPane.setLeftAnchor(lowerRangeTextField, 30.0);
        lowerRangeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    lowerRangeTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        AnchorPane.setLeftAnchor(toLabel, 154.0);
        AnchorPane.setTopAnchor(toLabel, 70.0);

        upperRangeTextField.setPadding(new Insets(0));
        upperRangeTextField.setPrefSize(100, 25);
        upperRangeTextField.setStyle("-fx-border-color:gray; -fx-faint-focus-color: transparent;-fx-focus-color: transparent;");
        AnchorPane.setTopAnchor(upperRangeTextField, 70.0);
        AnchorPane.setRightAnchor(upperRangeTextField, 30.0);
        upperRangeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if ((!newValue.matches("\\d*")) || (newValue.equalsIgnoreCase("0"))) {
                    upperRangeTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        errLabel.setStyle("-fx-text-fill:red;-fx-alignment:center;");
        AnchorPane.setTopAnchor(errLabel, 95.0);
        AnchorPane.setLeftAnchor(errLabel, 20.0);

        okButton.setPrefHeight(25);
        okButton.setPadding(new Insets(0));
        AnchorPane.setBottomAnchor(okButton, 15.0);
        AnchorPane.setRightAnchor(okButton, 115.0);
        okButton.setStyle("-fx-border-color:gray;");
        okButton.setPadding(new Insets(0, 40, 0, 40));
//        okButton.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//            }
//        });

        cancelButton.setPrefHeight(25);
        cancelButton.setPadding(new Insets(0));
        AnchorPane.setBottomAnchor(cancelButton, 15.0);
        AnchorPane.setRightAnchor(cancelButton, 20.0);
        cancelButton.setStyle("-fx-border-color:gray;");
        cancelButton.setPadding(new Insets(0, 20, 0, 20));

        getChildren().addAll(titleLabel, lowerRangeTextField, upperRangeTextField, toLabel, errLabel, okButton, cancelButton);

    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public int getLowerRange() {
        return lowerRange;
    }

    public int getUpperRange() {
        return upperRange;
    }

    public Button getOkButton() {
        return okButton;
    }

    public Label getErrLabel() {
        return errLabel;
    }

    public TextField getLowerRangeTextField() {
        return lowerRangeTextField;
    }

    public TextField getUpperRangeTextField() {
        return upperRangeTextField;
    }

}
