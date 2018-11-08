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
    private static final String[] PK = {"ПКмИ", "ПКжИ", "ПКсИ", "ПКмн"};
    private static final String PS1 = "ПС1";
    private static final String[] PS = {"ПС1*", "ПС2*"};
    private static final String PMI = ".ПмИ";
    private static final String PZI = ".ПжИ";
    private static final String PSI = ".ПсИ";
    private static final String PMNI = ".ПмнИ";

    private AdjectiveTasks() {
        logger.warning("Something happen");
        throw new IllegalStateException("Utility class");
    }


    @SafeVarargs
    static void saveAdjListsToFiles(List<List<String>>... adjectives) throws IOException {
        int index = 1;
        for (List<List<String>> adjGroup : adjectives) {
            saveListsToFiles(adjGroup, "outputAdj" + index);
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

        return noRuleGroups;
    }

    private static List<String> checkRule(List<List<String>> adjectiveGroups, String firstType, String secondType) {
        List<String> noRule1 = new ArrayList<>();

        for (List<String> group : adjectiveGroups) {
            boolean type1 = containsType(firstType, group);
            boolean type2 = containsType(secondType, group);

            if (!type1 && !type2) {
                noRule1.add(group.get(0));
            }
        }

        return noRule1;
    }

    private static boolean containsType(String type, List<String> group) {
        boolean containsType = false;
        for (String word : group) {
            if (word.contains(type)) {
                containsType = true;
                break;
            }
        }
        return containsType;
    }

    static List<List<String>> checkAdjRepeats(List<List<String>> adjectiveGroups) {
        List<List<String>> groupsWithRepeats = new ArrayList<>();

        List<String> wrongGroup = checkRepeats(adjectiveGroups, PK);
        groupsWithRepeats.add(wrongGroup);

        wrongGroup = checkRepeats(adjectiveGroups, PS1);
        groupsWithRepeats.add(wrongGroup);

        wrongGroup = checkRepeats(adjectiveGroups, PS);
        groupsWithRepeats.add(wrongGroup);

        return groupsWithRepeats;
    }

    private static List<String> checkRepeats(List<List<String>> adjectiveGroups, String... types) {
        List<String> groupWithRepeats = new ArrayList<>();

        for (List<String> group : adjectiveGroups) {
            int counter = 0;
            counter = countTypes(group, counter, types);
            if (counter == types.length) groupWithRepeats.add(group.get(0));
        }

        return groupWithRepeats;
    }

    private static int countTypes(List<String> group, int counter, String[] types) {
        for (String type : types) {
            int typeIndex = 0;
            for (String word : group) {
                if (word.contains(type)) {
                    typeIndex++;
                }
            }
            if (typeIndex < 2) break;
            else counter++;
        }
        return counter;
    }

    static List<List<String>> checkSpecificAdj(List<List<String>> adjectiveGroups) {
        List<List<String>> specAdjGroups = new ArrayList<>();

        List<String> wrongGroup = checkCapitalType(adjectiveGroups, PMI);
        specAdjGroups.add(wrongGroup);

        wrongGroup = checkTwoTypes(adjectiveGroups, PZI, PSI);
        specAdjGroups.add(wrongGroup);

        wrongGroup = checkType(adjectiveGroups, PMNI);
        specAdjGroups.add(wrongGroup);


        return specAdjGroups;
    }

    private static List<String> checkCapitalType(List<List<String>> adjectiveGroups, @SuppressWarnings("SameParameterValue") String type) {
        List<String> specificCapitalType = new ArrayList<>();

        for (List<String> group : adjectiveGroups) {
            if (!group.get(0).contains(type)) {
                specificCapitalType.add(group.get(0));
            }
        }

        return specificCapitalType;
    }

    private static List<String> checkTwoTypes(List<List<String>> adjectiveGroups, @SuppressWarnings("SameParameterValue") String firstType, @SuppressWarnings("SameParameterValue") String secondType) {
        List<String> maleType = new ArrayList<>();

        for (List<String> group : adjectiveGroups) {
            if (!containsType(firstType, group) || !containsType(secondType, group)) {
                maleType.add(group.get(0));
            }
        }

        return maleType;
    }

    private static List<String> checkType(List<List<String>> adjectiveGroups, @SuppressWarnings("SameParameterValue") String type) {
        List<String> singleType = new ArrayList<>();

        for (List<String> group : adjectiveGroups) {
            if (!containsType(type, group)) {
                singleType.add(group.get(0));
            }
        }

        return singleType;
    }
}
