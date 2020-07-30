package olympic.controller;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import olympic.entity.Athlete;
import olympic.util.EventInfoDao;
import olympic.util.ListUtils;
import olympic.util.StringUtils;
import olympic.util.WindowUtils;

public class InfoController {

    private Athlete athlete;
    @FXML
    private AnchorPane root;
    @FXML
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label sexLabel;
    @FXML
    private Label teamLabel;
    @FXML
    private Label lastAgeLabel;
    @FXML
    private Label lastHeightLabel;
    @FXML
    private Label lastWeightLabel;
    @FXML
    private TableView<EventInfoDao> eventTable;

    @FXML
    private void closeWindow() {
        WindowUtils.closeWindow(root);
    }

    @FXML
    private void addEvent() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/layout_add_entry.fxml"));
            VBox dialogRoot = fxmlLoader.load();
            AddEntryController controller = fxmlLoader.getController();
            controller.init(athlete);
            controller.athleteObjectProperty().addListener(observable -> {
                Athlete athlete = controller.getAthleteObject();
                System.out.println("Added: " + athlete.toString());
                ListUtils.add(athlete);
                this.init(ListUtils.get(athlete.getId()));
            });

            WindowUtils.showNewEntryWindow(dialogRoot, "Add Event");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes values and table entries by passed athlete
     *
     * @param athlete Pass athlete of which you want to show the information
     */
    public void init(Athlete athlete) {
        this.athlete = athlete;

        nameLabel.setText(athlete.getName());
        idLabel.setText("ID: " + athlete.getId());
        sexLabel.setText("Sex: " + StringUtils.formatSex(athlete.getSex()));
        teamLabel.setText("Team: " + athlete.getTeam().getName() + ", " + athlete.getTeam().getNoc());
        lastAgeLabel.setText("Last Age: " + athlete.getLastAgeFormatted());
        lastHeightLabel.setText("Last Height: " + athlete.getLastHeightFormatted());
        lastWeightLabel.setText("Last Weight: " + athlete.getLastWeightFormatted());


        eventTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("event"));
        eventTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("game"));
        eventTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("city"));
        eventTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("age"));
        eventTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("height"));
        eventTable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("weight"));
        eventTable.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("medal"));
        eventTable.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("sport"));

        List<EventInfoDao> eventInfoList = EventInfoDao.getListFromAthlete(athlete);
        ObservableList<EventInfoDao> list = FXCollections.observableArrayList(eventInfoList);
        eventTable.setItems(list);
    }
}
