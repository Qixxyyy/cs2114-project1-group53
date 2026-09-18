package nutritiontracker;

import java.util.Locale;
import java.util.Objects;

public class Food {
    private final String name;
    private final int calories;
    private final int protein;
    private int quantity;

    public Food(String name, int calories, int protein) {
        this(name, calories, protein, 1);
    }

    public Food(String name, int calories, int protein, int quantity) {
        if (!Validation.isValidName(name)) {
            throw new IllegalArgumentException("Food name must contain letters, numbers, spaces, or %.");
        }
        if (calories < 0 || calories > Validation.MAX_CALORIES
            || protein < 0 || protein > Validation.MAX_PROTEIN
            || quantity < 1 || quantity > Validation.MAX_QUANTITY) {
            throw new IllegalArgumentException("Food nutrition and quantity values are outside the allowed limits.");
        }

        this.name = name.trim();
        this.calories = calories;
        this.protein = protein;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int amount) {
        if (amount <= 0 || quantity > Validation.MAX_QUANTITY - amount) {
            throw new IllegalArgumentException("Quantity cannot exceed " + Validation.MAX_QUANTITY + ".");
        }
        quantity += amount;
    }

    public void removeQuantity(int amount) {
        if (amount <= 0 || amount > quantity) {
            throw new IllegalArgumentException("Quantity to remove must be positive and available.");
        }
        quantity -= amount;
    }

    public String normalizedName() {
        return name.toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Food)) {
            return false;
        }
        Food other = (Food) object;
        return normalizedName().equals(other.normalizedName())
            && calories == other.calories
            && protein == other.protein;
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalizedName(), calories, protein);
    }

    @Override
    public String toString() {
        return name + " - " + calories + " calories, " + protein
            + "g protein, quantity: " + quantity;
    }
}
