package sixseven.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

public class DialogBox extends HBox {
    private static final double WRAP_WIDTH = 320;
    private static final int PADDING = 12;
    private static final int SPACING = 10;

    private DialogBox(Label text, ImageView avatar, boolean isUser) {
        setSpacing(SPACING);
        setPadding(new Insets(PADDING));
        setAlignment(isUser ? Pos.TOP_RIGHT : Pos.TOP_LEFT);
        getStyleClass().add(isUser ? "dialog-user" : "dialog-duke");

        text.setWrapText(true);
        text.setMaxWidth(WRAP_WIDTH);
        text.setMinHeight(Region.USE_PREF_SIZE);

        if (avatar != null) {
            avatar.setFitWidth(40);
            avatar.setFitHeight(40);
            avatar.setPreserveRatio(true);
            Circle clip = new Circle(20, 20, 20);
            avatar.setClip(clip);
        }

        if (isUser) {
            getChildren().addAll(text, avatar != null ? avatar : placeholderNode());
        } else {
            getChildren().addAll(avatar != null ? avatar : placeholderNode(), text);
        }
    }

    private static javafx.scene.Node placeholderNode() {
        javafx.scene.layout.StackPane p = new javafx.scene.layout.StackPane();
        p.setMinSize(40, 40);
        p.setMaxSize(40, 40);
        p.getStyleClass().add("avatar-placeholder");
        return p;
    }

    public static DialogBox getUserDialog(String text, ImageView userImage) {
        Label l = new Label(text);
        l.getStyleClass().add("dialog-text");
        return new DialogBox(l, userImage, true);
    }

    public static DialogBox getUserDialog(String text) {
        return getUserDialog(text, null);
    }

    public static DialogBox getDukeDialog(String text, ImageView dukeImage) {
        Label l = new Label(text);
        l.getStyleClass().add("dialog-text");
        return new DialogBox(l, dukeImage, false);
    }

    public static DialogBox getDukeDialog(String text) {
        return getDukeDialog(text, null);
    }
}
