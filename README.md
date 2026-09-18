# CS 2114 Project 1: Nutrition Tracker

This Java console application helps users manage foods in a fridge, log food
eaten today, calculate daily calories and protein, and view an estimated
maintenance-calorie goal from a user profile.

## Requirements

- Java 17 or newer
- JUnit Platform Console Standalone 1.11.4 for tests

Download the JUnit launcher from Maven Central and save it outside the
repository, for example as `%TEMP%\junit-platform-console-standalone-1.11.4.jar`.

## Compile and Run

From PowerShell at the repository root:

```powershell
$sources = Get-ChildItem -Recurse src/main/java -Filter *.java |
	Select-Object -ExpandProperty FullName
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force out | Out-Null
javac -d out $sources
java -cp out nutritiontracker.App
```

## Run Tests

Set the launcher path, compile the tests, and run the complete suite:

```powershell
$junit = Join-Path $env:TEMP 'junit-platform-console-standalone-1.11.4.jar'
$testSources = Get-ChildItem -Recurse src/test/java -Filter *.java |
	Select-Object -ExpandProperty FullName
Remove-Item -Recurse -Force out-test -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force out-test | Out-Null
javac -cp "$junit;out" -d out-test $testSources
java -jar $junit execute --class-path 'out;out-test' --scan-class-path
```

## MVP Features

- Set up a profile with height in feet/inches, weight in pounds, age, sex, and activity level.
- Display BMI and an estimated maintenance-calorie goal.
- Display an estimated protein goal using `0.8g/kg`, converted internally to approximately `0.363g/lb`.
- Add foods to the fridge with name, calories, protein, and quantity.
- Merge repeated food names when their nutrition values match.
- Remove food quantities safely, including a not-found response.
- Log a selected quantity of a food eaten today and remove it from the fridge.
- Display grouped food history, fridge totals, daily totals, and progress toward both goals.
- Re-prompt after invalid menu, name, or number input.

After profile setup, the menu is:

```text
1. View profile and calorie estimate
2. Add food to fridge
3. View fridge
4. Remove food from fridge
5. Log food eaten today
6. View today's log
7. Exit
```

Food and profile values use these application limits:

- Calories: `0-2000` per serving
- Protein: `0-200g` per serving
- Quantity: `1-100`
- Height: `3'0"-8'11"`
- Weight: `50-700 lb`
- Age: `13-120`

These are sanity bounds for input validation. The BMI and calorie results are
educational estimates, not medical advice.

The MVP is in-memory only. Expiration dates, persistence, and simulated-day
tracking remain stretch goals outside the current scope.

## Project Layout

```text
src/main/java/nutritiontracker/  Application source
src/test/java/nutritiontracker/  JUnit tests
docs/system-diagram.md             System diagram
```

See [the system diagram](docs/system-diagram.md) for the class relationships.
