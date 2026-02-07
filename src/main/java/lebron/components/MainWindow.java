package lebron.components;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lebron.Lebron;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Lebron lebron;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setLebron(Lebron l) {
        lebron = l;
    }

    /**
     * Displays a dialog message in the dialog container.
     *
     * @param dialog The dialog box to display.
     */
    public void showMessage(DialogBox dialog) {
        dialogContainer.getChildren().add(dialog);
    }

    /**
     * Creates dialog boxes for the user input and chatbot response,
     * appends them to the dialog container, and clears the input field.
     */
    @FXML
    private void handleUserInput() {
        String fullCommand = userInput.getText();
        String fullResponse = lebron.getResponse(fullCommand);

        dialogContainer.getChildren().addAll(
                new UserDialog(fullCommand),
                new LebronDialog(fullResponse));
        userInput.clear();

        if (lebron.isExit()) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
