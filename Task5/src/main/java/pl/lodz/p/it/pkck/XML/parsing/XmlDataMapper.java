package pl.lodz.p.it.pkck.XML.parsing;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.CycleStrategy;
import pl.lodz.p.it.pkck.XML.XmlUtils;

import java.io.File;

public abstract class XmlDataMapper<T> {

    protected abstract Class<T> objectClass();

    public T readData(File file) throws Exception {
        String content = XmlUtils.readFileContent(file);
        content = XmlUtils.trimWithingTags(content);
        return this.readData(content);
    }

    private T readData(String content) throws Exception {
        Persister serializer = new Persister();
        CycleStrategy cycleStrategy = new CycleStrategy()
        T read = serializer.read(objectClass(), content);
        return read;
    }

    public void saveData(T elem, File file) throws Exception {
        Persister persister = new Persister();
        persister.write(elem, file);
    }
}
