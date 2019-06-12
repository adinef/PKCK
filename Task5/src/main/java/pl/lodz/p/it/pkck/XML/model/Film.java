package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import pl.lodz.p.it.pkck.XML.TableColumnCell;
import pl.lodz.p.it.pkck.XML.TableName;

import java.util.List;

@Data
@TableName("Films")
public class Film {

    @Attribute(name = "filmId", required = false)
    private String filmId;

    @TableColumnCell("Name")
    @Element(name = "Name")
    private String name;

    @TableColumnCell("Avg score")
    @Element(name = "AvgScore")
    private Double avgScore;

    @TableColumnCell("Categories")
    @ElementList(name = "Categories", entry = "Category")
    private List<Category> categoryList;

    @TableColumnCell("Description")
    @Element(name = "Description")
    private String description;

    @TableColumnCell("Release year")
    @Element(name = "ReleaseYear")
    private Integer releaseYear;

    @TableColumnCell("Lead")
    @Element(name = "Lead")
    private Actor lead;

    @TableColumnCell("Director")
    @Element(name = "Director")
    private Director director;

    @Override
    public String toString() {
        return name;
    }
}
