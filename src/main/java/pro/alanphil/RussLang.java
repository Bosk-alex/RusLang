package pro.alanphil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.*;

import static pro.alanphil.Colors.*;
import static pro.alanphil.FilesToOrFromLists.*;
import static pro.alanphil.VerbsTasks.*;

/**
 * Hello world!
 */
class RussLang {

    static final Properties properties = new Properties();
    static final String PREFIX_ANSI = "\u001B[";
    static final String POSTFIX = PREFIX_ANSI + "0m";
    static final Logger logger = Logger.getLogger("Common");
    static final String STRING_SEPARATOR = "\n";

    public static void main(String[] args) throws IOException {
        logger.config(() -> colorMessage("New color", BLUE));
        logger.info(() -> colorAllMassages("Change color of Error massage", BLUE));
        properties.load(new FileInputStream("src/main/resources/RusLang.properties"));
        Map<String, List<List<String>>> wordGroups = createMapOfLists();

        verbTasks(wordGroups.get("Verbs"));

        nounTasks(wordGroups.get("Nouns"));
    }

    private static void nounTasks(List<List<String>> wordGroups) throws IOException {
        saveListsToFile(wordGroups, properties.getProperty("outputNounGroups"));
    }

    private static Map<String, List<List<String>>> createMapOfLists() throws IOException {
        Map<String, List<List<String>>> wordGroups = new HashMap<>();
        for (Object key : properties.keySet()) {
            if (!key.toString().contains("path")) continue;
            List<String> file = getListFromFile(properties.getProperty(String.valueOf(key)),
                    properties.getProperty("code"));
            List<Integer> indexes = getListOfEmptyIndexes(file);
            List<List<String>> groups = getGroupsFromFile(indexes, file);
            wordGroups.put(key.toString().substring("pathTo".length()), groups);
        }
        return wordGroups;
    }

    private static void verbTasks(List<List<String>> wordGroups) throws IOException {
        List<List<String>> verbGroups = new ArrayList<>();

        List<List<String>> finalVerbGroups = verbGroups;
        wordGroups.forEach(list -> finalVerbGroups.add(removeRepeats(list)));
        saveSetToFileAndClear("outputVerbStringRepeat");

        verbGroups = checkParticipleList(verbGroups);

        verbGroups.forEach(list -> checkSubstringRepeats(list, GP));
        saveSetToFileAndClear("outputVerbGPRepeat");

        verbGroups.forEach(list -> checkSubstringRepeats(list, DN));
        saveSetToFileAndClear("outputVerbDNRepeat");

        verbGroups.forEach(list -> checkSubstringRepeats(list, DP));
        saveSetToFileAndClear("outputVerbDPRepeat");

        FilesToOrFromLists.saveListsToFile(verbGroups, properties.getProperty("outputVerbGroups"));
    }

}
