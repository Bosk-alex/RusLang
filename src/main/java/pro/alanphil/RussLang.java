package pro.alanphil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.logging.*;

import static pro.alanphil.Colors.*;

/**
 * Hello world!
 *
 */
public class RussLang {
    static final String PREFIX_ANSI = "\u001B[";
    static final String POSTFIX = PREFIX_ANSI + "0m";
    private static final Logger logger = Logger.getLogger("Common");
    private static final Properties properties = new Properties();

    public static void main(String[] args) throws IOException {
        System.err.println(colorAllMassages("Change color of Error massage", CYAN));
        properties.load(new FileInputStream("src/main/resources/RusLang.properties"));
        Map<String, List<List<String>>> wordGroups = new HashMap<>();

        for (Object key : properties.keySet()) {
            if (!key.toString().contains("path")) continue;
            List<String> file = getListFromFile(properties.getProperty(String.valueOf(key)),properties.getProperty("code"));
            List<Integer> indexes = getListOfEmptyIndexes(file);
            List<List<String>> groups = getGroupsFromFile(indexes, file);
            wordGroups.put(key.toString().substring("pathTo".length()), groups);
            logger.info(() -> key.toString().substring("pathTo".length()) + " " + groups.size());
        }

        logger.info(wordGroups.toString());
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
        return groups;
    }
}