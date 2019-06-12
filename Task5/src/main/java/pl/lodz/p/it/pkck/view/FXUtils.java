package pl.lodz.p.it.pkck.view;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.lodz.p.it.pkck.Main;
import pl.lodz.p.it.pkck.XML.model.Category;
import pl.lodz.p.it.pkck.view.common.FunctionWithException;

import java.io.File;

public class FXUtils {

    public static <T> T openAndReadXml(FunctionWithException<File, T> fileHandler) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Plik .xml", "xml"));
        File file = fileChooser.showOpenDialog(Main.getMainStage());
        if (file != null) {
            return fileHandler.apply(file);
        }
        return null;
    }

    public static void editMenuFor(Object object) {
        if (object instanceof Category) {
            editMenuForCategory((Category) object);
        }
    }

    private static void editMenuForCategory(Category cat) {
        VBox mainVBox = new VBox();
        mainVBox.setMinHeight(500);
        mainVBox.setSpacing(10);
        Label nameLabel = new Label("Name");
        TextField nameTextField = new TextField(cat.getName());
        JFXAlert alert = new JFXAlert((Stage) Main.getMainStage());
        JFXDialogLayout layout = new JFXDialogLayout();
        JFXButton saveButton = new JFXButton("Save");
        JFXButton cancelButton = new JFXButton("Cancel");
        saveButton.setOnAction(
                (e) -> {
                    cat.setName(nameTextField.getText());
                    alert.hideWithAnimation();
                    e.consume();
                }
        );
        cancelButton.setOnAction(
                (e) -> {
                    alert.hideWithAnimation();
                    e.consume();
                }
        );
        mainVBox.getChildren().addAll(nameLabel, nameTextField);
        layout.setHeading(new Label("Edit category " + cat.getCatId()));
        layout.setBody(mainVBox);
        layout.setActions(saveButton, cancelButton);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(true);
        alert.setContent(layout);
        alert.showAndWait();
    }
}
