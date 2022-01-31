| | | | |
|---:|:---:|:---:|:---:|
| [**main**](https://github.com/pmonks/tools-convenience/tree/main) | [![CI](https://github.com/pmonks/tools-convenience/workflows/CI/badge.svg?branch=main)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3ACI+branch%3Amain) | [![Dependencies](https://github.com/pmonks/tools-convenience/workflows/dependencies/badge.svg?branch=main)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3Adependencies+branch%3Amain) |
| [**dev**](https://github.com/pmonks/tools-convenience/tree/dev) | [![CI](https://github.com/pmonks/tools-convenience/workflows/CI/badge.svg?branch=dev)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3ACI+branch%3Adev) | [![Dependencies](https://github.com/pmonks/tools-convenience/workflows/dependencies/badge.svg?branch=dev)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3Adependencies+branch%3Adev) |

[![Latest Version](https://img.shields.io/clojars/v/com.github.pmonks/tools-convenience)](https://clojars.org/com.github.pmonks/tools-convenience/) [![Open Issues](https://img.shields.io/github/issues/pmonks/tools-convenience.svg)](https://github.com/pmonks/tools-convenience/issues) [![License](https://img.shields.io/github/license/pmonks/tools-convenience.svg)](https://github.com/pmonks/tools-convenience/blob/main/LICENSE)

# tools-convenience

Various convenience fns for [tools.build](https://github.com/clojure/tools.build)-based build scripts.

**NOTE: This library almost certainly won't work on "raw" Windows, and it is unknown if it works on [WSL](https://docs.microsoft.com/en-us/windows/wsl/about).**

## Using the library

### Dependency

Include a maven dependency on the library in your `deps.edn`, for a build tool alias:

```edn
  :aliases
    :build
      {:deps       {com.github.pmonks/tools-convenience {:mvn/version "LATEST_CLOJARS_VERSION"}}
       :ns-default your.build.ns}
```

### Require the Namespace

```clojure
(ns your.build.ns
  (:require [tools-convenience.api :as tc]))
```

### API Documentation

[API documentation is available here](https://pmonks.github.io/tools-convenience/).

## Contributor Information

[Contributing Guidelines](https://github.com/pmonks/tools-convenience/blob/main/.github/CONTRIBUTING.md)

[Bug Tracker](https://github.com/pmonks/tools-convenience/issues)

[Code of Conduct](https://github.com/pmonks/tools-convenience/blob/main/.github/CODE_OF_CONDUCT.md)

### Developer Workflow

This project uses the [git-flow branching strategy](https://nvie.com/posts/a-successful-git-branching-model/), with the caveat that the permanent branches are called `main` and `dev`, and any changes to the `main` branch are considered a release and auto-deployed (JARs to Clojars, API docs to GitHub Pages, etc.).

For this reason, **all development must occur either in branch `dev`, or (preferably) in temporary branches off of `dev`.**  All PRs from forked repos must also be submitted against `dev`; the `main` branch is **only** updated from `dev` via PRs created by the core development team.  All other changes submitted to `main` will be rejected.

## License

Copyright © 2021 Peter Monks

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

SPDX-License-Identifier: [Apache-2.0](https://spdx.org/licenses/Apache-2.0)
