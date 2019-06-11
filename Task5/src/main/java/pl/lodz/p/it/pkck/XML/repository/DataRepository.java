package pl.lodz.p.it.pkck.XML.repository;

import pl.lodz.p.it.pkck.XML.parsing.DataMapperFactory;
import pl.lodz.p.it.pkck.XML.parsing.XmlDataMapper;

import java.io.File;
import java.util.Optional;

public class DataRepository <T>{
    private final Class<T> elemClass;
    private XmlDataMapper<T> dataMapper;

    public DataRepository(Class<T> elemClass) {
        this.elemClass = elemClass;
    }

    public Optional<T> load(File file) throws Exception {
        if (file != null) {
            XmlDataMapper<T> forClass = getDataMapper();
            return Optional.ofNullable(forClass.readData(file));
        }
        return Optional.empty();
    }


    public void save(File file, T elem) throws Exception {
        if (file != null) {
            XmlDataMapper<T> forClass = getDataMapper();
            forClass.saveData(elem, file);
        }
    }

    private XmlDataMapper<T> getDataMapper() {
        if (this.dataMapper == null) {
            this.dataMapper = DataMapperFactory.getForClass(elemClass);
        }
        return this.dataMapper;
    }
}
