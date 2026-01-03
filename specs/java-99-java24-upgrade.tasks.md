# Tasks: java24.upgrade

**Input**: spec (`specs/java-99-java24-upgrade.feature.md`), plan (`specs/java-99-java24-upgrade.plan.md`), spec-kit templates  
**Prerequisites**: ADR approved; templates refreshed

## Format: `[ID] [P?] [Story] Description`

## Phase 1: Spec setup
- [ ] T001 [P] [SPEC] Create spec, plan, and tasks files.
- [ ] T002 [P] [SPEC] Run `specs sync` to create issue/ADR/wiki links.
- [ ] T003 [P] [SPEC] Approve ADR and update with plan/tasks.

## Phase 2: Java 24 baseline
- [ ] T010 [P] [BUILD] Define java.version=24 and align compiler/PMD configs.
- [ ] T011 [P] [BUILD] Confirm CI Java 24 checks remain green; document any 24-specific skips.
- [ ] T012 [P] [BUILD] Add spec-specific PR template with issue/ADR/wiki links.

## Phase 3: Release + close
- [ ] T020 [P] [REL] Add v0.2.0 entry to CHANGELOG.md.
- [ ] T021 [P] [REL] Run release workflow for v0.2.0 (issue/ADR/wiki inputs).
- [ ] T022 [P] [REL] Run `specs close java24.upgrade --issue <#> --pr <#>` after release + green checks.

## Dependencies & Execution Order
- Phase 1 → Phase 2 → Phase 3.
