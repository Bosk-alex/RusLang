package pro.alanphil;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringTokenizer;

class CommonTasks {

    private static final String TYPE_1 = "1";
    private static final String TYPE_2 = "2";

    private CommonTasks() {
        throw new IllegalStateException("Utility class");
    }

    static boolean checkType(@NotNull List<String> newGroup, List<Integer> indexes, String type) {
        boolean isTypeIn = false;
        for (int index = 0; index < newGroup.size(); index++) {
            if (newGroup.get(index).contains(type)) {
                isTypeIn = true;
                indexes.add(index);
            }
        }
        return isTypeIn;
    }

    static void renameTypeIfContains(List<String> newGroup, List<Integer> indexes, @SuppressWarnings("SameParameterValue") String subString1, @SuppressWarnings("SameParameterValue") String subString2, String type) {
        for (Integer index : indexes) {
            String word = newGroup.get(index);
            if (word.contains(subString1)) {
                word = word.replace(type, type + TYPE_1);
            } else if (word.contains(subString2)) {
                word = word.replace(type, type + TYPE_2);
            }
            newGroup.set(index, word);
        }
    }

    static void renameTypeIfEnds(List<String> newGroup, List<Integer> indexes, String subString1, String subString2, String type) {
        for (Integer index : indexes) {
            String word = newGroup.get(index);
            String firstWord = new StringTokenizer(word).nextToken();
            if (firstWord.endsWith(subString1)) {
                word = word.replace(type, type + TYPE_1);
            } else if (firstWord.endsWith(subString2)) {
                word = word.replace(type, type + TYPE_2);
            }
            newGroup.set(index, word);
        }
    }
}
