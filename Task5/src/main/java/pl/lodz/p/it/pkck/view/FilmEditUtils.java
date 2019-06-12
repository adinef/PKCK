package pl.lodz.p.it.pkck.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import pl.lodz.p.it.pkck.XML.IdGenerator;
import pl.lodz.p.it.pkck.XML.model.*;

import java.util.List;
import java.util.function.Supplier;

public class FilmEditUtils {


    public static void addFilmMenu(FilmDatabase filmDatabase) {
        modifyFilmMenu(() -> filmDatabase.getFilms(), filmDatabase, "Edit film ");
    }

    public static void editFilmMenu(Film film, FilmDatabase filmDatabase) {
        modifyFilmMenu(() -> film, filmDatabase, "New film ");
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

        EditViewTriplet editViewTriplet = new EditViewTriplet(caption + film.getFilmId(), 700);

        // NAME
        Label nameLabel = new Label("Name");
        TextField nameTextField = new TextField(film.getName());

        // AVG SCORE
        Label avgScoreLabel = new Label("Average score");
        TextField avgScoreTextField = new TextField(
                (film.getAvgScore() != null? film.getAvgScore().toString() : "")
        );

        // RELEASE YEAR
        Label releaseYearLabel = new Label("Release year");
        TextField releaseYearTextField = new TextField(
                (film.getReleaseYear() != null? film.getReleaseYear().toString() : "")
        );

        // DESCRIPTION
        Label descriptionLabel = new Label("Description");
        TextField descriptionTextField = new TextField(film.getDescription());

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
            actorCheckbox.getSelectionModel().select(film.getDirector());
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
                    public Category fromString(String s) {
                        Category category = null;
                        for (Category knownCat : filmDatabase.getCategories()) {
                            if (knownCat.getName().equals(s)) {
                                category = knownCat;
                                break;
                            }
                        }
                        if (category == null) {
                            category = createNewCategory(s, filmDatabase.getCategories());
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
                    filmDatabase.getFilms().add(film);
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
        editViewTriplet.mainVBox.getChildren().addAll(
                nameLabel,
                nameTextField,
                descriptionLabel,
                descriptionTextField,
                releaseYearLabel,
                releaseYearTextField,
                avgScoreLabel,
                avgScoreTextField,
                actorLabel,
                actorCheckbox,
                directorLabel,
                directorCheckbox,
                categoriesLabel,
                categoryChips);

        editViewTriplet.layout.setActions(saveButton, cancelButton);
        editViewTriplet.alert.showAndWait();
    }

    private static Category createNewCategory(String s, List<Category> categories) {
        Category category = new Category();
        category.setName(s);
        category.setCatId(IdGenerator.generateFrom(categories));
        categories.add(category);
        return category;
    }

}
