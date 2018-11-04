package pro.alanphil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.logging.*;

/**
 * Hello world!
 *
 */
public class RussLang {
    private static final Logger logger = Logger.getLogger("Common");
    private static final Properties properties = new Properties();

    public static void main(String[] args) throws IOException {
        properties.load(new FileInputStream("src/main/resources/RusLang.properties"));

        for (Object key : properties.keySet()) {
            if (!key.toString().contains("path")) continue;
            List<String> file = getListFromFile(properties.getProperty(String.valueOf(key)),properties.getProperty("code"));
            List<Integer> indexes = getListOfEmptyIndexes(file);
            List<List<String>> groups = getGroupsFromFile(indexes, file);
            groups.forEach(list -> logger.log(Level.INFO, list::toString));
        }
    }

    private static List<String> getListFromFile(String fileName, String codeName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), Charset.forName(codeName));
    }

    private static List<Integer> getListOfEmptyIndexes(List<String> file) {
        List<Integer> indexes = new ArrayList<>();
        for (int index = 0; index < file.size(); index++) {
            if (file.get(index).isEmpty()) indexes.add(index);
        }
        return indexes;
    }

    private static List<List<String>> getGroupsFromFile(List<Integer> indexes, List<String> file) {
        List<List<String>> groups = new ArrayList<>(indexes.size() + 1);
        List<String> group = new ArrayList<>();

        int currentFileIndex = 0;
        for (Integer index : indexes) {
            for (int fileIndex = currentFileIndex; fileIndex <= index; currentFileIndex = ++fileIndex) {
                if (fileIndex == index) continue;
                group.add(file.get(fileIndex));
            }
            groups.add(group);
            group = new ArrayList<>();
        }
        for (int fileIndex = currentFileIndex; fileIndex < file.size(); fileIndex++) {
            group.add(file.get(fileIndex));
        }
        groups.add(group);
        logger.log(Level.INFO, () -> String.valueOf(groups.size()));
        return groups;
    }
}