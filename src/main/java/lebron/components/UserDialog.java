package lebron.components;

import javafx.geometry.Pos;
import javafx.scene.image.Image;

/**
 * Represents a dialog box for user messages, aligned to the right.
 */
public class UserDialog extends DialogBox {
    private static final Image USER_IMAGE =
            new Image(UserDialog.class.getResourceAsStream("/images/Chaewon.jpeg"));

    /**
     * Creates a user dialog box with the given text.
     *
     * @param text The text to display.
     */
    public UserDialog(String text) {
        super(text, USER_IMAGE, Pos.TOP_RIGHT, false);
    }
}
