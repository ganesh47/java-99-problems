# Current Spec Task

Spec: specs.java-99-bootstrap
Feature: repo.creation
File: /home/ganesh/projects/java-99-problems/specs/java-99-bootstrap.feature.md

## Body
## Summary
Adopt specs in java-99-problems (Maven/Java) with the same guardrails and automation used in the specs toolkit: spec-kit templates, Codex wrappers, ADR/wiki automation, and CI checks. This makes future work trackable via Specs Board and ensures releases/merges are gated by ADR approval and required checks.

## Workflow
- Init: add `.specs.yml`, baseline spec in `specs/`, and Codex wrappers in `.codex/commands`.
- Templates: run `specs templates` or `specs scan --refresh-spec-kit` and keep `.specs/spec-kit` up to date.
- Sync: `specs sync` creates issue + ADR + wiki links; guard workflow enforces links and approvals.
- Implement: use specs tasks to introduce guard/coverage/security/release workflows and spec-kit check script.
- Release/close: bump minor version + run release workflow before `specs close`; ensure no pending review comments.

## Notes
- Repo already has a Java build workflow; specs workflows should coexist without disrupting Maven CI.