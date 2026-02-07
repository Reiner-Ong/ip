package lebron;

import lebron.command.Command;
import lebron.components.DialogBox;
import lebron.storage.Storage;
import lebron.task.TaskList;
import lebron.ui.Parser;
import lebron.ui.Ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class for the Lebron chatbot application.
 * This chatbot helps users manage their tasks with a basketball-themed personality.
 */
public class Lebron {
    private static final String FILE_PATH = "./data/lebron.txt";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a new Lebron chatbot instance.
     *
     * @param filePath The path to the file for storing tasks.
     */
    public Lebron(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (LebronException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the chatbot main loop.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (LebronException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }

        ui.close();
    }

//    /**
//     * Main entry point for the Lebron chatbot.
//     *
//     * @param args Command line arguments (not used).
//     */
//    public static void main(String[] args) {
//        new Lebron(FILE_PATH).run();
//    }
}
