package Naqqaal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Lenovo 520
 */
public class TextEditorAnchorPane extends AnchorPane {

    private ArrayList<Subtitle> subtitlesArrayList;
    private ComboBox fontStyle_ComboBox;
    private TextField fontSizeTextField;
    private ColorPicker colorPicker;
    private Button Bold_Button, Italic_Button, Underline_Button, AddTimeStamp_Button, newSubtitle_Button, markCompleteButton;
    private ListView<TextNode> listView;
    private FlowPane TextEditor_Buttons_FlowPane;
    private ArrayList<String> speakerNamesArrayList;

    public TextEditorAnchorPane(ArrayList<Subtitle> subtitlesArrayList_, ArrayList<String> speakerNamesArrayList_) {
        this.subtitlesArrayList = subtitlesArrayList_;
        this.speakerNamesArrayList = speakerNamesArrayList_;
        fontStyle_ComboBox = new ComboBox();
        fontSizeTextField = new TextField();
        colorPicker = new ColorPicker(Color.WHITE); // Parameter is the default color
        Bold_Button = new Button("B");
        Italic_Button = new Button("I");
        Underline_Button = new Button("U");//-fx-underline: true;
        AddTimeStamp_Button = new Button();
        newSubtitle_Button = new Button();
        markCompleteButton = new Button("🗸-🗸");

        listView = new ListView<>();
        TextEditor_Buttons_FlowPane = new FlowPane();

        final String readTextFile = AllStaticMethods.readTextFile("text files\\Font Styles.txt");
        String[] fonts = readTextFile.split("\n");
        System.out.println("All Fonts !\n" + readTextFile);
//        String fontStyle[] = {"Times New Roman", "Verdana", "Comic Sans MS", "WildWest", "Bedrock"};
        fontStyle_ComboBox.setItems(FXCollections.observableArrayList(fonts));
        fontStyle_ComboBox.setTooltip(new Tooltip("Font Style"));
        fontStyle_ComboBox.setPrefSize(119, 37);
        FlowPane.setMargin(fontStyle_ComboBox, new Insets(10, 5, 10, 5));
        fontStyle_ComboBox.setEditable(true);
        fontStyle_ComboBox.setPromptText("Font Style");
        fontStyle_ComboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.ENTER) {
                    TextArea textArea = listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId() - 1).getTextArea();
                    String originalText = textArea.getText();
                    int anchorPosition = textArea.getAnchor();
                    int caretPosition = textArea.getCaretPosition();
                    if (anchorPosition > caretPosition) {
                        int temp = anchorPosition;
                        anchorPosition = caretPosition;
                        caretPosition = temp;
                    }
                    //
                    if (fontStyle_ComboBox.getValue() != null) {
                        if (anchorPosition != caretPosition) {
                            String fontName = fontStyle_ComboBox.getValue().toString().trim();
                            if (!fontStyle_ComboBox.getItems().contains(String.valueOf(fontName)) && !fontName.isEmpty()) {

                                fontStyle_ComboBox.getItems().add(fontName);
                                String newFonts = readTextFile + fontName + "\n";
                                AllStaticMethods.createFile("text files\\Font Styles.txt", newFonts);
                                fontStyle_ComboBox.getSelectionModel().select(fontName);

                                String newText
                                        = addTags("<font face=\""
                                                + fontStyle_ComboBox.
                                                        getSelectionModel().
                                                        getSelectedItem().
                                                        toString() + "\">", "</font>",
                                                originalText,
                                                anchorPosition,
                                                caretPosition);
                                textArea.setText(newText);
                            }
                            listView.requestFocus();
                        }
                        fontStyle_ComboBox.getSelectionModel().clearSelection();

                    }
                }
            }
        });
        fontStyle_ComboBox.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                TextArea textArea = listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId() - 1).getTextArea();
                String originalText = textArea.getText();
                int anchorPosition = textArea.getAnchor();
                int caretPosition = textArea.getCaretPosition();
                if (anchorPosition > caretPosition) {
                    int temp = anchorPosition;
                    anchorPosition = caretPosition;
                    caretPosition = temp;
                }

                if (fontStyle_ComboBox.getSelectionModel().getSelectedItem() != null) {
                    if (anchorPosition != caretPosition) {
                        String newText
                                = addTags("<font face=\""
                                        + fontStyle_ComboBox.
                                                getSelectionModel().
                                                getSelectedItem().
                                                toString() + "\">", "</font>",
                                        originalText,
                                        anchorPosition,
                                        caretPosition);
                        textArea.setText(newText);
                    }
                }
                fontStyle_ComboBox.getSelectionModel().clearSelection();
            }
        });

        fontSizeTextField.setTooltip(new Tooltip("Font Size"));
        fontSizeTextField.setPromptText("Font Size");
        fontSizeTextField.setPrefSize(119, 37);
        FlowPane.setMargin(fontSizeTextField, new Insets(5, 5, 5, 5));
        fontSizeTextField.textProperty().addListener(new ChangeListener<String>() {
            // force the field to be numeric only
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    fontSizeTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        fontSizeTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().toString().equals("ENTER")) {
                    TextArea textArea = listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId() - 1).getTextArea();
                    String originalText = textArea.getText();
                    int anchorPosition = textArea.getAnchor();
                    int caretPosition = textArea.getCaretPosition();
                    if (anchorPosition > caretPosition) {
                        int temp = anchorPosition;
                        anchorPosition = caretPosition;
                        caretPosition = temp;
                    }

                    String newText = addTags("<font size=\"" + fontSizeTextField.getText() + "\">", "</font>", originalText, anchorPosition, caretPosition);
                    textArea.setText(newText);

                    fontSizeTextField.setText("");
                    textArea.requestFocus();
                }
            }
        });

        colorPicker.setTooltip(new Tooltip("Color"));
        colorPicker.setPrefSize(50, 37);
        FlowPane.setMargin(colorPicker, new Insets(5, 5, 5, 5));
        colorPicker.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                TextArea textArea = listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId() - 1).getTextArea();
                String originalText = textArea.getText();
                int anchorPosition = textArea.getAnchor();
                int caretPosition = textArea.getCaretPosition();
                if (anchorPosition > caretPosition) {
                    int temp = anchorPosition;
                    anchorPosition = caretPosition;
                    caretPosition = temp;
                }
                Color color = colorPicker.getValue();
                String hexColor = String.format("#%02X%02X%02X",
                        (int) (color.getRed() * 255),
                        (int) (color.getGreen() * 255),
                        (int) (color.getBlue() * 255));

                if (anchorPosition != caretPosition) {
                    String newText = addTags("<font color=\"" + hexColor + "\">", "</font>", originalText, anchorPosition, caretPosition);
                    textArea.setText(newText);
                }

                textArea.requestFocus();
                colorPicker.setValue(Color.WHITE);
            }
        });

        Bold_Button.setTooltip(new Tooltip("Bold"));
        Bold_Button.setPadding(new Insets(0));
        Bold_Button.setStyle("-fx-font-weight: bold;-fx-font-size: 13pt;");
        Bold_Button.setPrefSize(37, 37);
        FlowPane.setMargin(Bold_Button, new Insets(5, 5, 5, 5));
        Bold_Button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextArea textArea = listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId() - 1).getTextArea();
                String originalText = textArea.getText();
                int anchorPosition = textArea.getAnchor();
                int caretPosition = textArea.getCaretPosition();
                if (anchorPosition > caretPosition) {
                    int temp = anchorPosition;
                    anchorPosition = caretPosition;
                    caretPosition = temp;
                }

                if (anchorPosition != caretPosition) {
                    String newText = addTags("<b>", "</b>", originalText, anchorPosition, caretPosition);
                    textArea.setText(newText);
                }

            }
        });

        Italic_Button.setTooltip(new Tooltip("Italic"));
        Italic_Button.setStyle("-fx-font-style: italic;-fx-font-family: \"Courier New\";-fx-font-size: 13pt;");
        Italic_Button.setPrefSize(37, 37);
        Italic_Button.setMaxSize(37, 37);
        FlowPane.setMargin(Italic_Button, new Insets(5, 5, 5, 5));
        Italic_Button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextArea textArea = listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId() - 1).getTextArea();
                String originalText = textArea.getText();
                int anchorPosition = textArea.getAnchor();
                int caretPosition = textArea.getCaretPosition();
                if (anchorPosition > caretPosition) {
                    int temp = anchorPosition;
                    anchorPosition = caretPosition;
                    caretPosition = temp;
                }
                if (anchorPosition != caretPosition) {
                    String newText = addTags("<i>", "</i>", originalText, anchorPosition, caretPosition);
                    textArea.setText(newText);
                }
            }
        });

        Underline_Button.setTooltip(new Tooltip("Underline"));
        Underline_Button.setStyle("-fx-underline: true;-fx-font-size: 13pt;-fx-text-fill: black;");
        Underline_Button.setPrefSize(37, 37);
        FlowPane.setMargin(Underline_Button, new Insets(5, 5, 5, 5));
        Underline_Button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextArea textArea = listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId() - 1).getTextArea();
                String originalText = textArea.getText();
                int anchorPosition = textArea.getAnchor();
                int caretPosition = textArea.getCaretPosition();
                if (anchorPosition > caretPosition) {
                    int temp = anchorPosition;
                    anchorPosition = caretPosition;
                    caretPosition = temp;
                }
                if (anchorPosition != caretPosition) {
                    String newText = addTags("<u>", "</u>", originalText, anchorPosition, caretPosition);
                    textArea.setText(newText);
                }

            }
        });

        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("images/stopwatch.png")));
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        AddTimeStamp_Button.setMaxSize(37, 37);
        AddTimeStamp_Button.setGraphic(imageView);
        FlowPane.setMargin(AddTimeStamp_Button, new Insets(5, 5, 5, 5));
        AddTimeStamp_Button.setTooltip(new Tooltip("Add Time Stamp.\n"
                + "(Gets time values from range slider and sets \n"
                + "start and end time of subtitle being editted.)"));

        newSubtitle_Button.setPrefSize(37, 37);
        ImageView imageView2 = new ImageView(new Image(getClass().getResourceAsStream("images/returnArrow.png")));
        imageView2.setFitWidth(25);
        imageView2.setFitHeight(25);
        newSubtitle_Button.setGraphic(imageView2);
        FlowPane.setMargin(newSubtitle_Button, new Insets(5, 5, 5, 5));
        newSubtitle_Button.setTooltip(new Tooltip("New Subtitle. (CTRL+N)\n"
                + "(Splits text from cursor position \n"
                + "and generates new subtitle)"));
        newSubtitle_Button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextArea textArea = listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId() - 1).getTextArea();
                String originalText = textArea.getText();
                int caretPosition = textArea.getCaretPosition();
                String newText = originalText.substring(caretPosition);
                textArea.setText(originalText.substring(0, caretPosition));
