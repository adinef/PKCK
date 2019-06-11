package pl.lodz.p.it.pkck.XML.repository;

import pl.lodz.p.it.pkck.XML.model.FilmDatabase;

import java.io.File;
import java.util.Optional;

public class FilmDatabaseCachingRepository extends DataRepository<FilmDatabase> {
    private FilmDatabase data;
    private String lastFileLoaded = "";

    public FilmDatabaseCachingRepository() {
        super(FilmDatabase.class);
    }

    @Override
    public Optional<FilmDatabase> load(File file) throws Exception {
        if (this.data == null || !file.getAbsolutePath().equals(lastFileLoaded)) {
            this.data = super.load(file).orElseThrow();
            this.lastFileLoaded = file.getAbsolutePath();
        }
        return Optional.ofNullable(this.data);
    }

    public void removeCache() {
        if (this.data != null) {
            this.data = null;
            this.lastFileLoaded = "";
        }
    }

    public Optional<FilmDatabase> getCached() {
        return Optional.ofNullable(this.data);
    }
}
