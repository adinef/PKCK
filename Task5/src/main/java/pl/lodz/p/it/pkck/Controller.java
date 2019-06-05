package pl.lodz.p.it.pkck;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import pl.lodz.p.it.pkck.XML.XmlUtils;
import pl.lodz.p.it.pkck.XML.model.Film;
import pl.lodz.p.it.pkck.XML.model.FilmDatabase;
import pl.lodz.p.it.pkck.XML.parsing.FilmDatabaseDataMapper;
import pl.lodz.p.it.pkck.XML.parsing.XmlDataMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private VBox mainTabBox;

    @FXML
    private Tab mainTab;

    @FXML
    private TabPane tabPane;

    public void saveFile(ActionEvent actionEvent) {

    }

    public void newFile(ActionEvent actionEvent) {

    }

    public void loadFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj plik .xml");
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Plik .xml", ".xml")
        );
        File file = fileChooser.showOpenDialog(Main.getMainStage());
        if (file != null) {






            try {
                Tab tab = this.readContents(file);
                tabPane.getTabs().add(tab);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private Tab readContents(File file) throws Exception {
        final Tab tab = new Tab("Dane");

        final XmlDataMapper<FilmDatabase> filmDatabaseXmlDataMapper = new FilmDatabaseDataMapper();
        final String content = XmlUtils.readFileContent(file);
        final String contentFiltered = XmlUtils.trimWithingTags(content);

        final FilmDatabase filmDatabase = filmDatabaseXmlDataMapper.readData(contentFiltered);

        VBox mainBox = new VBox();
        Label label = new Label("Wybrana baza danych");
        Text author = new Text(filmDatabase.getAuthor());
        Text email = new Text(filmDatabase.getEmail());
        Text name = new Text(filmDatabase.getName());

        mainBox.getChildren().addAll(label, author, email, name);
        tab.setContent(mainBox);

        return tab;
    }
}
