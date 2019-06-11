package pl.lodz.p.it.pkck.view;

import javafx.stage.FileChooser;
import pl.lodz.p.it.pkck.Main;

import java.io.File;

public class FileAccessViews {

    public static File loadFileWindow(String extension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj plik ." + extension);
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Plik ." + extension, "." + extension)
        );
        return fileChooser.showOpenDialog(Main.getMainStage());
    }
}
