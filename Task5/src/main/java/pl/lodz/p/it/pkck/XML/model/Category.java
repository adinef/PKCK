package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

@Data
public class Category {
    @Attribute(name = "catId", required = false)
    private String catId;

    @Attribute(name = "catRefId", required = false)
    private String catRefId;

    @Text
    private String name;
}
