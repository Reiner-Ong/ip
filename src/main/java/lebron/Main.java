package lebron;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lebron.components.LebronDialog;
import lebron.components.MainWindow;

/**
 * Main JavaFX application class for the Lebron chatbot GUI.
 */
public class Main extends Application {
    private static final String DEFAULT_FILE_PATH = "./data/lebron.txt";

    private final Lebron lebronChatbot;

    /**
     * Creates a Main application with the specified file path for task storage.
     *
     * @param filePath The path to the file for storing tasks.
     */
    public Main(String filePath) {
        lebronChatbot = new Lebron(filePath);
    }

    /**
     * Creates a Main application with the default file path for task storage.
     */
    public Main() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Starts the JavaFX application by loading the FXML and displaying the GUI.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("LeBron");

            // Get the controller and inject the Lebron instance
            MainWindow controller = fxmlLoader.getController();
            controller.setLebron(lebronChatbot);

            // Show startup messages
            showStartupMessages(controller);

            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("LeBron - Startup Error");
            alert.setHeaderText("Failed to load the application.");
            alert.setContentText("Could not load the UI: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Displays startup messages including any loading errors and the welcome
     * message.
     *
     * @param controller The MainWindow controller to display messages in.
     */
    private void showStartupMessages(MainWindow controller) {
        String loadingError = lebronChatbot.getLoadingError();
        if (loadingError != null) {
            controller.showMessage(new LebronDialog("Ayo, couldn't load the save file: "
                    + loadingError + "\nLet's Run it back!"));
        }

        controller.showMessage(new LebronDialog(lebronChatbot.getWelcomeMessage()));
    }
}
