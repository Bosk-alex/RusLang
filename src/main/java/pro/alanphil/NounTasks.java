package pro.alanphil;

import java.util.ArrayList;
import java.util.List;

import static pro.alanphil.CommonTasks.*;

public class NounTasks {
    private static final String SMNI = "СмнИ";
    private static final String SMNV = "СмнВ";
    private static final String I = "и";
    private static final String Y = "ы";
    private static final String A = "а";
    private static final String YA = "я";

    private NounTasks() {
        throw new IllegalStateException("Utility class");
    }


    static List<List<String>> renameNounTypes(List<List<String>> nounGroups) {
        List<List<String>> newNounGroup = new ArrayList<>(nounGroups);
        for (List<String> nounGroup : newNounGroup) {
            renameNounType(nounGroup, SMNI);
            renameNounType(nounGroup, SMNV);
        }
        return newNounGroup;
    }

    static List<List<String>> removeNouns(List<List<String>> nounGroups) {
        List<List<String>> newNounGroup = new ArrayList<>(nounGroups);

        return newNounGroup;
    }

    private static void renameNounType(List<String> nounGroup, String type) {
        List<Integer> indexes = new ArrayList<>();
        boolean isTypeIn = checkType(nounGroup, indexes, type);

        if (isTypeIn) {
            renameTypeIfEnds(nounGroup, indexes, I, A, type);
            renameTypeIfEnds(nounGroup, indexes, Y, YA, type);
        }
    }
}
