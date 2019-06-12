package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;
import pl.lodz.p.it.pkck.XML.TableColumnCell;
import pl.lodz.p.it.pkck.XML.TableName;

@Data
@TableName("Categories")
public class Category {

    @Attribute(name = "catId", required = false)
    private String catId;

    @Attribute(name = "catRefId", required = false)
    private String catRefId;

    @TableColumnCell("Name")
    @Text
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
