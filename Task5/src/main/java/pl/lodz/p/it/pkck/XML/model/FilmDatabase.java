package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "FilmDatabase", strict = false)
@Data
public class FilmDatabase {

    @Path("Head")
    @Element(name = "Author")
    private String author;

    @Path("Head")
    @Element(name = "EMail")
    private String email;

    @Path("Head")
    @Element(name = "Name")
    private String name;

    @ElementList(name = "Categories", entry = "Category")
    private List<Category> categories;

    @ElementList(name = "Actors", entry = "Lead")
    private List<Actor> actors;

    @ElementList(name = "Directors", entry = "Director")
    private List<Director> directors;

    @ElementList(name = "Films", entry = "Film")
    private List<Film> films;
}
