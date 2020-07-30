package olympic.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import olympic.entity.Athlete;
import olympic.entity.Event;
import olympic.entity.Game;
import olympic.entity.Sport;
import olympic.entity.Team;
import olympic.util.FileUtils;
import olympic.util.ListUtils;
import olympic.util.WindowUtils;

public class MainController implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private ListView<Athlete> athletesList;
    @FXML
    private ListView<String> teamsList;
    @FXML
    private ListView<String> sportsList;
    @FXML
    private ListView<String> eventsList;
    @FXML
    private ListView<String> gamesList;
    @FXML
    private TabPane tabs;
    @FXML
    private TextField searchField;
    @FXML
    private Label entriesLabel;
    @FXML
    private CheckMenuItem darkThemeCheckbox;

    /**
     * Initializes the controller: Adds listeners, loads default file
     *
     * @param location  The location used to resolve relative paths for the root object, or {@code null} if the location is not known. (Aren't used, needed for override)
     * @param resources The resources used to localize the root object, or null if the root object was not localized. (Aren't used, needed for override)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateListView());
        athletesList.setOnMouseClicked(this::listItemClick);
        athletesList.setOnKeyPressed(this::listItemEnter);
        FileUtils.readFile();
        updateListView();
    }


    @FXML
    private void closeWindow() {
        System.exit(0);
    }

    private void listItemClick(MouseEvent click) {
        if (click.getClickCount() == 2) {
            showInfoWindow();
        }
    }

    private void listItemEnter(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            showInfoWindow();
        }
    }

    private void showInfoWindow() {
        Athlete athlete = athletesList.getSelectionModel().getSelectedItem();
        if (athlete != null) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/layout_info.fxml"));
                AnchorPane dialogRoot = fxmlLoader.load();
                InfoController controller = fxmlLoader.getController();
                controller.init(athlete);

                WindowUtils.showInfoWindow(dialogRoot);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void showNewEntryWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/layout_add_entry.fxml"));
            VBox dialogRoot = fxmlLoader.load();
            AddEntryController controller = fxmlLoader.getController();
            controller.init(null);
            controller.athleteObjectProperty().addListener(observable -> {
                Athlete athlete = controller.getAthleteObject();
                System.out.println("Added: " + athlete.toString());
                ListUtils.add(athlete);
                updateListView();
            });
            WindowUtils.showNewEntryWindow(dialogRoot, "New Athlete");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearDatabase() {
        FileUtils.clear();
        ListUtils.clear();
        updateListView();
    }


    @FXML
    private void loadDatabase() {
        FileUtils.loadDatabase(root);
        updateListView();
    }

    @FXML
    private void saveDatabase() {
        FileUtils.saveDatabase(root);
    }

    @FXML
    private void saveFile() {
        FileUtils.saveFile(root);
    }

    /**
     * Gets called when changing tabs to refresh entries size
     */
    public void changeTab() {
        if (entriesLabel != null) {
            entriesLabel.setText("Entries: " + getCurrentListView().getItems().size());
        }
    }


    private void updateListView() {
        // for each column, filtered by search and sorted
        athletesList.getItems().clear();
        Supplier<Stream<Map.Entry<String, Athlete>>> athletes = () -> ListUtils.getAthletes().entrySet().stream()
                .filter(athlete -> athlete.getValue().getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(Athlete::getName)));
        athletes.get().forEach(athlete -> athletesList.getItems().add(athlete.getValue()));

        teamsList.getItems().clear();
        Supplier<Stream<Team>> teams = () -> Team.getTeams().stream()
                .filter(team -> team.getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                .sorted(Comparator.comparing(Team::getName));
        teams.get().forEach(team -> teamsList.getItems().add(team.getName()));

        sportsList.getItems().clear();
        Supplier<Stream<Sport>> sports = () -> Sport.getSports().stream()
                .filter(sport -> sport.getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                .sorted(Comparator.comparing(Sport::getName));
        sports.get().forEach(sport -> sportsList.getItems().add(sport.getName()));

        eventsList.getItems().clear();
        Supplier<Stream<Event>> events = () -> Event.getEvents().stream()
                .filter(event -> event.getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                .sorted(Comparator.comparing(Event::getName));
        events.get().forEach(event -> eventsList.getItems().add(event.getName()));

        gamesList.getItems().clear();
        Supplier<Stream<Game>> games = () -> Game.getGames().stream()
                .filter(game -> game.getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                .sorted(Comparator.comparing(Game::getName));
        games.get().forEach(game -> gamesList.getItems().add(game.getName()));


        changeTab();
    }

    private ListView<?> getCurrentListView() {
        int selectedTabIndex = tabs.getSelectionModel().getSelectedIndex();
        switch (selectedTabIndex) {
            case 0:
                return athletesList;
            case 1:
                return teamsList;
            case 2:
                return sportsList;
            case 3:
                return eventsList;
            case 4:
                return gamesList;
            default:
                throw new IllegalStateException("Unexpected tab index: " + selectedTabIndex);
        }
    }


    @FXML
    private void toggleDarkTheme() {
        WindowUtils.setDarkTheme(darkThemeCheckbox.isSelected());
    }
}
