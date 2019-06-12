package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import pl.lodz.p.it.pkck.XML.TableColumnCell;
import pl.lodz.p.it.pkck.XML.TableName;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@Data
@TableName("People")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    @TableColumnCell("Name")
    @XmlElement(name = "Name")
    private String name;

    @TableColumnCell("Last name")
    @XmlElement(name = "LastName")
    private String lastName;

    @TableColumnCell("Birth date")
    @XmlElement(name = "BirthDate")
    private Date birthDate;
}
