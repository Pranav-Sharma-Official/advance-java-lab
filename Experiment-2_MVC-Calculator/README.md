# Experiment 2 — MVC Calculator

A desktop calculator built with Java Swing, structured using the **Model-View-Controller (MVC)** design pattern.

## What It Does

The application performs five arithmetic operations — addition, subtraction, multiplication, division, and percentage — on two numeric inputs. The UI, business logic, and control flow are cleanly separated across three classes, making this a practical example of applying MVC in a Swing application.

## Technologies Used

- Java Swing for the UI
- MVC architectural pattern

## Project Structure

```
Experiment-2_MVC-Calculator/
├── Main.java          # Entry point — creates and wires the MVC components
├── Cviews.java        # View — the Swing UI (input fields, buttons, result display)
├── Cmodels.java       # Model — arithmetic logic (add, sub, mul, div, per)
└── Ccontroller.java   # Controller — handles button events, calls model, updates view
```

### Component Breakdown

| Class | Role |
|---|---|
| `Cviews` | Renders the form with two number inputs, operation buttons, and a result field |
| `Cmodels` | Pure logic — takes two doubles and returns the computed result |
| `Ccontroller` | Listens for button clicks, reads inputs from the view, delegates to the model, and pushes results back |

## Prerequisites

- JDK 8 or higher

## How to Run

1. **Compile** all files from the experiment directory:

```bash
javac Main.java Ccontroller.java Cmodels.java Cviews.java
```

2. **Run** the application:

```bash
java Main
```

The calculator window will open. Enter two numbers and click any operation button to see the result.
