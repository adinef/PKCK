package pl.lodz.p.it.pkck.XML.parsing;

import pl.lodz.p.it.pkck.XML.model.FilmDatabase;

public class FilmDatabaseDataMapper extends XmlDataMapper<FilmDatabase> {
    @Override
    protected Class<FilmDatabase> objectClass() {
        return FilmDatabase.class;
    }
}
