package pro.alanphil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static pro.alanphil.VerbsTasks.groupsWithRepeats;

class FilesToOrFromLists {

    private FilesToOrFromLists() {
        throw new IllegalStateException("Utility class");
    }

    static List<String> getListFromFile(String fileName, String codeName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), Charset.forName(codeName));
    }

    static List<Integer> getListOfEmptyIndexes(List<String> file) {
        List<Integer> indexes = new ArrayList<>();
        for (int index = 0; index < file.size(); index++) {
            if (file.get(index).isEmpty()) indexes.add(index);
        }
        return indexes;
    }

    static List<List<String>> getGroupsFromFile(List<Integer> indexes, List<String> file) {
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

    static void saveListsToFile(List<List<String>> verbGroups, String pathName) throws IOException {
        String output = makeOutputStringFromLists(verbGroups);
        Files.writeString(Paths.get(pathName), output, StandardCharsets.UTF_16);
    }

    private static String makeOutputStringFromLists(List<List<String>> list) {
        StringBuilder builder = new StringBuilder();
        for (List<String> group : list) {
            builder.append(makeOutputStringFromList(group)).append(RussLang.STRING_SEPARATOR);
        }
        return builder.toString();
    }

    private static void saveListToFile(List<String> group, String pathName) throws IOException {
        String output = makeOutputStringFromList(group);
        Files.writeString(Paths.get(pathName), output, StandardCharsets.UTF_16);
    }

    private static String makeOutputStringFromList(List<String> group) {
        StringBuilder builder = new StringBuilder();
        for (String word : group) {
            builder.append(word).append(RussLang.STRING_SEPARATOR);
        }
        return builder.toString();
    }

    static void saveSetToFileAndClear(String outputPath) throws IOException {
        saveListToFile(new ArrayList<>(groupsWithRepeats), RussLang.properties.getProperty(outputPath));
        groupsWithRepeats.clear();
    }
}
