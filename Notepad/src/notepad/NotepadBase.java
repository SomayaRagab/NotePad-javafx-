package notepad;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

public class NotepadBase extends BorderPane {

    protected final MenuBar menuBar;
    protected final Menu file;
    protected final MenuItem New;
    protected final MenuItem open;
    protected final MenuItem save;
    protected final MenuItem exit;

    protected final Menu edit;
    protected final MenuItem cut;
    protected final MenuItem copy;
    protected final MenuItem paste;
    protected final MenuItem delete;
    protected final MenuItem select;

    protected final Menu help;
    protected final MenuItem about;
    protected final TextArea textArea;

    protected String textAfterSaving = "";
    protected boolean saved = false;

    public NotepadBase() {

        menuBar = new MenuBar();
        file = new Menu();
        New = new MenuItem();
        open = new MenuItem();
        save = new MenuItem();
        exit = new MenuItem();
        edit = new Menu();
        cut = new MenuItem();
        copy = new MenuItem();
        paste = new MenuItem();
        delete = new MenuItem();
        select = new MenuItem();
        help = new Menu();
        about = new MenuItem();
        textArea = new TextArea();
        textArea.setWrapText(true);
         
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(200.0);
        setPrefWidth(200.0);

        BorderPane.setAlignment(menuBar, javafx.geometry.Pos.CENTER);

        file.setMnemonicParsing(false);
        file.setText("File");

        New.setMnemonicParsing(false);
        New.setText("New");
        New.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        New.setOnAction((ActionEvent e) -> {
            if (textArea.getText().equals("")) {
                textArea.clear();
            } else {
                if (saved == false) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Notepad");
                    alert.setHeaderText("Do you want to save the changes?");
                    ButtonType saveButton = new ButtonType("Save");
                    ButtonType dontSaveButton = new ButtonType("Don't Save");
                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(saveButton, dontSaveButton, cancelButton);
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == saveButton) {
                        save();
                    } else if (result.get() == dontSaveButton) {
                        textArea.clear();
                    } else if (result.get() == cancelButton) {
                        alert.close();
                    } else {
                        alert.close();
                    }
                } else {
                    textArea.clear();
                }
            }

        });

        open.setMnemonicParsing(false);
        open.setText("Open..");
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        open.setOnAction((e) -> {
            if (textArea.getText().equals("")) {
                open();
            } else {
                if (saved == false) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Notepad");
                    alert.setHeaderText("Do you want to save the changes?");
                    ButtonType saveButton = new ButtonType("Save");
                    ButtonType dontSaveButton = new ButtonType("Don't Save");
                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(saveButton, dontSaveButton, cancelButton);
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == saveButton) {
                        save();
                        open();

                    } else if (result.get() == dontSaveButton) {
                        open();
                    } else if (result.get() == cancelButton) {
                        alert.close();
                    } else {
                        alert.close();
                    }
                } else {
                    open();
                }
            }

        });

        save.setMnemonicParsing(false);
        save.setText("Save");
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        save.setOnAction(e -> {
            saved = true;
            save();
        });

        exit.setMnemonicParsing(false);
        exit.setText("Exit");
        exit.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        exit.setOnAction((event) -> {
            if (textArea.getText().equals("")) {
                Platform.exit();
            } else {
                if (saved == false) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Notepad");
                    alert.setHeaderText("Do you want to save the changes?");
                    ButtonType saveButton = new ButtonType("Save");
                    ButtonType dontSaveButton = new ButtonType("Don't Save");
                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(saveButton, dontSaveButton, cancelButton);
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == saveButton) {
                        save();
                        Platform.exit();
                    } else if (result.get() == dontSaveButton) {
                        Platform.exit();
                    } else if (result.get() == cancelButton) {
                        alert.close();
                    } else {
                        alert.close();
                    }
                } else {
                    Platform.exit();
                }
            }
        });

        edit.setMnemonicParsing(false);
        edit.setText("Edit");

        cut.setMnemonicParsing(false);
        cut.setText("Cut");
        cut.setOnAction((event) -> {
            textArea.cut();
        });

        copy.setMnemonicParsing(false);
        copy.setText("Copy");
        copy.setOnAction((event) -> {
            textArea.copy();
        });

        paste.setMnemonicParsing(false);
        paste.setText("Paste");
        paste.setOnAction((event) -> {
            textArea.paste();
        });

        delete.setMnemonicParsing(false);
        delete.setText("Delete");
        delete.setOnAction((event) -> {
            textArea.replaceSelection("");
        });

        select.setMnemonicParsing(false);
        select.setText("SelectAll");
        select.setOnAction((event) -> {
            textArea.selectAll();
        });

        help.setMnemonicParsing(false);
        help.setText("Help");

        about.setMnemonicParsing(false);
        about.setText("About");
        about.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN));
        about.setOnAction((e) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Image image = new Image(getClass().getResource("image.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.setTitle("About Notepad");
            alert.setHeaderText("Notepad made by Somaya Ragab");
            alert.showAndWait();
        });

        setTop(menuBar);
        BorderPane.setAlignment(textArea, javafx.geometry.Pos.CENTER);
        textArea.setPrefHeight(272.0);
        textArea.setPrefWidth(600.0);
        setCenter(textArea);

        file.getItems().add(New);

        file.getItems().add(open);
        file.getItems().add(save);
        file.getItems().add(exit);
        menuBar.getMenus().add(file);
        edit.getItems().add(cut);
        edit.getItems().add(copy);
        edit.getItems().add(paste);
        edit.getItems().add(delete);
        edit.getItems().add(select);
        menuBar.getMenus().add(edit);
        help.getItems().add(about);
        menuBar.getMenus().add(help);

    }

    public void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("Untitled.txt");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                FileWriter fileWriter = new FileWriter(selectedFile);
                fileWriter.write(textArea.getText());
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(NotepadBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void open() {

        FileChooser fileChooser = new FileChooser();
        Window primaryStage = null;
        File myFile = fileChooser.showOpenDialog(primaryStage);
        try {
            FileInputStream fInputStr = new FileInputStream(myFile);
            int size = fInputStr.available();
            byte[] b = new byte[size];
            fInputStr.read(b);
            textArea.clear();
            textArea.setText(new String(b));
            fInputStr.close();
        } catch (IOException e) {
            System.out.println("Can't read this File !");
        }
    }

}
