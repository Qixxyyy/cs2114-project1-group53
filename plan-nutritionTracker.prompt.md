## Plan: Nutrition Tracker MVP

Build a small Java console nutrition tracker from the current blank repository, using the provided specification as the contract and the final-deliverable rubric as the acceptance checklist. Keep the MVP in memory only, use plain `javac` for production code, and use JUnit 5 through a documented standalone console JAR rather than adding Maven. The user selected a CLI, integer quantity merging in the fridge, and no calorie/protein goals in the MVP.

**Steps**
1. **Lock the contract and project layout**
   - Use Java 17 unless the course specifies another installed version.
   - Create `src/main/java` and `src/test/java` with one consistent package, plus a `lib`-free README workflow that explains where to obtain the JUnit Platform Console Standalone JAR.
   - Resolve specification contradictions explicitly: validation methods accept strings so malformed console input can be handled; model methods reject invalid values without crashing; height/weight remain simple `User` data accessors but interactive prompting and goal calculations stay out of the MVP.
   - Use integer nonnegative calories, protein, and quantity. Food names accept letters, digits, spaces, and `%` so names such as `1% milk` work; reject null, blank, and punctuation-only/unsupported names.

2. **Implement the model in dependency order**
   - `Validation`: reusable checks for numeric text/ranges and food-name format. It should return predictable booleans for checks; `App` handles re-prompting.
   - `Food`: name, calories per unit, protein per unit, and integer quantity. Expose getters and controlled quantity changes; reject invalid construction or quantity changes with clear exceptions.
   - `Fridge`: own an `ArrayList<Food>`. Adding a food with the same normalized name merges quantity into the existing entry; otherwise it adds a new entry. Provide lookup/listing and removal that decrements quantity, removes the entry at zero, and returns a not-found result/message without crashing.
   - `DailyLog`: own an `ArrayList<Food>` of consumed entries. Adding a food records the consumed item/serving, and totals sum calories and protein. Empty history returns `No food logged today.` and empty totals return zero.
   - `User`: retain the specified integer height/weight state with getters and setters for programmatic use; do not add goal logic or interactive prompts to the MVP.
   - `App`: own the CLI loop and coordinate the model classes only. Menu actions: add food to fridge, view fridge, remove food, log a fridge food, view today’s history/totals, and exit. Parse all input defensively and re-prompt instead of allowing exceptions to terminate the program.

3. **Add focused JUnit tests**
   - Test normal and invalid/boundary behavior for `Validation` and `Food`.
   - Test fridge insertion, same-name quantity merge, removal, missing-food behavior, and empty state.
   - Test daily-log history, multiple-food calorie/protein totals, repeated entries, and empty-log behavior.
   - Test `User` setters/getters and invalid values if those setters validate.
   - Keep tests independent and runnable through the JUnit Platform Console Standalone JAR. Avoid testing console formatting more deeply than the required bad-input/no-crash behavior.

4. **Create runnable documentation and project evidence**
   - Expand `README.md` with prerequisites, compile commands for PowerShell and/or a platform-neutral equivalent, run command, test command, expected source layout, MVP behavior, and the explicit stretch-goal boundary.
   - Add a legible system diagram under a documentation/assets path or as a repository image. It should show `App -> Validation`, `App -> Fridge -> Food`, and `App -> DailyLog -> Food`, with the movement of food data and totals labeled.
   - Add a short presentation evidence section or notes for the group: what was built, live demo sequence, what changed from Deliverable 2, what went well/poorly, and truthful GenAI lessons. Do not invent claims about tool usage; the group supplies its own experience.

5. **Verify in clean stages**
   - After setup, compile production classes with `javac`.
   - After each model slice, run the narrow JUnit class or test subset, then run the complete suite.
   - Run the CLI manually with a normal flow and malformed names/numbers, confirming it re-prompts and continues.
   - Recompile from a clean output directory and follow the README exactly. Check that no compiled artifacts or JUnit binaries are committed.
   - Before submission, confirm the public GitHub repository opens in a private browser, contains source/tests/README/diagram, and that the final presentation covers all four required parts.

**Relevant files**
- `README.md` — replace the one-line description with complete compile/run/test instructions and project scope.
- `src/main/java/.../Validation.java` — validation contract and reusable checks.
- `src/main/java/.../Food.java` — food profile and quantity state.
- `src/main/java/.../Fridge.java` — merged inventory collection and removal behavior.
- `src/main/java/.../DailyLog.java` — consumed-food collection and totals.
- `src/main/java/.../User.java` — retained height/weight state without goal scope.
- `src/main/java/.../App.java` — console orchestration and bad-input recovery.
- `src/test/java/.../*Test.java` — JUnit normal and bad-input coverage.
- `docs/system-diagram.*` — repository copy of the required diagram.

**Verification**
1. `javac -d out (Get-ChildItem -Recurse src/main/java -Filter *.java)` succeeds.
2. JUnit Platform Console Standalone runs all tests and reports zero failures.
3. The console demo proves add/merge, log/totals, and malformed-input recovery.
4. A clean README-driven build reproduces the result.
5. Repository checklist matches the final assignment: working code, tests, README, diagram, public link, and presentation coverage.

**Decisions**
- Console MVP, not GUI.
- Plain `javac` plus a documented JUnit 5 standalone console JAR, per the user’s selection; no Maven unless the course environment makes the standalone workflow impractical.
- Fridge duplicate names merge into an integer quantity field on `Food`; DailyLog entries remain separate consumed records so totals are straightforward.
- In-memory only; no persistence, expiration tracking, height/weight prompts, or calorie/protein goal calculations in the MVP.
- No commits or remote publishing by the coding assistant unless explicitly requested; GitHub setup and final submission remain supervised group actions.

**Further Considerations**
1. Confirm the course’s required Java version before implementation; default recommendation is Java 17.
2. Confirm whether the instructor requires a specific package name or starter JUnit setup; otherwise use a simple lowercase group package.
3. During implementation, review whether the instructor expects the exact original method names (`newFood`, `delFood`, `newCalories`, `newProtein`) or permits conventional parameterized APIs. Preserve compatibility aliases only if needed by grading expectations.
