package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import org.simpleframework.xml.Element;

import java.util.Date;

@Data
public class Person {
    @Element(name = "Name")
    private String name;

    @Element(name = "LastName")
    private String lastName;

    @Element(name = "BirthDate")
    private Date birthDate;
}
