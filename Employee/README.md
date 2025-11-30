# Employee – Kotlin Inheritance Example

This small Kotlin program shows how to use a base class and subclasses to
calculate employee salaries.

## Classes

- `Employee`
    - Base class
    - Protected properties:
        - `name` – employee’s name
        - `baseSalary` – fixed base salary
    - `open fun calculateSalary()`  
      → returns the base salary
    - `fun getEmployeeName()`  
      → returns the employee’s name so we can use it outside the class

- `FullTimeEmployee`
    - Inherits from `Employee`
    - Has an extra `bonus`
    - `calculateSalary()` returns `baseSalary + bonus`

- `PartTimeEmployee`
    - Inherits from `Employee`
    - Has `hourlyRate` and `hoursWorked`
    - `calculateSalary()` returns `hourlyRate * hoursWorked`

## What the program does

1. Creates some `FullTimeEmployee` and `PartTimeEmployee` objects.
2. Uses `mapOf` to store each employee’s **name** and calculated **salary**.
3. Loops through the map and prints:

