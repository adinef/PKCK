package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "FilmDatabase")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class FilmDatabase {

    @XmlElement(name = "Head")
    private Head head = new Head();

    @XmlElementWrapper(name = "Categories")
    @XmlElement(name = "Category")
    private List<Category> categories;

    @XmlElementWrapper(name = "Actors")
    @XmlElement(name = "Lead")
    private List<Actor> actors;

    @XmlElementWrapper(name = "Directors")
    @XmlElement(name = "Director")
    private List<Director> directors;

    @XmlElementWrapper(name = "Films")
    @XmlElement(name = "Film")
    private List<Film> films;


    public void setName(String name) {
        this.head.name = name;
    }

    public void setAuthor(String author) {
        this.head.author = author;
    }

    public void setEmail(String email) {
        this.head.email = email;
    }

    public String getName() {
        return this.head.name;
    }

    public String getAuthor() {
        return this.head.author;
    }

    public String getEmail() {
        return this.head.email;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @ToString
    public static class Head {
        @XmlElement(name = "Author")
        private String author;

        @XmlElement(name = "EMail")
        private String email;

        @XmlElement(name = "Name")
        private String name;
    }
}
