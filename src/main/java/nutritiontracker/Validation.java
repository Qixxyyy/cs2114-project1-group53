package nutritiontracker;

public final class Validation {
    public static final int MAX_CALORIES = 2000;
    public static final int MAX_PROTEIN = 200;
    public static final int MAX_QUANTITY = 100;
    public static final int MIN_HEIGHT_FEET = 3;
    public static final int MAX_HEIGHT_FEET = 8;
    public static final int MIN_WEIGHT_POUNDS = 50;
    public static final int MAX_WEIGHT_POUNDS = 700;
    public static final int MIN_AGE = 13;
    public static final int MAX_AGE = 120;

    private Validation() {
    }

    public static boolean isValidNumber(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        try {
            return Integer.parseInt(input.trim()) >= 0;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static boolean isValidPositiveNumber(String input) {
        if (!isValidNumber(input)) {
            return false;
        }
        return Integer.parseInt(input.trim()) > 0;
    }

    public static boolean isValidCalories(String input) {
        return isInRange(input, 0, MAX_CALORIES);
    }

    public static boolean isValidProtein(String input) {
        return isInRange(input, 0, MAX_PROTEIN);
    }

    public static boolean isValidQuantity(String input) {
        return isInRange(input, 1, MAX_QUANTITY);
    }

    public static boolean isValidHeightFeet(String input) {
        return isInRange(input, MIN_HEIGHT_FEET, MAX_HEIGHT_FEET);
    }

    public static boolean isValidHeightInches(String input) {
        return isInRange(input, 0, 11);
    }

    public static boolean isValidWeight(String input) {
        return isInRange(input, MIN_WEIGHT_POUNDS, MAX_WEIGHT_POUNDS);
    }

    public static boolean isValidAge(String input) {
        return isInRange(input, MIN_AGE, MAX_AGE);
    }

    public static boolean isValidSex(String input) {
        return input != null
            && (input.trim().equalsIgnoreCase("male")
                || input.trim().equalsIgnoreCase("female"));
    }

    private static boolean isInRange(String input, int minimum, int maximum) {
        if (!isValidNumber(input)) {
            return false;
        }
        int value = Integer.parseInt(input.trim());
        return value >= minimum && value <= maximum;
    }

    public static boolean isValidName(String input) {
        return input != null
            && !input.trim().isEmpty()
            && input.trim().matches("[A-Za-z0-9%]+(?:[ A-Za-z0-9%]*[A-Za-z0-9%])?");
    }
}
