package pl.lodz.p.it.pkck.XML;

import javax.xml.bind.annotation.XmlID;
import java.lang.reflect.Field;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class IdGenerator {
    public static String generateFrom(List<?> entities) {
        SortedSet<String> sortedIdSet = new TreeSet<>();
        Field idFIeld = null;
        for (Object ent : entities) {
            if (idFIeld == null) {
                for (Field field : ent.getClass().getDeclaredFields()) {
                    XmlID annotation = field.getAnnotation(XmlID.class);
                    if (annotation != null) {
                        idFIeld = field;
                        idFIeld.setAccessible(true);
                        break;
                    }
                }
                if (idFIeld == null) {
                    return null;
                }
            }
            String idFieldValue;
            try {
                idFieldValue = (String) idFIeld.get(ent);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
            sortedIdSet.add(idFieldValue);
        }
        return extractedFrom(sortedIdSet.last());
    }

    private static String extractedFrom(String last) {
        String bef = last.replaceAll("\\d*", "");
        String after = last.replaceAll(bef, "");
        Integer intAfter = Integer.parseInt(after);
        return bef + ++intAfter;
    }
}
