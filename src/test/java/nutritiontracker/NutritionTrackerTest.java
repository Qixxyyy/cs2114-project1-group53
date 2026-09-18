package nutritiontracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NutritionTrackerTest {
    @Test
    void validationAcceptsNormalAndRevisedFoodNames() {
        assertTrue(Validation.isValidNumber("45"));
        assertTrue(Validation.isValidName("1% milk"));
        assertFalse(Validation.isValidNumber("hello world"));
        assertFalse(Validation.isValidName("Chicken!!!"));
    }

    @Test
    void validationUsesGeneralNutritionAndQuantityBounds() {
        assertTrue(Validation.isValidCalories("0"));
        assertTrue(Validation.isValidCalories("2000"));
        assertFalse(Validation.isValidCalories("2001"));
        assertFalse(Validation.isValidCalories("222222"));
        assertFalse(Validation.isValidCalories("-1"));

        assertTrue(Validation.isValidProtein("0"));
        assertTrue(Validation.isValidProtein("200"));
        assertFalse(Validation.isValidProtein("201"));
        assertFalse(Validation.isValidProtein("123123123"));

        assertTrue(Validation.isValidQuantity("1"));
        assertTrue(Validation.isValidQuantity("100"));
        assertFalse(Validation.isValidQuantity("0"));
        assertFalse(Validation.isValidQuantity("101"));
        assertFalse(Validation.isValidQuantity("13123123"));

        assertTrue(Validation.isValidHeightFeet("3"));
        assertTrue(Validation.isValidHeightFeet("8"));
        assertFalse(Validation.isValidHeightFeet("2"));
        assertFalse(Validation.isValidHeightFeet("9"));
        assertTrue(Validation.isValidHeightInches("0"));
        assertTrue(Validation.isValidHeightInches("11"));
        assertFalse(Validation.isValidHeightInches("12"));
        assertTrue(Validation.isValidWeight("50"));
        assertTrue(Validation.isValidWeight("300"));
        assertFalse(Validation.isValidWeight("19"));
        assertFalse(Validation.isValidWeight("701"));
        assertTrue(Validation.isValidAge("13"));
        assertTrue(Validation.isValidAge("120"));
        assertFalse(Validation.isValidAge("12"));
        assertFalse(Validation.isValidAge("121"));
    }

    @Test
    void foodStoresProfileAndRejectsInvalidValues() {
        Food food = new Food("Apple", 200, 15, 2);

        assertEquals("Apple", food.getName());
        assertEquals(200, food.getCalories());
        assertEquals(15, food.getProtein());
        assertEquals(2, food.getQuantity());
        assertThrows(IllegalArgumentException.class, () -> new Food("Apple", -1, 15));
        assertThrows(IllegalArgumentException.class, () -> new Food("Apple", 2001, 15));
        assertThrows(IllegalArgumentException.class, () -> new Food("Apple", 200, 201));
        assertThrows(IllegalArgumentException.class, () -> new Food("Apple", 200, 15, 101));
    }

    @Test
    void fridgeMergesMatchingFoodsAndRemovesQuantity() {
        Fridge fridge = new Fridge();
        fridge.addFood(new Food("Apple", 200, 15, 2));
        fridge.addFood(new Food("apple", 200, 15, 3));

        assertEquals(1, fridge.getFoods().size());
        assertEquals(5, fridge.findFood("APPLE").getQuantity());
        assertEquals(1000, fridge.totalCalories());
        assertEquals(75, fridge.totalProtein());
        assertEquals("Food removed from the fridge.", fridge.removeFood("Apple", 4));
        assertEquals(1, fridge.findFood("Apple").getQuantity());
        assertEquals("Food not found.", fridge.removeFood("Rice"));
    }

    @Test
    void fridgeRejectsConflictingProfileForSameName() {
        Fridge fridge = new Fridge();
        fridge.addFood(new Food("Apple", 200, 15));

        assertThrows(IllegalArgumentException.class,
            () -> fridge.addFood(new Food("Apple", 300, 15)));
    }

    @Test
    void dailyLogCalculatesTotalsAndEmptyState() {
        DailyLog log = new DailyLog();
        assertEquals("No food logged today.", log.foodHistory());
        assertEquals(0, log.totalCal());
        assertEquals(0, log.totalProtein());

        log.addFood(new Food("Apple", 200, 15), 2);
        log.addFood(new Food("apple", 200, 15), 1);
        log.addFood(new Food("Rice", 300, 20));

        assertEquals("Apple x3, Rice x1", log.foodHistory());
        assertEquals(900, log.totalCal());
        assertEquals(65, log.totalProtein());
    }

    @Test
    void userStoresHeightAndWeight() {
        User user = new User();
        user.newHeight(5, 11);
        user.newWeight(198);
        user.newAge(25);
        user.newSex("male");
        user.newActivityMultiplier(1.2);

        assertEquals(5, user.getHeightFeet());
        assertEquals(11, user.getHeightInches());
        assertEquals(198, user.getWeightPounds());
        assertEquals(25, user.getAge());
        assertEquals("male", user.getSex());
        assertEquals(27.6, user.getBmi(), 0.1);
        assertEquals("Overweight", user.getBmiCategory());
        assertEquals(2286, user.getEstimatedCalorieGoal());
        assertEquals(72, user.getEstimatedProteinGoal());
        assertThrows(IllegalArgumentException.class, () -> user.newHeight(5, 12));
        assertThrows(IllegalArgumentException.class, () -> user.newWeight(49));
        assertThrows(IllegalArgumentException.class, () -> user.newAge(121));
        assertThrows(IllegalArgumentException.class, () -> user.newSex("unknown"));
    }
}
