package pl.lodz.p.it.pkck.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import pl.lodz.p.it.pkck.XML.TableName;
import pl.lodz.p.it.pkck.XML.model.*;
import pl.lodz.p.it.pkck.XML.parsing.FilmDatabaseDataMapper;
import pl.lodz.p.it.pkck.events.RunActionEventHolder;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Controller {

    public MenuItem saveItem;
    FilmDatabaseDataMapper filmDatabaseDataMapper = new FilmDatabaseDataMapper();
    FilmDatabase currentDatabase = null;
    private File databaseFile;
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
        FXUtils
                .openAndReadXml(filmDatabaseDataMapper::readData)
                .ifPresent(
                        (e) -> {
                            this.currentDatabase = e.getElem();
                            this.databaseFile = e.getFile();
                            this.createTabsForCollectionsOf(this.currentDatabase);
                            runActionEventHolder.start("loadedData");
                        }
                );
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

            tabl.setOnMouseClicked(
                    (e) -> {
                        if (e.getClickCount() == 2) {
                            this.edit(tabl.getSelectionModel().getSelectedItem());
                            reloadContent(supplierPair, tabl);
                            e.consume();
                        }
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
                        this.deleteElementFromList(tabl.getSelectionModel().getSelectedItem(), supplierPair.getValue());
                        reloadContent(supplierPair, tabl);
                        e.consume();
                    }
            );

            tabl.setOnKeyPressed(
                    (e) -> {
                        if (e.getCode().equals(KeyCode.DELETE)) {
                            this.deleteElementFromList(tabl.getSelectionModel().getSelectedItem(), supplierPair.getValue());
                            reloadContent(supplierPair, tabl);
                            e.consume();
                        }
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

    private void deleteElementFromList(Object selectedItem, Function<FilmDatabase, List> value) {
        if (FXUtils.yesOrNoPopup("Delete", "Are you sure you want to delete the element?")) {
            value.apply(currentDatabase).remove(selectedItem);
        }
    }

    private void reloadContent(Map.Entry<Class, Function<FilmDatabase, List>> supplierPair,
                               TableView tabl) {
        int selectedIndex = tabl.getSelectionModel().getSelectedIndex();
        tabl.getItems().clear();
        tabl.getItems().addAll(supplierPair.getValue().apply(currentDatabase));
        tabl.getSelectionModel().select(selectedIndex);
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

    @FXML
    private void saveFile() {
        try {
            this.filmDatabaseDataMapper.saveData(
                    this.currentDatabase,
                    this.databaseFile,
                    FilmDatabase.class,
                    Actor.class,
                    Director.class,
                    Film.class,
                    Category.class
            );
            FXUtils.noDataPopup("Success", "Saved with success!");
        } catch (Exception e) {
            FXUtils.noDataPopup("Error", "There has been error during saving, " + e.getMessage());
            e.printStackTrace();
        }
    }
}
