# Kotlin Counter App

This is a small Android app made with **Kotlin** and **Jetpack Compose**.

The app shows **three independent counters**. Each counter has:

- A **“-” button**
- A **text field** that shows the current value
- A **“+” button**

The text field can be edited, so you can type the **starting value** for each counter.  
Each counter works independently, so changing one does not affect the others.

## How it works

- Each counter is built with a composable `CounterRow(label: String)`.
- The current value is stored as a `String` using `remember { mutableStateOf("0") }`.
- When the user presses `+` or `-`, the text is converted to `Int`, changed by ±1,
  and converted back to text.
- When the user types, only empty string or numeric input is accepted
  (`toIntOrNull()` check).

## Files in this folder

- `Main.kt`  
  Contains `MainActivity`, `ThreeCountersScreen`, and `CounterRow` composables.

- `screenshot.png`  
  Screenshot of the app running on an emulator/phone showing the three counters.

- `README.md`  
  This description of the program and how it works.
