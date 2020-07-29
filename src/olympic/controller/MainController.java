package olympic.controller;

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
import javafx.stage.FileChooser;
import olympic.entity.Athlete;
import olympic.entity.Event;
import olympic.entity.Game;
import olympic.entity.Sport;
import olympic.entity.Team;
import olympic.util.FileUtils;
import olympic.util.ListUtils;
import olympic.util.WindowUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static olympic.util.StringUtils.CSV_HEADER;

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
    private Label entitiesLabel;
    @FXML
    private CheckMenuItem darkThemeCheckbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateListView());
        athletesList.setOnMouseClicked(this::listItemClick);
        athletesList.setOnKeyPressed(this::listItemEnter);
        FileUtils.readFile();
        updateListView();
    }


    @FXML
    public void closeWindow() {
        System.exit(0);
    }

    public void listItemClick(MouseEvent click) {
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
    public void clearDatabase() {
        FileUtils.clear();
        ListUtils.clear();
        updateListView();
    }


    @FXML
    public void loadDatabase() {
        FileUtils.loadDatabase(root);
        updateListView();
    }

    @FXML
    public void saveDatabase() {
        FileUtils.saveDatabase(root);
    }

    @FXML
    public void saveFile() {
        FileUtils.saveFile(root);
    }

    public void changeTab() {
        if (entitiesLabel != null) {
            entitiesLabel.setText("Entities: " + getCurrentListView().getItems().size());
        }
    }


    public void updateListView() {

        // for each collumn, filtered by search and sorted
        athletesList.getItems().clear();
        Supplier<Stream<Map.Entry<String, Athlete>>> athletes = () -> ListUtils.getAthletes().entrySet().stream()
                .filter(athlete -> athlete.getValue().getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(Athlete::getName)));
        athletes.get().forEach(athlete -> athletesList.getItems().add(athlete.getValue()));

        teamsList.getItems().clear();
        Supplier<Stream<Team>> teams = () -> Team.teams.stream()
                .filter(team -> team.getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                .sorted(Comparator.comparing(Team::getName));
        teams.get().forEach(team -> teamsList.getItems().add(team.getName()));

        sportsList.getItems().clear();
        Supplier<Stream<Sport>> sports = () -> Sport.sports.stream()
                .filter(sport -> sport.getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                .sorted(Comparator.comparing(Sport::getName));
        sports.get().forEach(sport -> sportsList.getItems().add(sport.getName()));

        eventsList.getItems().clear();
        Supplier<Stream<Event>> events = () -> Event.events.stream()
                .filter(event -> event.getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                .sorted(Comparator.comparing(Event::getName));
        events.get().forEach(event -> eventsList.getItems().add(event.getName()));

        gamesList.getItems().clear();
        Supplier<Stream<Game>> games = () -> Game.games.stream()
                .filter(game -> game.getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                .sorted(Comparator.comparing(Game::getName));
        games.get().forEach(game -> gamesList.getItems().add(game.getName()));


        changeTab();
    }

    private ListView<?> getCurrentListView() {
        int selectedTabIndex = tabs.getSelectionModel().getSelectedIndex();
        switch (selectedTabIndex){
            case 0: return athletesList;
            case 1: return teamsList;
            case 2: return sportsList;
            case 3: return eventsList;
            case 4: return gamesList;
            default: throw new IllegalStateException("Unexpected tab index: " + selectedTabIndex);
        }
    }


    @FXML
    private void toggleDarkTheme() {
        WindowUtils.setDarkTheme(darkThemeCheckbox.isSelected());
    }
}
