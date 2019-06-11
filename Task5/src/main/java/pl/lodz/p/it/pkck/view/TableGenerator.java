package pl.lodz.p.it.pkck.view;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.simpleframework.xml.Element;
import pl.lodz.p.it.pkck.annotations.Column;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class TableGenerator {

    public static Optional noDataPopup(String title, String body, Scene scene) {
        JFXAlert alert = new JFXAlert((Stage) scene.getWindow());
        JFXDialogLayout layout = new JFXDialogLayout();
        JFXButton closeButton = new JFXButton("Zamknij");
        closeButton.setButtonType(JFXButton.ButtonType.FLAT);
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setHeading(new Label(title));
        layout.setBody(new Label(body));
        layout.setActions(closeButton);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(true);
        alert.setContent(layout);
        return alert.showAndWait();
    }

    public static Boolean yesNoPopup(String title, String body, Scene scene) {
        AtomicBoolean result = new AtomicBoolean(false);
        JFXAlert alert = new JFXAlert((Stage) scene.getWindow());
        JFXDialogLayout layout = new JFXDialogLayout();

        JFXButton noButton = new JFXButton("Nie");
        noButton.setButtonType(JFXButton.ButtonType.FLAT);
        noButton.setOnAction(event -> alert.hideWithAnimation());

        JFXButton yesButton = new JFXButton("Tak");
        yesButton.setButtonType(JFXButton.ButtonType.FLAT);
        yesButton.setOnAction(event -> {
            alert.hideWithAnimation();
            result.set(true);
        });

        layout.setHeading(new Label(title));
        layout.setBody(new Label(body));
        layout.setActions(noButton, yesButton);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        alert.setHideOnEscape(false);
        alert.setContent(layout);
        alert.showAndWait();
        return result.get();
    }

    public static <T> List<TableColumn<String, T>> getSimpleColumnsForClass(Class<T> tClass, boolean editable) {
        List<TableColumn<String, T>> columns = new LinkedList<>();
        for (Field field : tClass.getDeclaredFields()) {
            createColumn(editable, columns, field);
        }
        Class loopClass = tClass.getSuperclass();
        while (loopClass != null) {
            for (Field field : loopClass.getDeclaredFields()) {
                createColumn(editable, columns, field);
            }
            loopClass = loopClass.getSuperclass();
        }
        return columns;
    }

    private static <T> void createColumn(boolean editable, List<TableColumn<String, T>> columns, Field field) {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation != null) {
            TableColumn<String, T> column = new TableColumn<>(annotation.value());
            column.setEditable(editable);
            column.setPrefWidth(100);
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            columns.add(column);
        }
    }
}
