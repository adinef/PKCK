package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.simpleframework.xml.Attribute;
import pl.lodz.p.it.pkck.annotations.Column;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Director extends Person {

    @Column("id")
    @Attribute(required = false, name = "directorId")
    private String directorId;

    @Column("refId")
    @Attribute(required = false, name = "directorRefId")
    private String directorRefId;


    @Override
    public String toString() {
        return getName() + " " + getLastName();
    }
}
