package pro.alanphil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommonTasks {

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

    static void renameType(List<String> newGroup, List<Integer> indexes, String type) {
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
}
