package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;
import pl.lodz.p.it.pkck.annotations.Column;

@Data
public class Category {

    @Column("id")
    @Attribute(name = "catId", required = false)
    private String catId;

    @Column("refId")
    @Attribute(name = "catRefId", required = false)
    private String catRefId;

    @Text
    @Column("Name")
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
