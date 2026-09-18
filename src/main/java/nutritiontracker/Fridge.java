package nutritiontracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fridge {
    private final ArrayList<Food> foods;

    public Fridge() {
        foods = new ArrayList<>();
    }

    public String addFood(Food food) {
        if (food == null) {
            throw new IllegalArgumentException("Food cannot be null.");
        }

        Food existingFood = findFood(food.getName());
        if (existingFood == null) {
            foods.add(food);
            return "Food added to the fridge.";
        }
        if (existingFood.getCalories() != food.getCalories()
            || existingFood.getProtein() != food.getProtein()) {
            throw new IllegalArgumentException(
                "A food with that name already has different nutrition values.");
        }

        existingFood.addQuantity(food.getQuantity());
        return "Food quantity updated in the fridge.";
    }

    public Food findFood(String name) {
        if (!Validation.isValidName(name)) {
            return null;
        }

        String normalizedName = name.trim().toLowerCase();
        for (Food food : foods) {
            if (food.normalizedName().equals(normalizedName)) {
                return food;
            }
        }
        return null;
    }

    public String removeFood(String name) {
        return removeFood(name, 1);
    }

    public String removeFood(String name, int quantity) {
        Food food = findFood(name);
        if (food == null) {
            return "Food not found.";
        }
        if (quantity <= 0 || quantity > food.getQuantity()) {
            return "Invalid quantity.";
        }

        food.removeQuantity(quantity);
        if (food.getQuantity() == 0) {
            foods.remove(food);
        }
        return "Food removed from the fridge.";
    }

    public List<Food> getFoods() {
        return Collections.unmodifiableList(foods);
    }

    public boolean isEmpty() {
        return foods.isEmpty();
    }

    public int totalCalories() {
        return foods.stream()
            .mapToInt(food -> food.getCalories() * food.getQuantity())
            .sum();
    }

    public int totalProtein() {
        return foods.stream()
            .mapToInt(food -> food.getProtein() * food.getQuantity())
            .sum();
    }
}
