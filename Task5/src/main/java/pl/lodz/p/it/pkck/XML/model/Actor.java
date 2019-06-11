package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.simpleframework.xml.Attribute;
import pl.lodz.p.it.pkck.annotations.Column;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Actor extends Person {

    @Column("id")
    @Attribute(required = false, name = "leadId")
    private String leadId;

    @Column("refId")
    @Attribute(required = false, name = "leadRefId")
    private String leadRefId;


    @Override
    public String toString() {
        return getName() + " " + getLastName();
    }
}
