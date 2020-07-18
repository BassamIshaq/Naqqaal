package Naqqaal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Lenovo 520
 */
public class ReplaceInputDialog extends AnchorPane {

    private Label replaceLabel, withLabel;
    private TextField replaceTextField, withTextField;
    private Button replaceButton, closeButton, replaceAllButton, findNextButton;
//    private CheckBox checkBoxMatchCase;

    public ReplaceInputDialog() {

        replaceLabel = new Label("Replace: ");
        withLabel = new Label("With: ");
        replaceTextField = new TextField();
        withTextField = new TextField();
        replaceButton = new Button("Replace"); // 
        replaceAllButton = new Button("Replace All");
        findNextButton = new Button("Find Next");
        closeButton = new Button("Close");
//        checkBoxMatchCase = new CheckBox("Match Case");

//        titleLabel.setStyle("-fx-underline: true;-fx-font-weight: bold;");
        AnchorPane.setTopAnchor(replaceLabel, 20.0);
        AnchorPane.setLeftAnchor(replaceLabel, 20.0);

//        replaceTextField.setPromptText("");
        replaceTextField.setPadding(new Insets(0));
        replaceTextField.setPrefSize(170, 25);
        replaceTextField.setStyle("-fx-border-color:gray; -fx-faint-focus-color: transparent;-fx-focus-color: transparent;");
        AnchorPane.setTopAnchor(replaceTextField, 20.0);
        AnchorPane.setRightAnchor(replaceTextField, 20.0);

        AnchorPane.setLeftAnchor(withLabel, 20.0);
        AnchorPane.setTopAnchor(withLabel, 50.0);

        withTextField.setPadding(new Insets(0));
        withTextField.setPrefSize(170, 25);
        withTextField.setStyle("-fx-border-color:gray; -fx-faint-focus-color: transparent;-fx-focus-color: transparent;");
        AnchorPane.setTopAnchor(withTextField, 50.0);
        AnchorPane.setRightAnchor(withTextField, 20.0);

//        AnchorPane.setRightAnchor(checkBoxMatchCase, 20.0);
//        AnchorPane.setTopAnchor(checkBoxMatchCase, 85.0);

        replaceButton.setPrefSize(130, 25);
        AnchorPane.setTopAnchor(replaceButton, 115.0);
        AnchorPane.setLeftAnchor(replaceButton, 20.0);
        replaceButton.setStyle("-fx-border-color:gray;");
        replaceButton.setPadding(new Insets(0));

        replaceAllButton.setPrefSize(130, 25);
        replaceAllButton.setPadding(new Insets(0));
        AnchorPane.setTopAnchor(replaceAllButton, 115.0);
        AnchorPane.setRightAnchor(replaceAllButton, 20.0);
        replaceAllButton.setStyle("-fx-border-color:gray;");
        replaceAllButton.setPadding(new Insets(0));

        findNextButton.setPrefSize(130, 25);
        AnchorPane.setTopAnchor(findNextButton, 155.0);
        AnchorPane.setLeftAnchor(findNextButton, 20.0);
        findNextButton.setStyle("-fx-border-color:gray;");
        findNextButton.setPadding(new Insets(0));

        closeButton.setPrefSize(130, 25);
        AnchorPane.setTopAnchor(closeButton, 155.0);
        AnchorPane.setRightAnchor(closeButton, 20.0);
        closeButton.setStyle("-fx-border-color:gray;");
        closeButton.setPadding(new Insets(0));

        getChildren().addAll(replaceLabel, replaceTextField, withLabel, withTextField,  replaceButton, replaceAllButton, findNextButton, closeButton); //, withTextField, withLabel, replaceButton, closeButton

    }

    public Button getCloseButton() {
        return closeButton;
    }

    public Button getReplaceButton() {
        return replaceButton;
    }

//    public Label getErrLabel() {
//        return errLabel;
//    }
    public TextField getReplaceTextField() {
        return replaceTextField;
    }

    public TextField getWithTextField() {
        return withTextField;
    }

    public Button getReplaceAllButton() {
        return replaceAllButton;
    }

    public Button getFindNextButton() {
        return findNextButton;
    }

//    public CheckBox getCheckBoxMatchCase() {
//        return checkBoxMatchCase;
//    }

}
