package nutritiontracker;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DailyLog {
    private final ArrayList<Food> foods;

    public DailyLog() {
        foods = new ArrayList<>();
    }

    public void addFood(Food food) {
        addFood(food, 1);
    }

    public void addFood(Food food, int quantity) {
        if (food == null) {
            throw new IllegalArgumentException("Food cannot be null.");
        }
        if (quantity < 1 || quantity > Validation.MAX_QUANTITY) {
            throw new IllegalArgumentException("Logged quantity is outside the allowed limits.");
        }
        foods.add(new Food(food.getName(), food.getCalories(), food.getProtein(), quantity));
    }

    public String foodHistory() {
        if (foods.isEmpty()) {
            return "No food logged today.";
        }
        Map<String, Food> groupedFoods = new LinkedHashMap<>();
        for (Food food : foods) {
            Food groupedFood = groupedFoods.get(food.normalizedName());
            if (groupedFood == null) {
                groupedFoods.put(food.normalizedName(),
                    new Food(food.getName(), food.getCalories(), food.getProtein(), food.getQuantity()));
            } else {
                groupedFood.addQuantity(food.getQuantity());
            }
        }
        return groupedFoods.values().stream()
            .map(food -> food.getName() + " x" + food.getQuantity())
            .collect(Collectors.joining(", "));
    }

    public int totalCal() {
        return foods.stream()
            .mapToInt(food -> food.getCalories() * food.getQuantity())
            .sum();
    }

    public int totalProtein() {
        return foods.stream()
            .mapToInt(food -> food.getProtein() * food.getQuantity())
            .sum();
    }

    public List<Food> getFoods() {
        return List.copyOf(foods);
    }

    public boolean isEmpty() {
        return foods.isEmpty();
    }
}
