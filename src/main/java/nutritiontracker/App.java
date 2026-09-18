package nutritiontracker;

import java.util.Scanner;

public class App {
    private final Scanner scanner;
    private final Fridge fridge;
    private final DailyLog dailyLog;
    private final User user;

    public App() {
        scanner = new Scanner(System.in);
        fridge = new Fridge();
        dailyLog = new DailyLog();
        user = new User();
    }

    public static void main(String[] args) {
        new App().run();
    }

    public void run() {
        boolean running = true;
        System.out.println("Nutrition Tracker");
        setupProfile();

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    showProfile();
                    break;
                case "2":
                    addFood();
                    break;
                case "3":
                    showFridge();
                    break;
                case "4":
                    removeFood();
                    break;
                case "5":
                    logFood();
                    break;
                case "6":
                    showDailyLog();
                    break;
                case "7":
                    running = false;
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid menu choice. Please try again.");
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. View profile and calorie estimate");
        System.out.println("2. Add food to fridge");
        System.out.println("3. View fridge");
        System.out.println("4. Remove food from fridge");
        System.out.println("5. Log food eaten today");
        System.out.println("6. View today's log");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    private void setupProfile() {
        System.out.println("\nSet up your profile.");
        int heightFeet = readProfileInteger("Height in feet", Validation.MIN_HEIGHT_FEET,
            Validation.MAX_HEIGHT_FEET, Validation::isValidHeightFeet);
        int heightInches = readProfileInteger("Additional height in inches", 0, 11,
            Validation::isValidHeightInches);
        user.newHeight(heightFeet, heightInches);
        user.newWeight(readProfileInteger("Weight in pounds", Validation.MIN_WEIGHT_POUNDS,
            Validation.MAX_WEIGHT_POUNDS, Validation::isValidWeight));
        user.newAge(readProfileInteger("Age", Validation.MIN_AGE, Validation.MAX_AGE,
            Validation::isValidAge));
        user.newSex(readSex());
        user.newActivityMultiplier(readActivityMultiplier());
    }

    private void showProfile() {
        System.out.printf("Height: %d'%d\"%n", user.getHeightFeet(), user.getHeightInches());
        System.out.printf("Weight: %d lb%n", user.getWeightPounds());
        System.out.printf("Age: %d%n", user.getAge());
        System.out.printf("BMI: %.1f (%s)%n", user.getBmi(), user.getBmiCategory());
        System.out.println("Estimated maintenance calories: "
            + user.getEstimatedCalorieGoal() + " per day");
        System.out.println("Estimated protein goal: " + user.getEstimatedProteinGoal() + "g per day");
    }

    private void addFood() {
        String name = readName("Food name: ");
        int calories = readCalories();
        int protein = readProtein();
        int quantity = readQuantity("Quantity: ");

        try {
            System.out.println(fridge.addFood(new Food(name, calories, protein, quantity)));
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void showFridge() {
        if (fridge.isEmpty()) {
            System.out.println("The fridge is empty.");
            System.out.println("Fridge totals:");
            System.out.println("Calories: 0");
            System.out.println("Protein: 0g");
            return;
        }
        for (Food food : fridge.getFoods()) {
            System.out.println(food);
        }
        System.out.println("Fridge totals:");
        System.out.println("Calories: " + fridge.totalCalories());
        System.out.println("Protein: " + fridge.totalProtein() + "g");
    }

    private void removeFood() {
        String name = readName("Food name to remove: ");
        int quantity = readQuantity("Quantity to remove: ");
        System.out.println(fridge.removeFood(name, quantity));
    }

    private void logFood() {
        String name = readName("Food name to log: ");
        Food food = fridge.findFood(name);
        if (food == null) {
            System.out.println("Food not found in the fridge.");
            return;
        }
        int quantity;
        while (true) {
            quantity = readQuantity("Quantity eaten: ");
            if (quantity <= food.getQuantity()) {
                break;
            }
            System.out.println("Only " + food.getQuantity() + " serving(s) are available.");
        }
        dailyLog.addFood(food, quantity);
        fridge.removeFood(name, quantity);
        System.out.println("Food logged for today.");
    }

    private void showDailyLog() {
        System.out.println(dailyLog.foodHistory());
        System.out.println("Total calories: " + dailyLog.totalCal());
        System.out.println("Total protein: " + dailyLog.totalProtein() + "g");
        showProgress("Calories", dailyLog.totalCal(), user.getEstimatedCalorieGoal());
        showProgress("Protein", dailyLog.totalProtein(), user.getEstimatedProteinGoal());
    }

    private void showProgress(String label, int consumed, int goal) {
        if (consumed < goal) {
            System.out.println(label + " remaining: " + (goal - consumed)
                + (label.equals("Protein") ? "g" : ""));
        } else if (consumed == goal) {
            System.out.println(label + " goal met.");
        } else {
            System.out.println(label + " goal met.");
            System.out.println(label + " over goal: " + (consumed - goal)
                + (label.equals("Protein") ? "g" : ""));
        }
    }

    private String readName(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (Validation.isValidName(input)) {
                return input;
            }
            System.out.println("Invalid name. Use letters, numbers, spaces, or %.");
        }
    }

    private int readCalories() {
        while (true) {
            System.out.print("Calories per serving (0-" + Validation.MAX_CALORIES + "): ");
            String input = scanner.nextLine();
            if (Validation.isValidCalories(input)) {
                return Integer.parseInt(input.trim());
            }
            System.out.println("Calories must be between 0 and " + Validation.MAX_CALORIES + ".");
        }
    }

    private int readProtein() {
        while (true) {
            System.out.print("Protein per serving (0-" + Validation.MAX_PROTEIN + "g): ");
            String input = scanner.nextLine();
            if (Validation.isValidProtein(input)) {
                return Integer.parseInt(input.trim());
            }
            System.out.println("Protein must be between 0 and " + Validation.MAX_PROTEIN + "g.");
        }
    }

    private int readQuantity(String prompt) {
        while (true) {
            System.out.print(prompt.replace(":", "").trim() + " (1-"
                + Validation.MAX_QUANTITY + "): ");
            String input = scanner.nextLine();
            if (Validation.isValidQuantity(input)) {
                return Integer.parseInt(input.trim());
            }
            System.out.println("Quantity must be between 1 and " + Validation.MAX_QUANTITY + ".");
        }
    }

    private int readProfileInteger(String field, int minimum, int maximum,
        java.util.function.Predicate<String> validator) {
        while (true) {
            System.out.print(field + " (" + minimum + "-" + maximum + "): ");
            String input = scanner.nextLine();
            if (validator.test(input)) {
                return Integer.parseInt(input.trim());
            }
            System.out.println(field + " must be between " + minimum + " and " + maximum + ".");
        }
    }

    private String readSex() {
        while (true) {
            System.out.print("Sex for the calorie estimate (male/female): ");
            String input = scanner.nextLine();
            if (Validation.isValidSex(input)) {
                return input.trim();
            }
            System.out.println("Enter male or female.");
        }
    }

    private double readActivityMultiplier() {
        while (true) {
            System.out.println("Activity level:");
            System.out.println("1. Sedentary");
            System.out.println("2. Lightly active");
            System.out.println("3. Moderately active");
            System.out.println("4. Very active");
            System.out.print("Choose an activity level: ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    return 1.2;
                case "2":
                    return 1.375;
                case "3":
                    return 1.55;
                case "4":
                    return 1.725;
                default:
                    System.out.println("Choose an activity level from 1 to 4.");
                    break;
            }
        }
    }
}
