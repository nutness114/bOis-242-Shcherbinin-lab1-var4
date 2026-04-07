package converter.util;

public final class InputValidator {

    private InputValidator() {
    }

    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isNumeric(String input) {
        if (isEmpty(input)) {
            return false;
        }
        try {
            Double.parseDouble(input.trim().replace(',', '.'));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPositive(String input) {
        if (!isNumeric(input)) {
            return false;
        }
        return Double.parseDouble(input.trim().replace(',', '.')) > 0;
    }

    public static boolean isWithinMax(String input, double maxValue) {
        if (!isNumeric(input)) {
            return false;
        }
        return Double.parseDouble(input.trim().replace(',', '.')) <= maxValue;
    }
}
