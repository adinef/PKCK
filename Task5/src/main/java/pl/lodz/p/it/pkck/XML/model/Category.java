package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import pl.lodz.p.it.pkck.XML.TableColumnCell;
import pl.lodz.p.it.pkck.XML.TableName;

import javax.xml.bind.annotation.*;

@Data
@TableName("Categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class Category {

    @XmlID
    @XmlAttribute(name = "id")
    private String catId;

    @TableColumnCell("Name")
    @XmlValue
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
