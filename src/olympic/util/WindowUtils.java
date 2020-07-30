package olympic.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import olympic.Main;

/**
 * Handles everything window related.
 */
public class WindowUtils {

    private static boolean darkTheme = true;
    private static final List<Pane> openWindows = new ArrayList<>();

    /**
     * Closes window and removes from openWindows which is used to toggle dark theme on all open windows.
     *
     * @param pane Root pane of window.
     */
    public static void closeWindow(Pane pane) {
        openWindows.remove(pane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    /**
     * Enables and disables dark theme for all open windows.
     *
     * @param darkTheme boolean if dark theme should be enabled.
     */
    public static void setDarkTheme(boolean darkTheme) {
        WindowUtils.darkTheme = darkTheme;
        openWindows.forEach(pane -> {
            if (darkTheme) {
                pane.getStylesheets().add(WindowUtils.class.getResource("/style/dark_theme.css").toExternalForm());
            } else {
                pane.getStylesheets().clear();
            }
        });
    }

    /**
     * Shows Main window
     *
     * @param primaryStage Stage passed by main function.
     * @throws IOException Exception when loading window layout fails.
     */
    public static void showMain(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(WindowUtils.class.getResource("/layout/layout_main.fxml"));
        primaryStage.setTitle("Olympico™");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(200);
        primaryStage.show();

        openWindows.add((Pane) root);
        setDarkTheme(darkTheme);
    }

    /**
     * Shows new event / new athlete window.
     *
     * @param dialogRoot Root for new window for access outside of class.
     * @param title      Title of window.
     */
    public static void showNewEntryWindow(VBox dialogRoot, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(dialogRoot, 450, 450));
        stage.getIcons().add(new Image(WindowUtils.class.getResourceAsStream("/images/icon.png")));
        stage.setMinWidth(400);
        stage.setMaxWidth(700);
        stage.setMinHeight(490);
        stage.setMaxHeight(464);
        stage.setOnCloseRequest(windowEvent -> closeWindow(dialogRoot));
        stage.show();

        openWindows.add(dialogRoot);
        setDarkTheme(darkTheme);
    }

    /**
     * Shows athlete information window.
     *
     * @param dialogRoot Root for new window for access outside of class.
     */
    public static void showInfoWindow(AnchorPane dialogRoot) {
        Stage stage = new Stage();
        stage.setTitle("Athlete information");
        stage.setScene(new Scene(dialogRoot, 650, 450));
        stage.getIcons().add(new Image(WindowUtils.class.getResourceAsStream("/images/icon.png")));
        stage.setMinWidth(400);
        stage.setMaxWidth(900);
        stage.setMinHeight(400);
        stage.setMaxHeight(600);
        stage.setOnCloseRequest(windowEvent -> closeWindow(dialogRoot));
        stage.show();

        openWindows.add(dialogRoot);
        setDarkTheme(darkTheme);
    }
}
