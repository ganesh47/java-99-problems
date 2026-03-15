# Agent Context: java-99-problems

## Overview
- Java implementations of the classic "99 Problems" kata, ported from the original Prolog/Lisp versions.
- Focus on incremental learning: each problem has a dedicated class under `org.nintynine.problems` plus a comprehensive JUnit 5 test suite showing expected behaviour and edge cases.

## Tech Stack & Tooling
- Java 22, built with Maven (`pom.xml` configures compiler 22 release).
- Test framework: JUnit Jupiter 5.13.0-M3 (parameterized tests used widely).
- Quality gates enabled during `mvn verify`: Checkstyle (Google rules), PMD (targetJdk 22), SpotBugs (with `spotbugs-exclude.xml`), and JaCoCo coverage.
- CI hooks to SonarCloud and GitHub Actions (see badges in `README.md`).

## Code Layout
- `src/main/java/org/nintynine/problems`
  - `MyList` is the base generic list wrapper implementing a simple `Streamable` interface; provides P01 (`last`) and shared utilities.
  - `MyListP02`–`MyListP28` solve the list-focused problems. Later classes often extend earlier ones (e.g. `MyListP07 extends MyListP06`) to reuse helper methods.
  - `MathP31`–`MathP43` cover arithmetic/number theory tasks using static utility methods (prime checks, totients, combinatorics, arithmetic puzzles).
  - `TruthP46`–`TruthP55` implement logic/truth-table problems with expression parsing and encoding helpers.
  - `BTree54`/`BTreeP56`–`BTreeP61` tackle binary tree construction, traversal, and balancing; internal `Node` classes encapsulate structure/equality rules.
  - `package-info.java` keeps everything inside the single package namespace.
- `src/test/java/org/nintynine/problems` mirrors the main package 1:1; each problem’s tests document usage patterns, guard rails, and corner cases (including exhaustive combinations where practical).
- `target/` is Maven’s build output (ignore for edits).

## Testing & Commands
- Run unit tests: `./mvnw test`
- Full verification (tests + static analysis + coverage): `./mvnw verify`
- `README.md` reiterates the project goal, structure, and test philosophy.

## Conventions & Tips
- Stick with the naming convention `DomainPXX` for new problems and place matching `DomainPXXTest` under tests.
- When extending list problems, prefer subclassing the previous implementation to inherit helper behaviour (see `MyListP07` for flattening).
- Math/logic/tree problems favour static factory/utility methods; keep signatures simple and pure for easy unit testing.
- Tests double as documentation—consult them first when changing behaviour or adding features.
- Keep code checkstyle-compliant; run `mvn verify` locally if you touch formatting-sensitive files.
