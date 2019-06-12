package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import org.simpleframework.xml.Element;
import pl.lodz.p.it.pkck.XML.TableColumnCell;
import pl.lodz.p.it.pkck.XML.TableName;

import java.util.Date;

@Data
@TableName("People")
public class Person {

    @TableColumnCell("Name")
    @Element(name = "Name")
    private String name;

    @TableColumnCell("Last name")
    @Element(name = "LastName")
    private String lastName;

    @TableColumnCell("Birth date")
    @Element(name = "BirthDate")
    private Date birthDate;
}
