# Tasks: specs.java-99-bootstrap

**Input**: spec (`specs/java-99-bootstrap.feature.md`), plan (`specs/java-99-bootstrap.plan.md`), spec-kit templates  
**Prerequisites**: ADR #79 approved; templates refreshed

## Format: `[ID] [P?] [Story] Description`

## Phase 1: Repo scaffolding
- [ ] T001 [P] [BOOT] Add `.specs.yml` with Specs Board project + labels.
- [ ] T002 [P] [BOOT] Add baseline spec files under `specs/`.
- [ ] T003 [P] [BOOT] Create labels `spec` and `feature`; enable discussions/wiki for ADR and docs.

## Phase 2: Codex + spec-kit
- [ ] T010 [P] [BOOT] Add `.codex/commands` wrappers (specs, spec-next, spec-sync, spec-coverage, approve).
- [ ] T011 [P] [BOOT] Add `scripts/check-spec-kit.js` and `package.json` with `spec-kit:check`.
- [ ] T012 [P] [BOOT] Fetch spec-kit templates (`specs templates`) and commit `.specs/spec-kit`.

## Phase 3: GitHub automation
- [ ] T020 [BOOT] Add guard workflow (issue/ADR/wiki/approval/no pending review comments).
- [ ] T021 [BOOT] Add specs coverage + security scans workflow.
- [ ] T022 [BOOT] Add release workflow with inputs for version/ADR/issue/wiki and changelog enforcement.
- [ ] T023 [BOOT] Add spec-specific PR template for this spec.

## Phase 4: Sync and approval
- [ ] T030 [BOOT] Run `specs sync` to create issue/ADR/wiki and update Specs Board.
- [ ] T031 [BOOT] Update ADR #79 with plan/tasks and approval marker.
- [ ] T032 [BOOT] Ensure PR checks green using spec template.

## Phase 5: Release + close
- [ ] T040 [BOOT] Add/update CHANGELOG entry and bump minor version.
- [ ] T041 [BOOT] Run release workflow (workflow_dispatch) with version/ADR/issue/wiki inputs.
- [ ] T042 [BOOT] Run `specs close specs.java-99-bootstrap --issue 80 --pr <#>` after release + green checks.

## Dependencies & Execution Order
- Phase 1 → Phase 2 → Phase 3 → Phase 4 → Phase 5.
