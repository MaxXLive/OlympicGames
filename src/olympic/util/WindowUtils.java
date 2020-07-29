package olympic.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import olympic.Main;

import java.io.IOException;

public class WindowUtils {

    public static boolean darkTheme = false;
    private static Parent root;

    public static void setDarkTheme(boolean darkTheme) {
        WindowUtils.darkTheme = darkTheme;
        if (darkTheme) {
            root.getStylesheets().add(WindowUtils.class.getResource("/style/dark_theme.css").toExternalForm());
        } else {
            root.getStylesheets().clear();
        }
    }

    public static void showMain(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(WindowUtils.class.getResource("/layout/layout_main.fxml"));
        primaryStage.setTitle("Olympico™");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(200);
        primaryStage.show();
        setDarkTheme(true);
    }

    public static void showNewEntryWindow(VBox dialogRoot, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(dialogRoot, 450, 450));
        stage.getIcons().add(new Image(WindowUtils.class.getResourceAsStream("/images/icon.png")));
        stage.setMinWidth(400);
        stage.setMaxWidth(700);
        stage.setMinHeight(490);
        stage.setMaxHeight(464);

        if (darkTheme) {
            dialogRoot.getStylesheets().add(WindowUtils.class.getResource("/style/dark_theme.css").toExternalForm());
        }
        stage.show();
    }

    public static void showInfoWindow(AnchorPane dialogRoot) {
        Stage stage = new Stage();
        stage.setTitle("Athlete information");
        stage.setScene(new Scene(dialogRoot, 650, 450));
        stage.getIcons().add(new Image(WindowUtils.class.getResourceAsStream("/images/icon.png")));
        stage.setMinWidth(400);
        stage.setMaxWidth(900);
        stage.setMinHeight(400);
        stage.setMaxHeight(600);

        if (darkTheme) {
            dialogRoot.getStylesheets().add(WindowUtils.class.getResource("/style/dark_theme.css").toExternalForm());
        }
        stage.show();
    }
}
