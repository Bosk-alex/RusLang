package pro.alanphil;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static pro.alanphil.CommonTasks.*;
import static pro.alanphil.RussLang.logger;

class NounTasks {
    private static final String SMNI = "СмнИ";
    private static final String SMNV = "СмнВ";
    private static final String SMNR = "СмнВ";
    private static final String SMI = "СмИ";
    private static final String SMV = "СмВ";
    private static final String SMR = "СмР";
    private static final String I = "и";
    private static final String Y = "ы";
    private static final String A = "а";
    private static final String YA = "я";
    private static final CharSequence LIFELESS = "неод";

    private NounTasks() {
        logger.warning("Something happen");
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
        for (List<String> nounGroup : newNounGroup) {
            classifyNouns(nounGroup);
        }
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

    private static void classifyNouns(List<String> nounGroup) {
        if (nounGroup.get(0).contains(LIFELESS)) {
            removeWrongString(nounGroup, SMV, SMI);
            removeWrongString(nounGroup, SMNV, SMNI);
        } else {
            removeWrongString(nounGroup, SMV, SMR);
            removeWrongString(nounGroup, SMNV, SMNR);
        }
    }

    private static void removeWrongString(List<String> nounGroup, String checkType, String sampleType) {
        int checkIndex = Integer.MAX_VALUE;
        int sampleIndex = Integer.MAX_VALUE;
        String checkValue = "";
        String sampleValue = "";
        for (int index = 0; index < nounGroup.size(); index++) {
            if (nounGroup.get(index).contains(checkType)) {
                checkIndex = index;
                checkValue = new StringTokenizer(nounGroup.get(index)).nextToken();
            } else if (nounGroup.get(index).contains(sampleType)) {
                sampleIndex = index;
                sampleValue = new StringTokenizer(nounGroup.get(index)).nextToken();
            }
        }
        if (checkIndex != Integer.MAX_VALUE && sampleIndex != Integer.MAX_VALUE && !checkValue.equals(sampleValue)) {
            nounGroup.remove(checkIndex);
        }
    }
}
