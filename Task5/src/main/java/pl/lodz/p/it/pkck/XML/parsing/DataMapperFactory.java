package pl.lodz.p.it.pkck.XML.parsing;

import pl.lodz.p.it.pkck.XML.model.FilmDatabase;

public class DataMapperFactory {
    public static <T> XmlDataMapper<T> getForClass(Class<T> clazz) {
        if (clazz.equals(FilmDatabase.class)) {
            return (XmlDataMapper<T>) new FilmDatabaseDataMapper();
        }
        return null;
    }
}
