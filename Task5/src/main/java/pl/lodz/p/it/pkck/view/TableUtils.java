package pl.lodz.p.it.pkck.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.lodz.p.it.pkck.XML.TableColumnCell;
import pl.lodz.p.it.pkck.XML.model.FilmDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class TableUtils {

    public static TableView getFromFilmDatabase(
            FilmDatabase database,
            Function<FilmDatabase, List> contentSupplier,
            Class clazz) {
        TableView tableView = new TableView<>();
        List<TableColumn> columns = TableUtils.createColumnsFor(clazz);
        tableView.getColumns().addAll(columns);
        tableView.getItems().addAll(contentSupplier.apply(database));
        return tableView;
    }

    private static <T> List<TableColumn> createColumnsFor(Class<T> clazz) {
        List<TableColumn> columns = new ArrayList<>();
        columns.addAll(createColumnsForFieldsOf(clazz));
        Class lClass = clazz.getSuperclass();
        while (lClass != null) {
            columns.addAll(createColumnsForFieldsOf(lClass));
            lClass = lClass.getSuperclass();
        }
        return columns;
    }

    private static List<TableColumn> createColumnsForFieldsOf(Class lClass) {
        List<TableColumn> columns = new ArrayList<>();
        for (Field field : lClass.getDeclaredFields()) {
            TableColumnCell ann = field.getAnnotation(TableColumnCell.class);
            if (ann != null) {
                TableColumn column = new TableColumn(ann.value());
                column.setCellValueFactory(
                        cellDataFeatures -> extractValueFrom(cellDataFeatures, field)
                );
                columns.add(column);
            }
        }
        return columns;
    }

    private static Object extractValueFrom(Object cellDataFeatures, Field field) {
        TableColumn.CellDataFeatures cdf = (TableColumn.CellDataFeatures)cellDataFeatures;
        field.setAccessible(true);
        String val = "";
        try {
            val =  field.get(cdf.getValue()).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new SimpleStringProperty(val);
    }
}
