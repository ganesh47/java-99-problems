---
spec_id: specs.java-99-bootstrap
title: Bootstrap specs for java-99-problems
features:
  - id: repo.creation
    accept:
      - "Repo has .specs.yml and baseline spec scaffold wired to Specs Board and labels spec/feature"
      - "Specs directory uses markdown+yaml front matter and paths specs/**/*.feature.md"
  - id: codex.enablement
    accept:
      - ".codex/commands includes specs wrappers (specs, spec-next, spec-sync, spec-coverage, approve)"
      - "Codex context paths include src/main/java and src/test/java"
  - id: spec-kit.templates
    accept:
      - "spec-kit templates fetched via `specs templates` or `specs scan --refresh-spec-kit` and recorded in context"
      - "`npm run spec-kit:check` exists and is referenced by CI/doctor guidance"
  - id: gh.automation
    accept:
      - "Workflows for guard, coverage, security scans, and release are added/enabled in this repo"
      - "Specs sync/close updates project Status (Todo/In Progress/Done) when a Specs Board exists"
  - id: adr.wiki.flow
    accept:
      - "Sync creates ADR discussion + wiki page for this spec and stores links in the issue body"
      - "Guardrails require ADR URL + approval marker, wiki URL, open issue, and no CHANGES_REQUESTED reviews before merge"
---

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
