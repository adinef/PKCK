package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.simpleframework.xml.Attribute;
import pl.lodz.p.it.pkck.XML.TableName;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("Directors")
public class Director extends Person {

    @Attribute(required = false, name = "directorId")
    private String directorId;

    @Attribute(required = false, name = "directorRefId")
    private String directorRefId;

    @Override
    public String toString() {
        return getName() + " " + getLastName();
    }
}
