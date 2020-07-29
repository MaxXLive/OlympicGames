package olympic;

import javafx.application.Application;
import javafx.stage.Stage;
import olympic.util.WindowUtils;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WindowUtils.showMain(primaryStage);
    }
}
