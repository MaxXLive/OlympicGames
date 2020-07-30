package olympic;

import javafx.application.Application;
import javafx.stage.Stage;
import olympic.util.WindowUtils;

public class Main extends Application {

    /**
     * Main method launches window.
     *
     * @param args Application arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Overridden start method from application class shows main window.
     *
     * @param primaryStage Primary stage for first window.
     * @throws Exception Throws exception when something goes wrong.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        WindowUtils.showMain(primaryStage);
    }
}
