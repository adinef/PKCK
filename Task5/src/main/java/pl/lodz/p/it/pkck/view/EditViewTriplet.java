package pl.lodz.p.it.pkck.view;


import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.lodz.p.it.pkck.Main;

class EditViewTriplet {

    final JFXAlert alert;
    final VBox mainVBox;
    final JFXDialogLayout layout;

    EditViewTriplet(String label, int minHeight) {
        mainVBox = new VBox();
        mainVBox.setMinHeight(minHeight);
        mainVBox.setSpacing(10);
        alert = new JFXAlert((Stage) Main.getMainStage());
        layout = new JFXDialogLayout();
        layout.setHeading(new Label(label));
        layout.setBody(mainVBox);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        alert.setContent(layout);
    }
}