//                int id = Integer.parseInt(listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId() - 1).getLabel_SubtitleID().getText());
//                id += 1;
                int currentSubtitleID = MainScreenBorderPane.currentSubtitle.getId();
                Subtitle subtitle = new Subtitle(currentSubtitleID + 1, 0, 0, 0, 0, 0, 0, 0, 0, newText, "", false, "");
//                subtitlesArrayList.add(currentSubtitleID, subtitle);
                TextNode newTextNode = new TextNode(subtitle, speakerNamesArrayList);
                newTextNode.getDeleteSubtitleButton().setOnMousePressed(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        ObservableList<TextNode> items = getListView().getItems();
                        if (items.size() > 1) {
//                            MainScreenBorderPane.currentSubtitle = items.get(newTextNode.getSubtitle().getId() - 2).getSubtitle();
                            items.remove(newTextNode);
                            for (int i = 0; i < items.size(); i++) {
                                // can be done in parallel to improve performance
                                items.get(i).getLabel_SubtitleID().setText((i + 1) + "");
                            }
                        } else {
                            items.get(0).clear();
                            MainScreenBorderPane.currentSubtitle = items.get(0).getSubtitle();
                        }
                    }
                });
                listView.getItems().add(currentSubtitleID, newTextNode);

                MainScreenBorderPane.currentSubtitle = listView.getItems().get(currentSubtitleID).getSubtitle();

                for (int i = 0; i < listView.getItems().size(); i++) {
//                    int id_ = subtitlesArrayList.get(i).getId();
//                    id_ += 1;
//                    subtitlesArrayList.get(i).setId(id_);
                    listView.getItems().get(i).getLabel_SubtitleID().setText((i + 1) + "");

                }
            }
        });

        markCompleteButton.setTooltip(new Tooltip("Mark range as complete !"));
        markCompleteButton.setStyle("-fx-font-size: 13pt;-fx-text-fill: black;");
        markCompleteButton.setPrefSize(37, 37);
        markCompleteButton.setPadding(new Insets(0));
        FlowPane.setMargin(markCompleteButton, new Insets(5, 5, 5, 5));

        markCompleteButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RangeInputDialog rangeInputDialog = new RangeInputDialog();
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(Runner.primaryStage);
                rangeInputDialog.getCancelButton().setOnMousePressed(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        stage.close();
                    }
                });
                rangeInputDialog.getOkButton().setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        String lower = rangeInputDialog.getLowerRangeTextField().getText().trim();
                        String upper = rangeInputDialog.getUpperRangeTextField().getText().trim();
                        if (!lower.isEmpty() && !upper.isEmpty()) {
                            int lowerRange_ = Integer.parseInt(lower);
                            int upperRange_ = Integer.parseInt(upper);

                            if (upperRange_ >= lowerRange_) {
                                int lowerRange = lowerRange_;
                                int upperRange = upperRange_;

                                if (upperRange <= listView.getItems().size() && upperRange > 0) {
                                    if (lowerRange > 0 && lowerRange <= listView.getItems().size()) {
                                        for (int i = lowerRange; i <= upperRange; i++) {
                                            listView.getItems().get(i - 1).getCompletedCheckBox().setSelected(true);
                                        }
                                        stage.close();
                                    } else {
                                        rangeInputDialog.getErrLabel().setText("Lower range must be greater than 0 and \nless than total number of text nodes!");
                                    }
                                } else {
                                    rangeInputDialog.getErrLabel().setText("Upper range must be greater than 0 and \nless than total number of text nodes !");
                                }
                            } else {
                                rangeInputDialog.getErrLabel().setText("Upper Range cannot be less than lower range !");
                            }
                        } else {
                            rangeInputDialog.getErrLabel().setText("Non of the fields can be empty !");
                        }
