package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.simpleframework.xml.Attribute;
import pl.lodz.p.it.pkck.XML.TableName;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("Actors")
public class Actor extends Person {

    @Attribute(required = false, name = "leadId")
    private String leadId;

    @Attribute(required = false, name = "leadRefId")
    private String leadRefId;

    @Override
    public String toString() {
        return getName() + " " + getLastName();
    }
}
