package sixseven.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sixseven.DukeException;
import sixseven.Storage;
import sixseven.TaskList;

import java.io.InputStream;

public class MainWindow {
    private static final String USER_IMAGE_PATH = "/images/user.png";
    private static final String DUKE_IMAGE_PATH = "/images/duke.png";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private CommandHandler commandHandler;
    private Storage storage;
    private TaskList tasks;
    private Image userImage;
    private Image dukeImage;
    private String loadingErrorMessage;

    public void setDataFile(String filePath) {
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
            loadingErrorMessage = null;
        } catch (DukeException e) {
            tasks = new TaskList();
            loadingErrorMessage = "Oops! Could not load tasks from file. Starting with empty list.";
        }
        commandHandler = new CommandHandler(tasks, storage);
        userImage = loadImage(USER_IMAGE_PATH);
        dukeImage = loadImage(DUKE_IMAGE_PATH);
    }

    private Image loadImage(String path) {
        try (InputStream in = getClass().getResourceAsStream(path)) {
            return in != null ? new Image(in) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private ImageView imageViewFor(Image img) {
        return img != null ? new ImageView(img) : null;
    }

    public void showWelcome() {
        if (loadingErrorMessage != null) {
            dialogContainer.getChildren().add(DialogBox.getDukeDialog(
                    loadingErrorMessage, imageViewFor(dukeImage)));
        }
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(
                "Hello! I'm SixSeven\nWhat can I do for you?", imageViewFor(dukeImage)));
    }

    @FXML
    private void handleUserInput() {
        String text = userInput.getText().trim();
        if (text.isEmpty()) {
            return;
        }
        userInput.clear();
        dialogContainer.getChildren().add(DialogBox.getUserDialog(text, imageViewFor(userImage)));

        GuiResponse response = commandHandler.processCommand(text);
        String combined = String.join("\n", response.getMessages());
        if (!combined.isEmpty()) {
            dialogContainer.getChildren().add(DialogBox.getDukeDialog(combined, imageViewFor(dukeImage)));
        }

        if (response.shouldExit()) {
            Stage stage = (Stage) sendButton.getScene().getWindow();
            stage.close();
        }

        scrollToBottom();
    }

    private void scrollToBottom() {
        scrollPane.applyCss();
        scrollPane.layout();
        scrollPane.setVvalue(1.0);
    }
}
