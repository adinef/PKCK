package pl.lodz.p.it.pkck.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import pl.lodz.p.it.pkck.XML.TableName;
import pl.lodz.p.it.pkck.XML.model.*;
import pl.lodz.p.it.pkck.XML.parsing.FilmDatabaseDataMapper;
import pl.lodz.p.it.pkck.events.RunActionEventHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Controller {

    public MenuItem saveItem;
    FilmDatabaseDataMapper filmDatabaseDataMapper = new FilmDatabaseDataMapper();
    FilmDatabase currentDatabase = null;
    RunActionEventHolder runActionEventHolder = new RunActionEventHolder();

    /******************** CLASS -> FILM DB SUPPLIER ************************************/
    private Map<Class, Function<FilmDatabase, List>> suppliers = new HashMap<>();
    /***********************************************************************************/
    /******************** CLASS -> TABLE VIEW (EDIT)************************************/
    private Map<Class, TableView> tableViews = new HashMap<>();
    /***********************************************************************************/

    @FXML
    private TabPane tabPane;

    public Controller() {
        this.suppliers.put(Film.class, FilmDatabase::getFilms);
        this.suppliers.put(Actor.class, FilmDatabase::getActors);
        this.suppliers.put(Director.class, FilmDatabase::getDirectors);
        this.suppliers.put(Category.class, FilmDatabase::getCategories);
        runActionEventHolder.addListener("loadedData", this::enableSaveButton);
        runActionEventHolder.addListener("beforeLoad", this::clearTableView);
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void loadFile() throws Exception {
        runActionEventHolder.start("beforeLoad");
        this.currentDatabase = FXUtils.openAndReadXml(filmDatabaseDataMapper::readData);
        this.createTabsForCollectionsOf(this.currentDatabase);
        runActionEventHolder.start("loadedData");
    }

    private void createTabsForCollectionsOf(FilmDatabase currentDatabase) {
        for (Map.Entry<Class, Function<FilmDatabase, List>> supplierPair : this.suppliers.entrySet()) {
            TableView tabl = TableUtils.getFromFilmDatabase(
                    this.currentDatabase,
                    supplierPair.getValue(),
                    supplierPair.getKey()
            );
            tabl.setOnMouseClicked(e -> this.handleDoubleClickFor(e, tabl.getSelectionModel().getSelectedItem()));
            Tab tab = new Tab();
            TableName annotation = (TableName)supplierPair.getKey().getAnnotation(TableName.class);
            if (annotation != null) {
                tab.setText(annotation.value());
            }
            VBox vBox = new VBox();
            vBox.getChildren().add(tabl);
            tab.setContent(vBox);
            tab.selectedProperty().addListener(
                    (e) -> {
                        tabl.refresh();
                        System.out.println("REFRESH: " + supplierPair.getKey());
                    }
            );
            this.tabPane.getTabs().add(tab);
            this.tableViews.put(supplierPair.getKey(), tabl);
        }
    }

    private void handleDoubleClickFor(MouseEvent event, Object obj) {
        if (event.getClickCount() == 2) {
            FXUtils.editMenuFor(obj);
            TableView tableView = this.tableViews.getOrDefault(obj.getClass(), null);
            if (tableView != null) {
                tableView.refresh();
            }
        }
    }

    private void enableSaveButton() {
        saveItem.setDisable(false);
    }

    private void disableSaveData() {
        saveItem.setDisable(true);
    }

    private void clearTableView() {
        this.tableViews.clear();
    }


    public void saveFile(ActionEvent actionEvent) {
    }
}
