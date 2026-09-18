package nutritiontracker;

public class User {
    private int heightFeet;
    private int heightInches;
    private int weightPounds;
    private int age;
    private String sex;
    private double activityMultiplier;

    public void newHeight(int feet, int inches) {
        if (feet < Validation.MIN_HEIGHT_FEET || feet > Validation.MAX_HEIGHT_FEET
            || inches < 0 || inches > 11) {
            throw new IllegalArgumentException("Height must be between 3'0\" and 8'11\".");
        }
        heightFeet = feet;
        heightInches = inches;
    }

    public void newWeight(int weight) {
        if (weight < Validation.MIN_WEIGHT_POUNDS || weight > Validation.MAX_WEIGHT_POUNDS) {
            throw new IllegalArgumentException("Weight must be between "
                + Validation.MIN_WEIGHT_POUNDS + " and " + Validation.MAX_WEIGHT_POUNDS + " lb.");
        }
        weightPounds = weight;
    }

    public void newAge(int age) {
        if (age < Validation.MIN_AGE || age > Validation.MAX_AGE) {
            throw new IllegalArgumentException("Age must be between "
                + Validation.MIN_AGE + " and " + Validation.MAX_AGE + ".");
        }
        this.age = age;
    }

    public void newSex(String sex) {
        if (!Validation.isValidSex(sex)) {
            throw new IllegalArgumentException("Sex must be male or female for this estimate.");
        }
        this.sex = sex.trim().toLowerCase();
    }

    public void newActivityMultiplier(double activityMultiplier) {
        if (activityMultiplier <= 0) {
            throw new IllegalArgumentException("Activity multiplier must be positive.");
        }
        this.activityMultiplier = activityMultiplier;
    }

    public int getHeightFeet() {
        return heightFeet;
    }

    public int getHeightInches() {
        return heightInches;
    }

    public int getWeightPounds() {
        return weightPounds;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public double getBmi() {
        int totalHeightInches = heightFeet * 12 + heightInches;
        return 703.0 * weightPounds / (totalHeightInches * totalHeightInches);
    }

    public String getBmiCategory() {
        double bmi = getBmi();
        if (bmi < 18.5) {
            return "Underweight";
        }
        if (bmi < 25.0) {
            return "Normal range";
        }
        if (bmi < 30.0) {
            return "Overweight";
        }
        return "Obesity range";
    }

    public int getEstimatedCalorieGoal() {
        double weightKg = weightPounds / 2.20462;
        double heightCm = (heightFeet * 12 + heightInches) * 2.54;
        double bmr = 10 * weightKg + 6.25 * heightCm - 5 * age;
        bmr += sex.equals("male") ? 5 : -161;
        return (int) Math.round(bmr * activityMultiplier);
    }

    public int getEstimatedProteinGoal() {
        return (int) Math.round(weightPounds * 0.8 / 2.20462);
    }
}
