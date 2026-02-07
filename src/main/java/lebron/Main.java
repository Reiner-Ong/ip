package lebron;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    public Main(String filePath) {
        lebronChatbot = new Lebron(filePath);
    }

    // Overloaded constructor
    public Main() {
        this(DEFAULT_FILE_PATH);
    }

    @Override
    public void start(Stage stage) {
        try {
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
            e.printStackTrace();
        }
    }

    private void showStartupMessages(MainWindow controller) {
        String loadingError = lebronChatbot.getLoadingError();
        if (loadingError != null) {
            controller.showMessage(new LebronDialog("Ayo, couldn't load the save file: "
                    + loadingError + "\nLet's Run it back!"));
        }

        controller.showMessage(new LebronDialog(lebronChatbot.getWelcomeMessage()));
    }
}
