package pl.lodz.p.it.pkck.XML.parsing;

import pl.lodz.p.it.pkck.XML.XmlUtils;
import pl.lodz.p.it.pkck.XML.model.Actor;
import pl.lodz.p.it.pkck.XML.model.Category;
import pl.lodz.p.it.pkck.XML.model.Film;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;

public abstract class XmlDataMapper<T> {

    protected abstract Class<T> objectClass();

    public T readData(File file) throws Exception {
        String content = XmlUtils.readFileContent(file);
        content = XmlUtils.trimWithingTags(content);
        return this.readData(content);
    }

    private T readData(String content) throws Exception {
        //JAXBContext jaxbContext = JAXBContext.newInstance(objectClass(), Film.class, Category.class, Actor.class);
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass());
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        T elem = (T) unmarshaller.unmarshal(new StringReader(content));
        return elem;
    }

    public void saveData(T elem, File file, Class... classes) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(classes);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        marshaller.marshal(elem, fileOutputStream);
    }
}
