package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lodz.p.it.pkck.XML.TableName;

import javax.xml.bind.annotation.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("Actors")
@XmlAccessorType(XmlAccessType.FIELD)
public class Actor extends Person {

    @XmlID
    @XmlAttribute(name = "id")
    private String leadId;

    @Override
    public String toString() {
        return getName() + " " + getLastName();
    }
}
