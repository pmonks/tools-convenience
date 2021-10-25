| | | |
|---:|:---:|:---:|
| [**main**](https://github.com/pmonks/tools-convenience/tree/main) | [![Lint](https://github.com/pmonks/tools-convenience/workflows/lint/badge.svg?branch=main)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3Alint) | [![Dependencies](https://github.com/pmonks/tools-convenience/workflows/dependencies/badge.svg?branch=main)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3Adependencies) |
| [**dev**](https://github.com/pmonks/tools-convenience/tree/dev)  | [![Lint](https://github.com/pmonks/tools-convenience/workflows/lint/badge.svg?branch=dev)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3Alint) | [![Dependencies](https://github.com/pmonks/tools-convenience/workflows/dependencies/badge.svg?branch=dev)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3Adependencies) |

[![Latest Version](https://img.shields.io/clojars/v/com.github.pmonks/tools-convenience)](https://clojars.org/com.github.pmonks/tools-convenience/) [![Open Issues](https://img.shields.io/github/issues/pmonks/tools-convenience.svg)](https://github.com/pmonks/tools-convenience/issues) [![License](https://img.shields.io/github/license/pmonks/tools-convenience.svg)](https://github.com/pmonks/tools-convenience/blob/main/LICENSE)

# tools-convenience

Various convenience fns for tools.build build scripts.

## Using the library

### Dependency

Include a maven dependency on the library in your `deps.edn`, for a build tool alias:

```edn
  :aliases
    :build
      {:deps       {com.github.pmonks/tools-convenience {:mvn/version "LATEST_CLOJARS_VERSION"}}
       :ns-default your.build.ns}
```

### API Documentation

[API documentation is available here](https://pmonks.github.io/tools-convenience/).

## Contributor Information

[Contributing Guidelines](https://github.com/pmonks/tools-convenience/blob/main/.github/CONTRIBUTING.md)

[Bug Tracker](https://github.com/pmonks/tools-convenience/issues)

[Code of Conduct](https://github.com/pmonks/tools-convenience/blob/main/.github/CODE_OF_CONDUCT.md)

### Developer Workflow

The repository has two permanent branches: `main` and `dev`.  **All development must occur either in branch `dev`, or (preferably) in feature branches off of `dev`.**  All PRs must also be submitted against `dev`; the `main` branch is **only** updated from `dev` via PRs created by the core development team.  All other changes submitted to `main` will be rejected.

This model allows otherwise unrelated changes to be batched up in the `dev` branch, integration tested there, and then released en masse to the `main` branch, which will trigger automated generation and deployment of the release (Codox docs to github.io, JARs to Clojars, etc.).

## License

Copyright © 2021 Peter Monks

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

SPDX-License-Identifier: [Apache-2.0](https://spdx.org/licenses/Apache-2.0)
