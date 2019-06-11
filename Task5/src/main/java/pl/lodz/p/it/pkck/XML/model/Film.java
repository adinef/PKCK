package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import pl.lodz.p.it.pkck.annotations.Column;

import java.util.List;

@Data
public class Film {


    @Column("id")
    @Attribute(name = "filmId", required = false)
    private String filmId;


    @Column("Name")
    @Element(name = "Name")
    private String name;


    @Column("Average score")
    @Element(name = "AvgScore")
    private Double avgScore;


    @Column("Categories")
    @ElementList(name = "Categories", entry = "Category")
    private List<Category> categoryList;


    @Column("Description")
    @Element(name = "Description")
    private String description;


    @Column("Release year")
    @Element(name = "ReleaseYear")
    private Integer releaseYear;


    @Column("Lead")
    @Element(name = "Lead")
    private Actor lead;


    @Column("Director")
    @Element(name = "Director")
    private Director director;


    @Override
    public String toString() {
        return name;
    }
}
