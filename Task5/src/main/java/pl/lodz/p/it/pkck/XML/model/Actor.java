package pl.lodz.p.it.pkck.XML.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.simpleframework.xml.Attribute;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Actor extends Person {

    @Attribute(required = false, name = "leadId")
    private String leadId;

    @Attribute(required = false, name = "leadRefId")
    private String leadRefId;
}
