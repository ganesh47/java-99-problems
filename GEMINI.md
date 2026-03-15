# GEMINI.md - java-99-problems

This file serves as the foundational mandate for all AI agent interactions within the `java-99-problems` repository. It defines the project's identity, technical standards, and the Spec-Driven Development (SDD) workflow that must be strictly followed.

## 🎯 Project Identity
The `java-99-problems` project is a high-fidelity implementation of the classic "99 Problems" (originally from Prolog/Lisp) in modern Java. It prioritizes:
- **Incremental Learning**: Each problem is a self-contained unit building on previous solutions.
- **Type Safety**: Extensive use of Java generics.
- **Code Quality**: Rigorous static analysis and 100% (or near-total) test coverage.
- **Documentation**: Tests are treated as the primary source of documentation and behavioral truth.

## 🛠 Technical Stack
- **Runtime**: Java 24 (OpenJDK/Temurin).
- **Build System**: Maven (`mvnw`).
- **Testing**: JUnit Jupiter 5.13.0-M3.
- **Quality Gates**:
  - **Checkstyle**: Google Java Style.
  - **PMD**: Targeted at JDK 23 (due to PMD tool limitations).
  - **SpotBugs**: With custom excludes in `spotbugs-exclude.xml`.
  - **JaCoCo**: For coverage reporting.
- **Process Tooling**: Spec-Kit and Codex wrappers (located in `.codex/` and `.specs/`).

## 🔄 Spec-Driven Development (SDD) Workflow
This repository follows **Spec-Driven Development**. Specifications are the source of truth; code is the generated expression of those specs.

### 1. Research & Specification (`.feature.md`)
- Define the **WHAT** and **WHY**.
- Avoid implementation details (**HOW**).
- Identify edge cases and acceptance criteria.
- Use `[NEEDS CLARIFICATION]` for ambiguities.

### 2. Implementation Planning (`.plan.md`)
- Translate requirements into technical architecture.
- Define data models, API contracts, and test scenarios.
- Adhere to the **Constitution** (Simplicity, Anti-Abstraction, Library-First).

### 3. Task Execution (`.tasks.md`)
- Break the plan into granular, executable tasks.
- Follow the **Test-First Imperative**: No implementation code before failing tests are written and verified.

## 📏 Engineering Standards

### Code Conventions
- **Naming**: `DomainPXX.java` for implementations and `DomainPXXTest.java` for tests.
- **Package**: All core logic resides in `org.nintynine.problems`.
- **Inheritance**: Subclass previous solutions (e.g., `MyListP07 extends MyListP06`) to reuse helper logic where appropriate.
- **Statics**: Use static utility methods for Math, Logic, and Tree problems where state is not required.

### Testing Mandates
- **TDD is Non-Negotiable**: Write tests first, ensure they fail, then implement.
- **Coverage**: Every path, edge case, and error condition must be tested.
- **Mocks**: Prefer real environments/objects over mocks (Integration-First).

### Quality Assurance
- **Verification**: Always run `./mvnw verify` before considering a task complete. This runs tests, checkstyle, PMD, and SpotBugs.
- **Zero Warnings**: New code must not introduce new static analysis warnings.

## 🤖 Operational Guidelines for AI Agents
- **Context Efficiency**: Use `grep_search` to find existing patterns before implementing new ones.
- **SDD Alignment**: Check `.specs/current-task.md` to understand the current focus.
- **Tool Usage**: Use `./mvnw` for all build/test operations.
- **No Reversions**: Do not revert changes unless they break the build or are explicitly requested.
- **Proactive Opinion**: Suggest better implementation patterns if they align with the project's goal of "clean, idiomatic Java."
