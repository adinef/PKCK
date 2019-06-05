package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.simpleframework.xml.Attribute;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Director extends Person {

    @Attribute(required = false, name = "directorId")
    private String directorId;

    @Attribute(required = false, name = "directorRefId")
    private String directorRefId;
}
