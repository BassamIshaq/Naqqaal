package Naqqaal;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Lenovo 520
 */
public class RemoveSpeakerAnchorPane extends AnchorPane {

    private Label label;
    private Button okButton, deleteButton;
    private ComboBox comboBox;

    public RemoveSpeakerAnchorPane(ObservableList items) {
        label = new Label("Select speaker to remove:");
        okButton = new Button("Ok");
        deleteButton = new Button("Delete");
        comboBox = new ComboBox(items);

        label.setStyle("-fx-underline: true;-fx-font-weight: bold;");
        AnchorPane.setTopAnchor(label, 20.0);
        AnchorPane.setLeftAnchor(label, 20.0);

        AnchorPane.setTopAnchor(comboBox, 70.0);
        AnchorPane.setLeftAnchor(comboBox, 30.0);

        deleteButton.setPrefSize(100, 20);
        AnchorPane.setBottomAnchor(deleteButton, 15.0);
        AnchorPane.setRightAnchor(deleteButton, 125.0);

        okButton.setPrefSize(100, 20);
        AnchorPane.setBottomAnchor(okButton, 15.0);
        AnchorPane.setRightAnchor(okButton, 20.0);

        getChildren().addAll(label, okButton, deleteButton, comboBox);
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public ComboBox getComboBox() {
        return comboBox;
    }

}
