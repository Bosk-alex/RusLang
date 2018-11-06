package pro.alanphil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static pro.alanphil.RussLang.logger;

class VerbsTasks {
    private static final Set<String> repeatingStrings = new HashSet<>();
    static final Set<String> groupsWithRepeats = new HashSet<>();
    private static final String PND = "ПНДжТ";
    private static final String PNS = "ПНСжТ";
    private static final String PPD = "ППДжТ";
    private static final String PPS = "ППСжТ";
    private static final String TYPE_1 = "1";
    private static final String TYPE_2 = "2";

    private VerbsTasks() {
        throw new IllegalStateException("Utility class");
    }

    static List<String> removeRepeats(List<String> list) {
        checkRepeats(list);
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
            logger.info("end of method");
            return newList;
        }
    }

    private static void checkRepeats(List<String> list) {
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

    static List<List<String>> checkParticipleList(List<List<String>> verbGroups) {
        List<List<String>> newVerbGroups = new ArrayList<>();
        for (List<String> group : verbGroups) {
            newVerbGroups.add(checkParticipleGroup(group));
        }
        return newVerbGroups;
    }

    private static List<String> checkParticipleGroup(List<String> group) {
        List<String> newGroup = checkParticiple(group, PND);
        newGroup = checkParticiple(newGroup, PNS);
        newGroup = checkParticiple(newGroup, PPD);
        newGroup = checkParticiple(newGroup, PPS);

        return newGroup;
    }

    private static List<String> checkParticiple(List<String> group, String type) {
        List<String> newGroup = new ArrayList<>(group);
        List<Integer> indexes = new ArrayList<>();
        boolean isTypeIn = false;
        for (int index = 0; index < newGroup.size(); index++) {
            if (newGroup.get(index).contains(type)) {
                isTypeIn = true;
                indexes.add(index);
            }
        }

        if(isTypeIn && indexes.size() > 1) {
            for (Integer index : indexes) {
                String word = newGroup.get(index);
                if (word.contains("ей")) {
                    word = word.replace(type, type + TYPE_1);
                } else if (word.contains("ею")) {
                    word = word.replace(type, type + TYPE_2);
                }
                newGroup.set(index, word);
            }
        }

        return newGroup;
    }
}
