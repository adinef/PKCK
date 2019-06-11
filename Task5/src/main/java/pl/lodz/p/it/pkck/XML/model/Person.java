package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import org.simpleframework.xml.Element;
import pl.lodz.p.it.pkck.annotations.Column;

import java.util.Date;

@Data
public class Person {

    @Column("Name")
    @Element(name = "Name")
    private String name;


    @Column("Last name")
    @Element(name = "LastName")
    private String lastName;


    @Column("Birth date")
    @Element(name = "BirthDate")
    private Date birthDate;
}
