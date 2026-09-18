# Nutrition Tracker System Diagram

```mermaid
flowchart LR
    App[App\nConsole workflow]
    Validation[Validation\nInput checks]
    Fridge[Fridge\nArrayList of Food]
    Food[Food\nName, nutrition, quantity]
    DailyLog[DailyLog\nConsumed Food entries]

    App -->|raw names and numbers| Validation
    Validation -->|valid input or retry| App
    App -->|add, find, remove| Fridge
    Fridge -->|stores and returns| Food
    App -->|selected serving| DailyLog
    DailyLog -->|reads calories and protein| Food
    DailyLog -->|history and totals| App
```

`App` coordinates the user workflow. `Validation` checks console input.
`Fridge` owns available food quantities, while `DailyLog` stores consumed food
entries and calculates daily nutrition totals.