package lebron.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of a text label and a display picture.
 */
public abstract class DialogBox extends HBox {

    /**
     * Creates a dialog box with the given text, image, and layout settings.
     *
     * @param s The text to display.
     * @param i The image to display.
     * @param alignment The alignment of the dialog box.
     * @param isImageFirst Whether the image should appear before the text.
     */
    protected DialogBox(String s, Image i, Pos alignment, boolean isImageFirst) {
        Label text = new Label(s);
        ImageView displayPicture = new ImageView(i);

        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        this.setAlignment(alignment);

        if (isImageFirst) {
            this.getChildren().addAll(displayPicture, text);
        } else {
            this.getChildren().addAll(text, displayPicture);
        }
    }
}
