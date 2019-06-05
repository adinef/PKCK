package pl.lodz.p.it.pkck.XML.parsing;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

public abstract class XmlDataMapper<T> {

    protected abstract Class<T> objectClass();

    public T readData(File file) throws Exception {
        Persister serializer = new Persister();
        T read = serializer.read(objectClass(), file);
        return read;
    }

    public T readData(String content) throws Exception {
        Persister serializer = new Persister();
        T read = serializer.read(objectClass(), content);
        return read;
    }
}