//                        Timer timer = new Timer();
//                        timer.schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                Platform.runLater(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        rangeInputDialog.getErrLabel().setText("");
//                                    }
//                                });
//                            }
//                        }, 10000);
                    }

                });

                stage.setTitle("Range");
                stage.setResizable(false);
                stage.setScene(new Scene(rangeInputDialog, 320, 200));
                stage.show();
                // Hide this current window (if this is what you want)
//                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });

        AnchorPane.setBottomAnchor(listView, 0.0);
        AnchorPane.setLeftAnchor(listView, 0.0);
        AnchorPane.setRightAnchor(listView, 0.0);
        AnchorPane.setBottomAnchor(listView, 0.0);
        ObservableList<TextNode> items = listView.getItems();
        for (int i = 0; i < this.subtitlesArrayList.size(); i++) {
            TextNode textNode = new TextNode(this.subtitlesArrayList.get(i), this.speakerNamesArrayList);
            textNode.getDeleteSubtitleButton().setOnMousePressed(new EventHandler() {
                @Override
                public void handle(Event event) {

                    if (items.size() > 1) {
                        items.remove(textNode);
                        for (int i = 0; i < items.size(); i++) {
                            // can be done in parallel to improve performance
                            items.get(i).getLabel_SubtitleID().setText((i + 1) + "");
                        }
                    } else {
                        items.get(0).clear();
                        MainScreenBorderPane.currentSubtitle = items.get(0).getSubtitle();
                    }
                }
            });
            listView.getItems().add(textNode);

            ComboBox comboBox = listView.getItems().get(i).getSpeakerNames_ComboBox();
            comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {

                    if (event.getCode() == KeyCode.ENTER) {
                        if (comboBox.getValue() != null) {
                            String speakerName = comboBox.getValue().toString().trim();
                            if (!comboBox.getItems().contains(String.valueOf(speakerName)) && !speakerName.isEmpty()) {
                                speakerNamesArrayList.add(speakerName);
                                comboBox.setItems(FXCollections.observableArrayList(speakerNamesArrayList));
                            }
                            for (int j = 0; j < listView.getItems().size(); j++) {
                                listView.getItems().get(j).getSpeakerNames_ComboBox().setItems(FXCollections.observableArrayList(speakerNamesArrayList));
                            }
                            comboBox.getSelectionModel().select(speakerName);
                            listView.requestFocus();
                        }
                    }
                }
            });

            listView.getItems().get(i).getTextfieldStartSec().setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        Collections.sort(listView.getItems());//sort wrt totalStartMillis - code in TextNode class
                        for (int i = 0; i < listView.getItems().size(); i++) {
                            // loop for reseting ids 
                            int index = i + 1;
                            listView.getItems().get(i).getSubtitle().setId(index);
                            listView.getItems().get(i).getLabel_SubtitleID().setText(index + "");
                        }
