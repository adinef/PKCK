package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lodz.p.it.pkck.XML.TableName;

import javax.xml.bind.annotation.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("Directors")
@XmlAccessorType(XmlAccessType.FIELD)
public class Director extends Person {

    @XmlID
    @XmlAttribute(name = "id")
    private String directorId;

    @Override
    public String toString() {
        return getName() + " " + getLastName();
    }
}
