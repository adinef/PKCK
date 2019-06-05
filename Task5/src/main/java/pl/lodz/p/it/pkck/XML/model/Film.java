package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

@Data
public class Film {

    @Attribute(name = "filmId", required = false)
    private String filmId;

    @Element(name = "Name")
    private String name;

    @Element(name = "AvgScore")
    private Double avgScore;

    @ElementList(name = "Categories", entry = "Category")
    private List<Category> categoryList;

    @Element(name = "Description")
    private String description;

    @Element(name = "ReleaseYear")
    private Integer releaseYear;

    @Element(name = "Lead")
    private Actor lead;

    @Element(name = "Director")
    private Director director;
}
