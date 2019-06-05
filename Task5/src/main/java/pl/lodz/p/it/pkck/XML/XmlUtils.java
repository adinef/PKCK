package pl.lodz.p.it.pkck.XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class XmlUtils {
    public static String trimWithingTags(String content) {
        return content.replaceAll("(?<=[>])(\\s*)|(\\s*)(?=[<])", "");
    }

    public static String readFileContent(File file) throws IOException {
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader)
                ) {
            return bufferedReader.lines().collect(Collectors.joining());
        }
    }
}
