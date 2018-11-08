package pro.alanphil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pro.alanphil.FilesToOrFromLists.saveListToFile;
import static pro.alanphil.RussLang.logger;
import static pro.alanphil.RussLang.properties;


class AdjectiveTasks {

    private static final String PZT1 = "ПжТ1";
    private static final String PZT2 = "ПжТ2";
    private static final String PMV1 = "ПмВ1";
    private static final String PMV2 = "ПмВ2";
    private static final String PMNV1 = "ПмнВ1";
    private static final String PMNV2 = "ПмнВ2";

    private AdjectiveTasks() {
        logger.warning("Something happen");
        throw new IllegalStateException("Utility class");
    }


    @SafeVarargs
    static void saveAdjListsToFiles(List<List<String>> ... adjectives) throws IOException {
        int index = 1;
        for (List<List<String>> adjGroup : adjectives) {
            saveListsToFiles(adjGroup, "outputAdjRule");
            index++;
        }
    }

    private static void saveListsToFiles(List<List<String>> lists, String propPath) throws IOException {
        for (int index = 0; index < lists.size(); index++) {
            String suffix = String.valueOf(index + 1);
            saveListToFile(lists.get(index), properties.getProperty(propPath + suffix));
        }
    }

    static List<List<String>> checkAdjRules(List<List<String>> adjectiveGroups) {
        List<List<String>> noRuleGroups = new ArrayList<>();

        List<String> wrongGroup = checkRule(adjectiveGroups, PZT1, PZT2);
        noRuleGroups.add(wrongGroup);

        wrongGroup = checkRule(adjectiveGroups, PMV1, PMV2);
        noRuleGroups.add(wrongGroup);

        wrongGroup = checkRule(adjectiveGroups, PMNV1, PMNV2);
        noRuleGroups.add(wrongGroup);

        logger.info(() -> String.valueOf(noRuleGroups.size()));

        return noRuleGroups;
    }

    private static List<String> checkRule(List<List<String>> adjectiveGroups, String firstType, String secondType) {
        List<String> noRule1 = new ArrayList<>();

        for (List<String> group : adjectiveGroups) {
            boolean type1 = checkType(firstType, group);
            boolean type2 = checkType(secondType, group);

            if (!type1 && !type2) {
                noRule1.add(group.get(0));
            }
        }

        return noRule1;
    }

    private static boolean checkType(String type, List<String> group) {
        boolean containsType = false;
        for (String word : group) {
            if (word.contains(type)) {
                containsType = true;
                break;
            }
        }
        return containsType;
    }
}
