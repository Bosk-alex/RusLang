package pro.alanphil;

import static pro.alanphil.RussLang.*;

public enum Colors {
    BLACK(30), BLUE(34), CYAN(36), GREEN(32), MAGENTA(35), RED(31), WHITE(37), YELLOW(33);

    private final int colorCode;

    Colors(int colorCode) {
        this.colorCode = colorCode;
    }

    private int getColorCode() {
        return colorCode;
    }

    public static String colorMessage(String text, Colors color) {
        return colorAllMassages(text, color) + POSTFIX;
    }

    public static String colorAllMassages(String text, Colors color) {
        int colorCode;
        switch (color) {
            case RED:
                colorCode = RED.getColorCode();
                break;
            case GREEN:
                colorCode = GREEN.getColorCode();
                break;
            case YELLOW:
                colorCode = YELLOW.getColorCode();
                break;
            case BLUE:
                colorCode = BLUE.getColorCode();
                break;
            case MAGENTA:
                colorCode = MAGENTA.getColorCode();
                break;
            case CYAN:
                colorCode = CYAN.getColorCode();
                break;
            case WHITE:
                colorCode = WHITE.getColorCode();
                break;
            case BLACK:
                colorCode = BLACK.getColorCode();
                break;
            default:
                colorCode = 0;
        }
        return PREFIX_ANSI + colorCode + "m" + text;
    }
}
