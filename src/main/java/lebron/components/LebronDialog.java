package lebron.components;

import javafx.scene.image.Image;

/**
 * Represents a dialog box for Lebron's responses, aligned to the left.
 */
public class LebronDialog extends DialogBox {
    private static final Image LEBRON_IMAGE = new Image(
            LebronDialog.class.getResourceAsStream("/images/LeSunShine.png"));

    /**
     * Creates a Lebron dialog box with the given text.
     *
     * @param text The text to display.
     */
    public LebronDialog(String text) {
        super(text, LEBRON_IMAGE, "/view/LebronDialog.fxml");
    }
}
