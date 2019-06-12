package pl.lodz.p.it.pkck.view;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.lodz.p.it.pkck.Main;
import pl.lodz.p.it.pkck.XML.IdGenerator;
import pl.lodz.p.it.pkck.XML.model.Actor;
import pl.lodz.p.it.pkck.XML.model.Category;
import pl.lodz.p.it.pkck.XML.model.Director;
import pl.lodz.p.it.pkck.XML.model.Person;
import pl.lodz.p.it.pkck.view.common.FunctionWithException;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class FXUtils {

    @Getter
    @AllArgsConstructor
    public static class ElemFileTuple<T> {
        T elem;
        File file;
    }

    public static <T> Optional<ElemFileTuple<T>> openAndReadXml(FunctionWithException<File, T> fileHandler) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Plik .xml", "xml"));
        File file = fileChooser.showOpenDialog(Main.getMainStage());
        if (file != null) {
            return Optional.of(new ElemFileTuple<>(fileHandler.apply(file), file));
        }
        return Optional.empty();
    }

    public static void editMenuFor(Object object) {
        if (object instanceof Category) {
            editMenuForCategory((Category) object);
        } else if (object instanceof Person) {
            editMenuForPerson((Person)object);
        }
    }

    public static void addMenuFor(Class clazz, List<?> allEntities) {
        if (Category.class.equals(clazz)) {
            addMenuForCategory((List<Category>)allEntities);
        } else if (Person.class.isAssignableFrom(clazz)) {
            addMenuForPerson((List<Person>) allEntities, clazz);
        }
    }

    private static void addMenuForCategory(List<Category> allEntities) {
        modifyMenuForCategory(() -> allEntities, "Add category ");
    }


    private static void editMenuForCategory(Category cat) {
        modifyMenuForCategory(() -> cat, "Edit category ");
    }

    private static <T> void modifyMenuForCategory(Supplier<T> dataSupplier, String caption) {
        T elem = dataSupplier.get();
        Category cat;
        List<Category> wholeData = null;
        if (elem instanceof Category) {
            cat = (Category) elem;
        } else if (elem instanceof List) {
            wholeData = (List<Category>)elem;
            cat = new Category();
            cat.setCatId(IdGenerator.generateFrom(wholeData));
        } else {
            throw new RuntimeException("WRONG INPUT DATA");
        }
        EditViewTriplet editViewTriplet = new EditViewTriplet(caption + cat.getCatId(), 200);
        Label nameLabel = new Label("Name");
        TextField nameTextField = new TextField(cat.getName());
        JFXButton saveButton = new JFXButton("Save");
        JFXButton cancelButton = new JFXButton("Cancel");

        List<Category> finalWholeDate = wholeData;
        saveButton.setOnAction(
                (e) -> {
                    cat.setName(nameTextField.getText());
                    if (finalWholeDate != null) {
                        finalWholeDate.add(cat);
                    }
                    editViewTriplet.alert.hideWithAnimation();
                    e.consume();
                }
        );

        cancelButton.setOnAction(
                (e) -> {
                    editViewTriplet.alert.hideWithAnimation();
                    e.consume();
                }
        );
        editViewTriplet.mainVBox.getChildren().addAll(nameLabel, nameTextField);
        editViewTriplet.layout.setActions(saveButton, cancelButton);
        editViewTriplet.alert.showAndWait();
    }


    private static void editMenuForPerson(Person person) {
        modifyMenuForPerson(() -> person, null, "Edit person ");
    }

    private static void addMenuForPerson(List<Person> people, Class<? extends Person> pClass) {
        if (Actor.class.equals(pClass)) {
            modifyMenuForPerson(() -> people, new Actor(), "New actor ");
        } else if (Director.class.equals(pClass)) {
            modifyMenuForPerson(() -> people, new Director(), "New director ");
        }
    }

    private static <T, E extends Person> void modifyMenuForPerson(Supplier<T> elemSupplier, E pers, String caption) {
        T elem = elemSupplier.get();
        Person person;
        List<Person> wholeData = null;
        if (elem instanceof Person) {
            person = (Person) elem;
        } else if (elem instanceof List) {
            wholeData = (List<Person>)elem;
            person = pers;
        } else {
            throw new RuntimeException("WRONG INPUT DATA");
        }
        String id = "";
        if (wholeData == null) {
            if (person instanceof Actor) {
                id = ((Actor) person).getLeadId();
            } else if (person instanceof Director) {
                id = ((Director)person).getDirectorId();
            }
        } else {
            id = IdGenerator.generateFrom(wholeData);
        }

        EditViewTriplet editViewTriplet = new EditViewTriplet(caption + id, 300);

        // NAME
        Label nameLabel = new Label("Name");
        TextField nameTextField = new TextField(person.getName());

        // LAST NAME
        Label lastNameLabel = new Label("Last name");
        TextField lastNameTextField = new TextField(person.getLastName());

        // LAST NAME
        Label birthDayLabel = new Label("Birth day");
        JFXDatePicker datePicker = new JFXDatePicker(person.getBirthDate());


        JFXButton saveButton = new JFXButton("Save");
        JFXButton cancelButton = new JFXButton("Cancel");
        List<Person> finalWholeData = wholeData;
        saveButton.setOnAction(
                (e) -> {
                    person.setName(nameTextField.getText());
                    person.setLastName(lastNameTextField.getText());
                    person.setBirthDate(datePicker.getValue());
                    if (finalWholeData != null) {
                        finalWholeData.add(person);
                    }
                    editViewTriplet.alert.hideWithAnimation();
                    e.consume();
                }
        );

        cancelButton.setOnAction(
                (e) -> {
                    editViewTriplet.alert.hideWithAnimation();
                    e.consume();
                }
        );

        editViewTriplet.mainVBox.getChildren().addAll(
                nameLabel,
                nameTextField,
                lastNameLabel,
                lastNameTextField,
                birthDayLabel,
                datePicker);
        editViewTriplet.layout.setActions(saveButton, cancelButton);
        editViewTriplet.alert.showAndWait();
    }

    public static void noDataPopup(String label, String body) {
        JFXAlert alert = new JFXAlert((Stage) Main.getMainStage());
        JFXDialogLayout layout = new JFXDialogLayout();
        JFXButton closeButton = new JFXButton("Zamknij");
        closeButton.setButtonType(JFXButton.ButtonType.FLAT);
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setHeading(new Label(label));
        layout.setBody(new Label(body));
        layout.setActions(closeButton);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(true);
        alert.setContent(layout);
        alert.showAndWait();
    }

    public static boolean yesOrNoPopup(String label, String body) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        JFXAlert alert = new JFXAlert((Stage) Main.getMainStage());
        JFXDialogLayout layout = new JFXDialogLayout();
        JFXButton noButton = new JFXButton("Nie");
        JFXButton yesButton = new JFXButton("Tak");
        yesButton.setDefaultButton(true);
        yesButton.setButtonType(JFXButton.ButtonType.RAISED);
        noButton.setButtonType(JFXButton.ButtonType.FLAT);
        yesButton.setOnAction(event -> {
            atomicBoolean.set(true);
            alert.hideWithAnimation();
            event.consume();
        });
        noButton.setOnAction(event -> {
            alert.hideWithAnimation();
            event.consume();
        });
        layout.setHeading(new Label(label));
        layout.setBody(new Label(body));
        layout.setActions(yesButton, noButton);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(true);
        alert.setContent(layout);
        alert.showAndWait();
        return atomicBoolean.get();
    }
}
