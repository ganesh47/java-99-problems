---
spec_id: java24.upgrade
title: Upgrade Java Baseline to 24
features:
  - id: toolchain.java24
    accept:
      - "pom.xml defines java.version=24 and compiler/PMD configs consume the property"
      - "mvnw verify defaults to Java 24 when no -Djava.version override is supplied"
  - id: ci.java24
    accept:
      - "CI builds include Java 24 and pass with -Djava.version=24"
      - "Java 24-specific skips (if any) are documented and keep checks green"
  - id: examples.java24
    accept:
      - "Problem implementations and tests compile/run under Java 24 in CI"
  - id: docs.release
    accept:
      - "CHANGELOG.md contains a v0.2.0 entry for the Java 24 upgrade"
      - "Release workflow publishes v0.2.0 with coverage artifact and ADR/wiki links"
  - id: spec.workflow
    accept:
      - "Spec issue/ADR/wiki links created via specs sync and ADR approved before implementation"
      - "PR uses the spec template with issue/ADR/wiki links and required checks green"
---

## Summary
Move the java-99-problems baseline to Java 24 by updating the Maven toolchain defaults and CI expectations. This keeps local builds consistent with CI, and documents the change via changelog and release automation.

## Workflow
- Todo → In Progress: create spec, sync issue/ADR/wiki, and approve ADR.
- Implementation: update pom toolchain defaults to Java 24 and align plugin configs; keep CI green.
- Release: add changelog entry and run release workflow for v0.2.0.
- Done: sync and close the spec after release and green checks.

## Notes
- CI already runs Java 24; this spec makes Java 24 the default without overrides.
- If any plugin requires a temporary Java 24 skip, document it in the workflow.
