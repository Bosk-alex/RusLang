package pro.alanphil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static pro.alanphil.CommonTasks.renameTypeIfContains;
import static pro.alanphil.RussLang.logger;

class VerbTasks {
    private static final Set<String> repeatingStrings = new HashSet<>();
    static final Set<String> groupsWithRepeats = new HashSet<>();
    private static final String PND = "ПНДжТ";
    private static final String PNS = "ПНСжТ";
    private static final String PPD = "ППДжТ";
    private static final String PPS = "ППСжТ";
    private static final String EY = "ей";
    private static final String EYU = "ею";

    static final String GP = "ГПм";
    static final String DN = "ДН";
    static final String DP = "ДП";

    private VerbTasks() {
        throw new IllegalStateException("Utility class");
    }

    static List<String> removeRepeats(List<String> list) {
        checkStringRepeats(list);
        if (repeatingStrings.isEmpty()) {
            return list;
        } else {
            List<String> newList = new ArrayList<>(list);
            for (String repeat : repeatingStrings) {
                while (newList.contains(repeat)) {
                    if (newList.indexOf(repeat) != newList.lastIndexOf(repeat)) {
                        newList.remove(newList.lastIndexOf(repeat));
                    } else break;
                }
            }
            return newList;
        }
    }

    private static void checkStringRepeats(@NotNull List<String> list) {
        for (int index = 0; index < list.size() - 1; index++) {
            String groupName = list.get(0);
            for (int subIndex = index + 1; subIndex < list.size(); subIndex++) {
                if (list.get(index).equalsIgnoreCase(list.get(subIndex))) {
                    groupsWithRepeats.add(groupName);
                    repeatingStrings.add(list.get(index));
                }
            }
        }
    }

    static void checkSubstringRepeats(@NotNull List<String> list, String type) {
        for (int index = 0; index < list.size() - 1; index++) {
            String groupName = list.get(0);
            for (int subIndex = index + 1; subIndex < list.size(); subIndex++) {
                if (list.get(index).endsWith(type) && list.get(subIndex).endsWith(type)) {
                    groupsWithRepeats.add(groupName);
                    repeatingStrings.add(list.get(index));
                }
            }
        }
    }

    static List<List<String>> checkParticipleList(@NotNull List<List<String>> verbGroups) {
        List<List<String>> newVerbGroups = new ArrayList<>();
        for (List<String> group : verbGroups) {
            newVerbGroups.add(checkParticipleGroup(group));
        }
        logger.info("end of participle method");
        return newVerbGroups;
    }

    private static List<String> checkParticipleGroup(List<String> group) {
        List<String> newGroup = checkAndRenameParticiple(group, PND);
        newGroup = checkAndRenameParticiple(newGroup, PNS);
        newGroup = checkAndRenameParticiple(newGroup, PPD);
        newGroup = checkAndRenameParticiple(newGroup, PPS);

        return newGroup;
    }

    private static List<String> checkAndRenameParticiple(List<String> group, String type) {
        List<String> newGroup = new ArrayList<>(group);
        List<Integer> indexes = new ArrayList<>();
        boolean isTypeIn = CommonTasks.checkType(newGroup, indexes, type);

        if(isTypeIn && indexes.size() > 1) {
            renameTypeIfContains(newGroup, indexes, EY, EYU, type);
        }

        return newGroup;
    }

}