//                        MainScreenBorderPane.currentSubtitle = 
                        listView.scrollTo((MainScreenBorderPane.currentSubtitle.getId() - 1));
                        listView.getSelectionModel().select((MainScreenBorderPane.currentSubtitle.getId() - 1));
                        listView.requestFocus();
                    }
                }
            });
            listView.getItems().get(i).getTextfieldStartMilli().setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        Collections.sort(listView.getItems());//sort wrt totalStartMillis - code in TextNode class
                        for (int i = 0; i < listView.getItems().size(); i++) {
                            // loop for reseting ids 
                            int index = i + 1;
                            listView.getItems().get(i).getSubtitle().setId(index);
                            listView.getItems().get(i).getLabel_SubtitleID().setText(index + "");
                        }
//                        MainScreenBorderPane.currentSubtitle = 
                        listView.scrollTo((MainScreenBorderPane.currentSubtitle.getId() - 1));
                        listView.getSelectionModel().select((MainScreenBorderPane.currentSubtitle.getId() - 1));
                        listView.requestFocus();
                    }
                }
            });
            listView.getItems().get(i).getTextfieldStartSec().focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                    if (newPropertyValue) {
                        System.out.println("Textfield on focus");
                    } else {
                        System.out.println("Textfield out focus");

                        Collections.sort(listView.getItems());//sort wrt totalStartMillis - code in TextNode class
                        for (int i = 0; i < listView.getItems().size(); i++) {
                            // loop for reseting ids 
                            int index = i + 1;
                            listView.getItems().get(i).getSubtitle().setId(index);
                            listView.getItems().get(i).getLabel_SubtitleID().setText(index + "");
                        }
                        listView.requestFocus();
                    }
                }
            });
            listView.getItems().get(i).getTextfieldStartMilli().focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                    if (newPropertyValue) {
                        System.out.println("Textfield on focus");
                    } else {
                        System.out.println("Textfield out focus");

                        Collections.sort(listView.getItems());//sort wrt totalStartMillis - code in TextNode class
                        for (int i = 0; i < listView.getItems().size(); i++) {
                            // loop for reseting ids 
                            int index = i + 1;
                            listView.getItems().get(i).getSubtitle().setId(index);
                            listView.getItems().get(i).getLabel_SubtitleID().setText(index + "");
                        }
                        listView.requestFocus();
                    }
                }
            });
        }

        AnchorPane.setLeftAnchor(TextEditor_Buttons_FlowPane, 0.0);
        AnchorPane.setRightAnchor(TextEditor_Buttons_FlowPane, 0.0);
        AnchorPane.setTopAnchor(TextEditor_Buttons_FlowPane, 0.0);
        TextEditor_Buttons_FlowPane.setMaxHeight(100);
        TextEditor_Buttons_FlowPane.setPrefHeight(42);
        TextEditor_Buttons_FlowPane.setMinWidth(310);
        TextEditor_Buttons_FlowPane.getChildren().addAll(fontStyle_ComboBox, fontSizeTextField, colorPicker, Bold_Button,
                Italic_Button, Underline_Button, AddTimeStamp_Button, newSubtitle_Button, markCompleteButton);
        TextEditor_Buttons_FlowPane.heightProperty().addListener(new ChangeListener<Number>() {
            // CODE TO CHANGE LISTVIEW TOP ANCHOR WITH THE CHANGE IN HEIGHT OF TextEditor_Buttons_FlowPane
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                AnchorPane.setTopAnchor(listView, TextEditor_Buttons_FlowPane.getHeight());
            }
        });

        getChildren().addAll(TextEditor_Buttons_FlowPane, listView);
    }

    private String addTags(String startTag, String endTag, String originalText, int anchorPosition, int caretPosition) {

        // searching all indexes of start and end tags
        ArrayList startTagIndexes = allIndicesOf(originalText, startTag);
        ArrayList endTagIndexes = allIndicesOf(originalText, endTag);

        String returnString = "";
        if (caretPosition != anchorPosition) {

            if (startTagIndexes.isEmpty()) { // if no <b> tags are found
                String newText = insertString(originalText, startTag, anchorPosition);
                newText = insertString(newText, endTag, caretPosition + startTag.length());
                returnString = newText;
//                listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId()).getTextArea().setText(newText);
            } else {
                for (int i = 0; i < startTagIndexes.size(); i++) {
                    if (anchorPosition < Integer.parseInt(startTagIndexes.get(i).toString())
                            && anchorPosition < Integer.parseInt(endTagIndexes.get(i).toString())
                            && caretPosition < Integer.parseInt(startTagIndexes.get(i).toString())
                            && caretPosition < Integer.parseInt(endTagIndexes.get(i).toString())) {
                        // selection region lies before start and end tab
                        String newText = insertString(originalText, startTag, anchorPosition);
                        newText = insertString(newText, endTag, caretPosition + startTag.length());
                        returnString = newText;
//                        listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId()).getTextArea().setText(newText);

                        break;
                    } else if (anchorPosition > Integer.parseInt(startTagIndexes.get(i).toString())
                            && anchorPosition < Integer.parseInt(endTagIndexes.get(i).toString())
                            && caretPosition < Integer.parseInt(endTagIndexes.get(i).toString())) {
                        // selection region lies between start and end tab
                        String newText = insertString(originalText, endTag, anchorPosition);
                        newText = insertString(newText, startTag, caretPosition + endTag.length());
                        returnString = newText;
//                        listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId()).getTextArea().setText(newText);

                        break;
                    } else if (anchorPosition < Integer.parseInt(startTagIndexes.get(i).toString())
                            && anchorPosition < Integer.parseInt(endTagIndexes.get(i).toString())
                            && caretPosition > Integer.parseInt(startTagIndexes.get(i).toString())
                            && caretPosition < Integer.parseInt(endTagIndexes.get(i).toString())) {
                        // Start tag lies between anchor and caret
                        originalText = replaceSubStringInRange(originalText, startTag, "", anchorPosition, caretPosition);
                        String newText = insertString(originalText, startTag, anchorPosition);
                        returnString = newText;
//                        listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId()).getTextArea().setText(newText);

                        break;
                    } else if (anchorPosition > Integer.parseInt(startTagIndexes.get(i).toString())
                            && anchorPosition < Integer.parseInt(endTagIndexes.get(i).toString())
                            && caretPosition > Integer.parseInt(startTagIndexes.get(i).toString())
                            && caretPosition > Integer.parseInt(endTagIndexes.get(i).toString())) {
                        // End tag lies between anchor and caret
                        String newText = insertString(originalText, endTag, caretPosition);
                        originalText = replaceSubStringInRange(newText, endTag, "", anchorPosition, caretPosition);
                        returnString = originalText;
//                        listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId()).getTextArea().setText(originalText);
                        break;
                    } else if (anchorPosition > Integer.parseInt(startTagIndexes.get(i).toString())
                            && anchorPosition > Integer.parseInt(endTagIndexes.get(i).toString())
                            && caretPosition > Integer.parseInt(startTagIndexes.get(i).toString())
                            && caretPosition > Integer.parseInt(endTagIndexes.get(i).toString())) {
                        // anchor and caret lies after end tag
                        String newText = insertString(originalText, startTag, anchorPosition);
                        newText = insertString(newText, endTag, caretPosition + startTag.length());
                        returnString = newText;
//                        listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId()).getTextArea().setText(newText);

                        break;
                    } else if (anchorPosition < Integer.parseInt(startTagIndexes.get(i).toString())
                            && anchorPosition < Integer.parseInt(endTagIndexes.get(i).toString())
                            && caretPosition > Integer.parseInt(startTagIndexes.get(i).toString())
                            && caretPosition > Integer.parseInt(endTagIndexes.get(i).toString())) {
                        // start and end tags lies between anchor and caret

                        String newText1 = originalText.substring(0, anchorPosition);
                        String newText2 = originalText.substring(anchorPosition, caretPosition);
                        String newText3 = originalText.substring(caretPosition);
                        newText2 = newText2.replace(startTag, "");
                        newText2 = newText2.replace(endTag, "");
                        originalText = newText1 + startTag + newText2 + endTag + newText3;
                        returnString = originalText;
//                        listView.getItems().get(MainScreenBorderPane.currentSubtitle.getId()).getTextArea().setText(originalText);
                        break;
                    }
                }
            }
        }
        return returnString;
    }

    private String insertString(String originalString, String stringToBeInserted, int insertionIndex) {
        return (originalString.substring(0, insertionIndex)
                + stringToBeInserted + originalString.substring(insertionIndex, originalString.length()));
    }

    public static ArrayList allIndicesOf(String input, String regex) {
        ArrayList indexes = new ArrayList();
        int regexLength = regex.length();
        for (int i = 0; i < (input.length() - regexLength + 1); i++) {

            if (input.substring(i, i + regexLength).equals(regex)) {
                indexes.add(i);
                i += regexLength;
            }
        }

        return indexes;
    }

    /**
     * *
     * replaces a substring in a String with a new string within specific range
     * of original string
     *
     */
    private static String replaceSubStringInRange(String originalString, String oldSubString, String newSubString,
            int startPosition, int endPosition) {
        String returnString1 = originalString.substring(0, startPosition);
        String returnString2 = originalString.substring(startPosition, endPosition);
        String returnString3 = originalString.substring(endPosition, originalString.length());
        returnString2 = returnString2.replace(oldSubString, newSubString);
        return returnString1 + returnString2 + returnString3;
    }

//    public ArrayList<Subtitle> getSubtitlesArrayList() {
//        return subtitlesArrayList;
//    }
    public ComboBox getFontStyle_ComboBox() {
        return fontStyle_ComboBox;
    }

    public TextField getFontSizeTextField() {
        return fontSizeTextField;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public Button getBold_Button() {
        return Bold_Button;
    }

    public Button getItalic_Button() {
        return Italic_Button;
    }

    public Button getUnderline_Button() {
        return Underline_Button;
    }

    public Button getAddTimeStamp_Button() {
        return AddTimeStamp_Button;
    }

    public Button getNewSubtitle_Button() {
        return newSubtitle_Button;
    }

    public ListView<TextNode> getListView() {
        return listView;
    }

    public FlowPane getTextEditor_Buttons_FlowPane() {
        return TextEditor_Buttons_FlowPane;
    }

}
