package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import pl.lodz.p.it.pkck.XML.TableColumnCell;
import pl.lodz.p.it.pkck.XML.TableName;
import pl.lodz.p.it.pkck.XML.parsing.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
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
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthDate;
}
