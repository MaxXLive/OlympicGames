package olympic.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import olympic.entity.Athlete;
import olympic.entity.Event;
import olympic.entity.Medal;
import olympic.util.WindowUtils;
import static olympic.util.StringUtils.SEASONS;
import static olympic.util.StringUtils.isNeitherEmptyNorBlank;

public class AddEntryController {

    private final ObjectProperty<Athlete> athleteObject = new SimpleObjectProperty<>(null);
    private String id = null;
    @FXML
    private VBox root;
    @FXML
    private TextField athleteName;
    @FXML
    private TextField age;
    @FXML
    private TextField height;
    @FXML
    private TextField weight;
    @FXML
    private TextField teamName;
    @FXML
    private TextField teamNoc;
    @FXML
    private TextField eventName;
    @FXML
    private TextField sportName;
    @FXML
    private ComboBox<String> medalBox;
    @FXML
    private ComboBox<String> gameSeason;
    @FXML
    private TextField gameYear;
    @FXML
    private TextField gameCity;
    @FXML
    private ToggleGroup sexGroup;

    /**
     * Used to get the values out of the saved athlete object.
     *
     * @return Return actual athlete object for data access.
     */
    public Athlete getAthleteObject() {
        return athleteObject.get();
    }

    /**
     * Used as observable whenever the athlete variable gets updated (click on add button).
     *
     * @return Returns athlete object property which can be observed with addListener outside of this class.
     */
    public ObjectProperty<Athlete> athleteObjectProperty() {
        return athleteObject;
    }

    @FXML
    private void addClick() {
        if (areAllElementsFilled()) {
            addEntity();
        } else {
            showAlert();
        }
    }

    @FXML
    private void cancelClick() {
        WindowUtils.closeWindow(root);
    }

    private void showAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Could not add event");
        alert.setContentText("Please fill every element!");
        alert.showAndWait();
    }

    private void addEntity() {
        int ageValue = isNeitherEmptyNorBlank(age.getText()) ? Integer.parseInt(age.getText()) : 0;
        float heightValue = isNeitherEmptyNorBlank(height.getText()) ? Float.parseFloat(height.getText()) : 0;
        float weightValue = isNeitherEmptyNorBlank(weight.getText()) ? Float.parseFloat(weight.getText()) : 0;
        int yearValue = Integer.parseInt(gameYear.getText());
        char sexValue = sexGroup.getSelectedToggle().getUserData().toString().charAt(0);
        String gameName = gameSeason.getValue() + " " + yearValue;

        Event event = new Event(
                eventName.getText(),
                ageValue,
                heightValue,
                weightValue,
                sportName.getText(),
                Medal.fromString(medalBox.getValue()),
                gameName,
                gameSeason.getValue(),
                gameCity.getText(),
                yearValue
        );

        Athlete athlete = new Athlete(id, athleteName.getText(), sexValue, teamName.getText(), teamNoc.getText(), event);
        this.athleteObject.set(athlete);
        cancelClick();
    }

    /**
     * Loads values from passed athlete parameter,
     * sets text fields to number only where necessary and loads possible Medals.
     *
     * @param athlete Pass athlete if you want to edit one else pass {@code null} to create a new one
     */
    public void init(Athlete athlete) {
        onlyAllowNumbers(age);
        onlyAllowNumbers(weight);
        onlyAllowNumbers(height);
        onlyAllowNumbers(gameYear);

        if (athlete != null) {
            restoreValues(athlete);
            athleteName.setDisable(true);
            teamName.setDisable(true);
            teamNoc.setDisable(true);
            sexGroup.getToggles().forEach(toggle -> ((RadioButton) toggle).setDisable(true));
        }

        for (Medal medal : Medal.values()) {
            medalBox.getItems().add(medal.toString());
        }
        gameSeason.getItems().addAll(SEASONS);
    }

    private void restoreValues(Athlete athlete) {
        id = athlete.getId();
        athleteName.setText(athlete.getName());
        sexGroup.getToggles().get(0).setSelected(athlete.getSex() == 'F');
        sexGroup.getToggles().get(1).setSelected(athlete.getSex() != 'F');
        age.setText(athlete.getLastAgeFormatted());
        weight.setText(athlete.getLastWeightRounded());
        height.setText(athlete.getLastHeightRounded());
        teamName.setText(athlete.getTeam().getName());
        teamNoc.setText(athlete.getTeam().getNoc());
    }

    private void onlyAllowNumbers(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private boolean areAllElementsFilled() {
        return (isNeitherEmptyNorBlank(athleteName.getText()) &&
                isNeitherEmptyNorBlank(teamName.getText()) &&
                isNeitherEmptyNorBlank(teamNoc.getText()) &&
                isNeitherEmptyNorBlank(eventName.getText()) &&
                isNeitherEmptyNorBlank(sportName.getText()) &&
                medalBox.getValue() != null &&
                gameSeason.getValue() != null &&
                isNeitherEmptyNorBlank(gameYear.getText()) &&
                isNeitherEmptyNorBlank(gameCity.getText())
        );
    }
}
