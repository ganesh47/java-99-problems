# Implementation Plan: specs.java-99-bootstrap

**Branch**: `specs-java-99-bootstrap` | **Date**: 2026-01-02 | **Spec**: specs/java-99-bootstrap.feature.md  
**Input**: Spec doc + spec-kit templates (`specs templates` or `specs scan --refresh-spec-kit`)

## Summary
Introduce specs to java-99-problems with baseline scaffolding, Codex wrappers, spec-kit checks, and GitHub automation (guard/coverage/security/release). Ensure ADR/wiki links are captured in issue #80 and guardrails enforce approval and PR hygiene.

## Technical Context
**Language/Version**: Java (Maven) + Node (specs tooling)  
**Primary Dependencies**: specs CLI, spec-kit templates, GitHub Actions, GitHub CLI  
**Testing**: Maven CI (existing build), spec coverage workflow, security scans  
**Target Platform**: GitHub public repo with Specs Board integration  
**Constraints**: ADR approval required before implementation; release required before close

## Project Structure
```
specs/
├── java-99-bootstrap.feature.md
├── java-99-bootstrap.plan.md
└── java-99-bootstrap.tasks.md
.specs/
└── spec-kit/           # fetched templates
.codex/commands/        # specs wrappers
scripts/check-spec-kit.js
```

## Implementation Strategy
1) Add .specs.yml and baseline spec; enable labels and discussions/wiki for sync.
2) Install Codex wrappers and spec-kit check script; ensure `npm run spec-kit:check` works.
3) Add guard, coverage/security, and release workflows; keep existing Java build intact.
4) Fetch spec-kit templates and commit `.specs/spec-kit`.
5) Run `specs sync` to create issue/ADR/wiki links and update Specs Board when available.
6) Use PR template with issue/ADR/wiki links and required checks; keep guard green.
7) Release: bump minor version + run release workflow, then `specs close`.

## Dependencies & Execution Order
- Discussions/wiki enabled before sync.
- Workflows added before requesting approval/merging.
- Release workflow requires CHANGELOG entry.

## Notes
- Wiki repo may need manual initialization if GitHub does not create it on enablement.
- Specs Board uses project number 6 for ganesh47.
