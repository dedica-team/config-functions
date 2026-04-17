# Actions

This folder contains actions that can be reused in different GitHub Action workflows.

They are referenced as steps:

```yaml
# [...]
steps:
  # [...]
  - name: Build Jar
    uses: ./.github/workflows/actions/run-maven
```

Further information:

- https://docs.github.com/en/actions/creating-actions/creating-a-composite-action
- https://docs.github.com/en/actions/creating-actions/metadata-syntax-for-github-actions#runs-for-composite-actions
