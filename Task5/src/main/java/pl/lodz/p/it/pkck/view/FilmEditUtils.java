package pl.lodz.p.it.pkck.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import pl.lodz.p.it.pkck.XML.IdGenerator;
import pl.lodz.p.it.pkck.XML.model.*;

import java.util.List;
import java.util.function.Supplier;

public class FilmEditUtils {

    private final static String DIGIT_PATTERN = "\\d*";

    public static void addFilmMenu(FilmDatabase filmDatabase) {
        modifyFilmMenu(filmDatabase::getFilms, filmDatabase, "New film ");
    }

    public static void editFilmMenu(Film film, FilmDatabase filmDatabase) {
        modifyFilmMenu(() -> film, filmDatabase, "Edit film ");
    }

    private static <T> void modifyFilmMenu(Supplier<T> supplier, FilmDatabase filmDatabase, String caption) {
        T elem = supplier.get();
        Film film;
        List<Film> wholeData = null;
        if (elem instanceof Film) {
            film = (Film) elem;
        } else if (elem instanceof List) {
            wholeData = (List<Film>)elem;
            film = new Film();
            film.setFilmId(IdGenerator.generateFrom(wholeData));
        } else {
            throw new RuntimeException("WRONG INPUT DATA");
        }

        EditViewTriplet editViewTriplet = new EditViewTriplet(caption + film.getFilmId(), 450);

        // NAME
        Label nameLabel = new Label("Name");
        TextField nameTextField = new TextField(film.getName());

        // AVG SCORE
        Label avgScoreLabel = new Label("Average score");
        TextField avgScoreTextField = new TextField(
                (film.getAvgScore() != null? film.getAvgScore().toString() : "")
        );
        avgScoreTextField.textProperty().addListener(
                (o, oV, nV) -> {
                    if (nV == null || nV.isEmpty()) {
                        return;
                    }
                    Double val;
                    try {
                        val = Double.parseDouble(nV);
                    } catch (Exception e) {
                        val = -1d;
                    }
                    if (val > 10 || val < 0) {
                        avgScoreTextField.setText(oV);
                    }
                }
        );

        // RELEASE YEAR
        Label releaseYearLabel = new Label("Release year");
        TextField releaseYearTextField = new TextField(
                (film.getReleaseYear() != null? film.getReleaseYear().toString() : "")
        );
        releaseYearTextField.textProperty().addListener(
                (o, oV, nV) -> {
                    if (nV == null || nV.isEmpty()) {
                        return;
                    }
                    if (!nV.matches(DIGIT_PATTERN)) {
                        releaseYearTextField.setText(oV);
                    }
                }
        );

        // DESCRIPTION
        Label descriptionLabel = new Label("Description");
        TextArea descriptionTextField = new TextArea(film.getDescription());
        descriptionTextField.setWrapText(true);
        descriptionTextField.setPrefRowCount(5);


        // LEAD
        Label actorLabel = new Label("Lead actor");
        JFXComboBox actorCheckbox = new JFXComboBox();
        actorCheckbox.getItems().addAll(filmDatabase.getActors());
        if (film.getLead() != null) {
            actorCheckbox.getSelectionModel().select(film.getLead());
        }


        // DIRECTOR
        Label directorLabel = new Label("Director");
        JFXComboBox directorCheckbox = new JFXComboBox();
        directorCheckbox.getItems().addAll(filmDatabase.getDirectors());
        if (film.getDirector() != null) {
            directorCheckbox.getSelectionModel().select(film.getDirector());
        }

        Label categoriesLabel = new Label("Categories");
        JFXChipView<Category> categoryChips = new JFXChipView<>();
        if (film.getCategoryList() != null) {
            categoryChips.getChips().addAll(film.getCategoryList());
        }
        categoryChips.getSuggestions().addAll(filmDatabase.getCategories());

        categoryChips.setConverter(
                new StringConverter<>() {
                    @Override
                    public String toString(Category category) {
                        return category.toString();
                    }

                    @Override
                    public Category fromString(String catName) {
                        Category category = null;
                        for (Category knownCat : filmDatabase.getCategories()) {
                            if (knownCat.getName().equals(catName)) {
                                category = knownCat;
                                break;
                            }
                        }
                        if (category == null) {
                            category = createNewCategory(catName, filmDatabase.getCategories());
                        }
                        return category;
                    }
                }
        );

        JFXButton saveButton = new JFXButton("Save");
        JFXButton cancelButton = new JFXButton("Cancel");

        List<Film> finalWholeData = wholeData;
        saveButton.setOnAction(
                (e) -> {
                    film.setName(nameTextField.getText());
                    film.setAvgScore(Double.valueOf(avgScoreTextField.getText()));
                    film.setLead((Actor) actorCheckbox.getSelectionModel().getSelectedItem());
                    film.setDirector((Director) directorCheckbox.getSelectionModel().getSelectedItem());
                    film.setReleaseYear(Integer.parseInt(releaseYearTextField.getText()));
                    film.setDescription(descriptionTextField.getText());
                    film.setCategoryList(categoryChips.getChips());
                    if (finalWholeData != null) {
                        finalWholeData.add(film);
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

        HBox nameHbox = getHBoxWith(nameLabel, nameTextField);
        HBox ryHBox = getHBoxWith(releaseYearLabel, releaseYearTextField);
        HBox avgScoreHBox = getHBoxWith(avgScoreLabel, avgScoreTextField);
        HBox actHBox = getHBoxWith(actorLabel, actorCheckbox);
        HBox dirHBox = getHBoxWith(directorLabel, directorCheckbox);

        editViewTriplet.mainVBox.getChildren().addAll(
                nameHbox,
                descriptionLabel,
                descriptionTextField,
                ryHBox,
                avgScoreHBox,
                actHBox,
                dirHBox,
                categoriesLabel,
                categoryChips);

        editViewTriplet.layout.setActions(saveButton, cancelButton);
        editViewTriplet.alert.showAndWait();
    }

    private static HBox getHBoxWith(Node... nodes) {
        HBox hbox = new HBox();
        hbox.setSpacing(20);
        hbox.getChildren().addAll(nodes);
        return hbox;
    }

    private static Category createNewCategory(String s, List<Category> categories) {
        Category category = new Category();
        category.setName(s);
        category.setCatId(IdGenerator.generateFrom(categories));
        categories.add(category);
        return category;
    }
}
