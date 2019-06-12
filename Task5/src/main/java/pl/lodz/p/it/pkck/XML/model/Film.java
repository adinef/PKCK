package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import pl.lodz.p.it.pkck.XML.TableColumnCell;
import pl.lodz.p.it.pkck.XML.TableName;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@TableName("Films")
@XmlAccessorType(XmlAccessType.FIELD)
public class Film {

    @XmlID
    @XmlAttribute(name = "id")
    private String filmId;

    @TableColumnCell("Name")
    @XmlElement(name = "Name")
    private String name;

    @TableColumnCell("Avg score")
    @XmlElement(name = "AvgScore")
    private Double avgScore;

    @TableColumnCell("Categories")
    @XmlElementWrapper(name = "Categories")
    @XmlElement(name = "Category")
    @XmlIDREF
    private List<Category> categoryList;

    @TableColumnCell("Description")
    @XmlElement(name = "Description")
    private String description;

    @TableColumnCell("Release year")
    @XmlElement(name = "ReleaseYear")
    private Integer releaseYear;

    @TableColumnCell("Lead")
    @XmlAttribute(name = "leadId")
    @XmlIDREF
    private Actor lead;

    @TableColumnCell("Director")
    @XmlAttribute(name = "directorId")
    @XmlIDREF
    private Director director;

    @Override
    public String toString() {
        return name;
    }
}
