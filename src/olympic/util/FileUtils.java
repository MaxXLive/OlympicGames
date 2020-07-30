package olympic.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import olympic.entity.Athlete;
import static olympic.util.StringUtils.CSV_HEADER;

/**
 * Handles everything related on file loading and saving
 */
public class FileUtils {

    private static String filePath = "olympic.db";

    /**
     * Resets file path to {@code null}. Therefore disables saving without explicitly selecting file path.
     */
    public static void clear() {
        FileUtils.filePath = null;
    }

    /**
     * Shows open file dialog, updates filepath and calls file loading.
     * Selection of .db files and all file types is possible.
     *
     * @param root Root layout of window of type border pane.
     */
    public static void loadDatabase(BorderPane root) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Database File");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Database files", "*.db"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            filePath = file.getAbsolutePath();
            readFile();
        }
    }

    /**
     * Shows save file dialog, updates filepath and calls file saving.
     * Selection of .db files and all file types is possible.
     *
     * @param root Root layout of window of type border pane.
     */
    public static void saveDatabase(BorderPane root) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Database File");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Database files", "*.db"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (file != null) {
            filePath = file.getAbsolutePath();
            saveFile(root);
        }
    }

    /**
     * Reads .db (csv) file from filepath and adds entries to list.
     */
    public static void readFile() {
        ListUtils.clear();
        try {
            File file = new File(filePath);
            System.out.println("Reading file... " + file.getAbsolutePath());
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (!line.contains("ID")) {
                    ListUtils.add(Athlete.fromCSVLine(line));
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("File " + filePath + " not found!");
        }
    }

    /**
     * Saves current list entries to filepath
     *
     * @param root Root layout of window of type border pane.
     */
    @FXML
    public static void saveFile(BorderPane root) {
        if (filePath == null) {
            saveDatabase(root);
        } else {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                System.out.println("Writing to file... " + new File(filePath).getAbsolutePath());
                String header = CSV_HEADER + "\n";
                bw.write(header);
                Stream<Athlete> sortedAthletes = ListUtils.getAthletes().values().stream()
                        .sorted(Comparator.comparing(Athlete::getIdNumber));
                sortedAthletes.forEach(athlete -> {
                    try {
                        bw.write(athlete.toCSV());
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
