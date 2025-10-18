# Parallelism & Concurrency Demos in Java

This repository demonstrates **Java concurrency and parallelism concepts** using simple, real-world-like examples. It is meant for **learning, reference, or demo purposes**.

Two main demos are included:
1. **Demo 1:** Compare **single-threaded**, **concurrent**, and **parallel** execution.
2. **Demo 2:** Explore how increasing thread count affects performance in **parallel execution**.

---

## Project Structure
```
src/main/java/samples/roosafeed/
├─ common/ # Shared utilities & interfaces
│ ├─ Utils.java # Warmup, dataset generation, timing helpers
│ └─ TaskRunner.java # Common interface for tasks
│ └─ Warmable.java # Common interface for any object that needs to be warmed up
├─ demo1/ # Demo 1 
│ └─ Demo1Main.java
├─ demo2/ # Demo 2 
│ └─ Demo2Main.java
```

---

## Prerequisites

- Java 21+
- Maven 3.x+
- IDE (IntelliJ IDEA recommended) or any online IDE that supports Maven

---

## Running the Demos

### Option 1: Using IntelliJ

- Open the project in IntelliJ IDEA.
- Two run configurations are already included:
    1. **Run Demo 1:** runs `Demo1Main` (single vs concurrent vs parallel)
    2. **Run Demo 2:** runs `Demo2Main` (thread multiplier exploration)
- Each configuration uses: `-Xms2g -Xmx4g` JVM heap settings to support large datasets.

### Option 2: Using Maven

Run Demo 1:

```bash
mvn exec:java@demo1
```

Run Demo 2:

```bash
mvn exec:java@demo2
```

---

## Heap and Performance Considerations

The demos operate on large datasets (~50 million integers).
Recommended JVM heap settings:
- -Xms2g (initial heap)
- -Xmx4g (maximum heap)
Adjust if using smaller/larger datasets.

---

## Contributing

Anyone can use or contribute.
Feel free to add:
- New task implementations
- Additional demos
- Optimizations for performance or clarity

---

This project is provided for educational purposes.
