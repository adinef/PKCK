package pl.lodz.p.it.pkck.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
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

            ContextMenu contextMenu = new ContextMenu();
            MenuItem newItem = new MenuItem("New item");
            MenuItem editItem = new MenuItem("Edit item");
            MenuItem deleteItem = new MenuItem("Delete item");
            contextMenu.getItems().addAll(editItem, newItem, deleteItem);
            tabl.setContextMenu(contextMenu);

            editItem.setOnAction(
                    (e) -> {
                        this.edit(tabl.getSelectionModel().getSelectedItem());
                        reloadContent(supplierPair, tabl);
                        e.consume();
                    }
            );

            newItem.setOnAction(
                    (e) -> {
                        this.newElem(supplierPair.getKey(), supplierPair.getValue().apply(this.currentDatabase));
                        reloadContent(supplierPair, tabl);
                        e.consume();
                    }
            );

            deleteItem.setOnAction(
                    (e) -> {
                        supplierPair.getValue().apply(currentDatabase).remove(tabl.getSelectionModel().getSelectedItem());
                        reloadContent(supplierPair, tabl);
                        e.consume();
                    }
            );

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
                    }
            );
            this.tabPane.getTabs().add(tab);
            this.tableViews.put(supplierPair.getKey(), tabl);
        }
    }

    private void reloadContent(Map.Entry<Class, Function<FilmDatabase, List>> supplierPair,
                               TableView tabl) {
        int selectedIndex = tabl.getSelectionModel().getSelectedIndex();
        tabl.getItems().clear();
        tabl.getItems().addAll(supplierPair.getValue().apply(currentDatabase));
        tabl.getSelectionModel().focus(selectedIndex);
    }

    private void edit(Object obj) {
        if (obj != null) {
            if (obj instanceof Film) {
                FilmEditUtils.editFilmMenu((Film)obj, this.currentDatabase);
            } else {
                FXUtils.editMenuFor(obj);
            }
        }
    }

    private void newElem(Class clazz, List<?> elems) {
        if (Film.class.equals(clazz)) {
            FilmEditUtils.addFilmMenu(this.currentDatabase);
        } else {
            FXUtils.addMenuFor(clazz, elems);
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
