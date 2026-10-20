# Experiment 5 — RMI Calculator

A distributed calculator application built with **Java RMI (Remote Method Invocation)**, where a client calls arithmetic methods that execute on a remote server.

## What It Does

The server registers a `calculator` object in the RMI registry under the name `"calculator"`. The client looks it up via `rmi://localhost/calculator` and calls `add` and `subtract` methods as if the object were local — but the computation happens on the server side.

## Technologies Used

- Java RMI (`java.rmi.*`)
- `java.rmi.server.UnicastRemoteObject`
- `java.rmi.Naming` for registry lookup and binding

## Project Structure

```
Experiment-5_RMI-Calculator/
├── calculator.java                  # Remote interface defining add() and subtract()
├── calculator_implementation.java   # Server-side implementation of the interface
├── server.java                      # Registers the implementation in the RMI registry
└── client.java                      # Looks up the remote object and calls its methods
```

## Prerequisites

- JDK 8 or higher
- No external dependencies — RMI is part of the standard Java library

## How to Run

You'll need two terminal windows. Run all commands from the experiment directory.

**Step 1 — Compile all files:**

```bash
javac calculator.java calculator_implementation.java server.java client.java
```

**Step 2 — Start the RMI registry** (in Terminal 1):

```bash
rmiregistry
```

Leave this running in the background.

**Step 3 — Start the server** (in Terminal 2):

```bash
java server
```

The server will bind the calculator object to the registry.

**Step 4 — Run the client** (in Terminal 3, or reuse Terminal 2 after the server is ready):

```bash
java client
```

The client will call `add` and `subtract` remotely and print the results.

## Notes

- The RMI registry, server, and client must all be run from the same directory (or have the compiled `.class` files on their classpath) so that the stub classes can be resolved.
- If you see a `Connection refused` error, make sure the RMI registry is running before starting the server.
