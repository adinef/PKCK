package pl.lodz.p.it.pkck.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pl.lodz.p.it.pkck.XML.model.Category;
import pl.lodz.p.it.pkck.XML.model.Film;
import pl.lodz.p.it.pkck.XML.model.FilmDatabase;
import pl.lodz.p.it.pkck.XML.repository.FilmDatabaseCachingRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Controller {

    //******** TABLE MAPPING ******************
    private Map<Class, TableView> tableViewMapping = new HashMap<>();
    //*****************************************

    @FXML
    private VBox mainTabBox;

    @FXML
    private TabPane tabPane;

    private FilmDatabaseCachingRepository filmDatabaseDataRepository = new FilmDatabaseCachingRepository();

    public void saveFile(ActionEvent actionEvent) {

    }

    public void newFile(ActionEvent actionEvent) {

    }

    public void loadFile(ActionEvent actionEvent) throws Exception {
        File file = FileAccessViews.loadFileWindow("xml");
        Tab tab = readContents(file);
        tabPane.getTabs().add(tab);
        System.out.println(filmDatabaseDataRepository.load(file));
        VBox filmsTabContent = filmListTab();
        VBox categoriesTabContent = categoriesListTab();
        
        this.appendToolBar(filmsTabContent, () -> System.out.println("ADD"), () -> System.out.println("SAVE"));
        this.appendToolBar(categoriesTabContent, () -> System.out.println("ADD"), () -> System.out.println("SAVE"));
        
    }

    public VBox filmListTab() {
        return tableWithContent(Film.class, "Filmy", FilmDatabase::getFilms);
    }

    public VBox categoriesListTab() {
        return tableWithContent(Category.class, "Kategorie", FilmDatabase::getCategories);
    }


    public <T> VBox tableWithContent(Class<T> clazz, String name, Function<FilmDatabase, List<T>> func) {
        List<T> elems = new ArrayList<>();
        filmDatabaseDataRepository.getCached().ifPresentOrElse(
                (e) -> {
                    elems.addAll(func.apply(e));
                },
                () -> {
                    File file = FileAccessViews.loadFileWindow("xml");
                    try {
                        elems.addAll(func.apply(filmDatabaseDataRepository.load(file).get()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        List<TableColumn<String, T>> elemsColumns = TableGenerator.getSimpleColumnsForClass(clazz, true);
        TableView tableView = new TableView();
        tableView.getColumns().addAll(elemsColumns);
        tableView.getItems().addAll(elems);
        Tab tab = new Tab(name);
        VBox vBox = new VBox();
        vBox.getChildren().add(tableView);
        tab.setContent(vBox);
        tabPane.getTabs().add(tab);
        tableViewMapping.put(clazz, tableView);
        return vBox;
    }

    private Tab readContents(File file) throws Exception {
        final Tab tab = new Tab("Dane");
        final FilmDatabase filmDatabase = filmDatabaseDataRepository.load(file).orElseThrow();
        VBox mainBox = new VBox();
        Label label = new Label("Wybrana baza danych");
        Text author = new Text(filmDatabase.getAuthor());
        Text email = new Text(filmDatabase.getEmail());
        Text name = new Text(filmDatabase.getName());
        mainBox.getChildren().addAll(label, author, email, name);
        tab.setContent(mainBox);
        return tab;
    }

    private void appendToolBar(VBox box, Runnable onNew, Runnable onSave) {
        ToolBar toolBar = new ToolBar();
        Button newElemButton = new Button("Nowy element");
        Button saveDataButton = new Button("Zapisz dane");
        newElemButton.setOnAction( (e) -> {
            onNew.run();
            e.consume();
        });
        saveDataButton.setOnAction( (e) -> {
            onSave.run();
            e.consume();
        });
        toolBar.getItems().addAll(newElemButton, saveDataButton);
        toolBar.setOrientation(Orientation.HORIZONTAL);
        toolBar.setPadding(new Insets(10));
        box.getChildren().add(toolBar);
    }

}
