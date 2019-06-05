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

    @ElementList(name = "Films", entry = "Film")
    private List<Film> films;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
