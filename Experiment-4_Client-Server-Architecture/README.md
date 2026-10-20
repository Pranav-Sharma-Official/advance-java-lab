# Experiment 4 — Client-Server Architecture

A basic TCP client-server application demonstrating socket programming in Java.

## What It Does

The **server** listens on port `5000` for incoming connections. Once a client connects, it reads the client's message, prints it, and sends back a response. The **client** connects to `localhost:5000`, sends the string `"Hello Server"`, and prints whatever the server replies.

This is a straightforward example of how `Socket` and `ServerSocket` work together for two-way communication over TCP.

## Technologies Used

- `java.net.ServerSocket` / `java.net.Socket`
- `java.io.BufferedReader` / `java.io.PrintWriter` for stream-based I/O

## Project Structure

```
Experiment-4_Client-Server-Architecture/
├── server.java    # Listens on port 5000 and responds to clients
└── client.java    # Connects to the server and sends a message
```

## Prerequisites

- JDK 8 or higher
- Both files compiled and run on the same machine (or update the host in `client.java` for a remote server)

## How to Run

You'll need two terminal windows.

**Step 1 — Compile both files:**

```bash
javac server.java client.java
```

**Step 2 — Start the server** (in Terminal 1):

```bash
java server
```

The server will block and wait for a connection.

**Step 3 — Run the client** (in Terminal 2):

```bash
java client
```

The client sends `"Hello Server"` to the server. You should see the message appear in the server terminal, and the server's reply appear in the client terminal.
