# Implementation Plan: java24.upgrade

**Branch**: `java24-upgrade` | **Date**: 2026-01-03 | **Spec**: specs/java-99-java24-upgrade.feature.md  
**Input**: Spec doc + spec-kit templates (`specs templates` or `specs scan --refresh-spec-kit`)

## Summary
Set Java 24 as the default toolchain by updating Maven properties and plugin configs, keep CI green on Java 24, document the change in CHANGELOG.md, and ship a v0.2.0 release.

## Technical Context
**Language/Version**: Java (Maven), target baseline 24  
**Primary Dependencies**: Maven compiler plugin, PMD, SpotBugs, GitHub Actions  
**Testing**: Maven CI workflow (build.yml), specs guard/coverage/security  
**Target Platform**: GitHub repo with Specs Board + release workflow

## Project Structure
```
specs/
├── java-99-java24-upgrade.feature.md
├── java-99-java24-upgrade.plan.md
└── java-99-java24-upgrade.tasks.md
pom.xml
.github/workflows/build.yml
CHANGELOG.md
```

## Implementation Strategy
1) Create spec + plan/tasks; run `specs sync` to open issue/ADR/wiki.
2) Approve ADR before implementation.
3) Update pom defaults: define java.version=24 and align compiler/PMD configs to use it.
4) Ensure CI expectations for Java 24 remain green; document any 24-specific skips.
5) Add v0.2.0 entry to CHANGELOG.md and run release workflow with issue/ADR/wiki links.
6) Run `specs sync` and `specs close` after release and green checks.

## Dependencies & Execution Order
- ADR approval before implementation.
- Release requires CHANGELOG.md entry for v0.2.0.

## Notes
- Use the spec-specific PR template with issue/ADR/wiki links.
