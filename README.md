| | | |
|---:|:---:|:---:|
| [**release**](https://github.com/pmonks/tools-convenience/tree/release) | [![CI](https://github.com/pmonks/tools-convenience/actions/workflows/ci.yml/badge.svg?branch=release)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3ACI+branch%3Arelease) | [![Dependencies](https://github.com/pmonks/tools-convenience/actions/workflows/dependencies.yml/badge.svg?branch=release)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3Adependencies+branch%3Arelease) |
| [**dev**](https://github.com/pmonks/tools-convenience/tree/dev) | [![CI](https://github.com/pmonks/tools-convenience/actions/workflows/ci.yml/badge.svg?branch=dev)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3ACI+branch%3Adev) | [![Dependencies](https://github.com/pmonks/tools-convenience/actions/workflows/dependencies.yml/badge.svg?branch=dev)](https://github.com/pmonks/tools-convenience/actions?query=workflow%3Adependencies+branch%3Adev) |

[![Latest Version](https://img.shields.io/clojars/v/com.github.pmonks/tools-convenience)](https://clojars.org/com.github.pmonks/tools-convenience/) [![License](https://img.shields.io/github/license/pmonks/tools-convenience.svg)](https://github.com/pmonks/tools-convenience/blob/release/LICENSE) [![Open Issues](https://img.shields.io/github/issues/pmonks/tools-convenience.svg)](https://github.com/pmonks/tools-convenience/issues)

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

[Contributing Guidelines](https://github.com/pmonks/tools-convenience/blob/release/.github/CONTRIBUTING.md)

[Bug Tracker](https://github.com/pmonks/tools-convenience/issues)

[Code of Conduct](https://github.com/pmonks/tools-convenience/blob/release/.github/CODE_OF_CONDUCT.md)

### Developer Workflow

This project uses the [git-flow branching strategy](https://nvie.com/posts/a-successful-git-branching-model/), and the permanent branches are called `release` and `dev`.  Any changes to the `release` branch are considered a release and auto-deployed (JARs to Clojars, API docs to GitHub Pages, etc.).

For this reason, **all development must occur either in branch `dev`, or (preferably) in temporary branches off of `dev`.**  All PRs from forked repos must also be submitted against `dev`; the `release` branch is **only** updated from `dev` via PRs created by the core development team.  All other changes submitted to `release` will be rejected.

### Build Tasks

`tools-convenience` uses [`tools.build`](https://clojure.org/guides/tools_build). You can get a list of available tasks by running:

```
clojure -A:deps -T:build help/doc
```

Of particular interest are:

* `clojure -T:build test` - run the unit tests
* `clojure -T:build lint` - run the linters (clj-kondo and eastwood)
* `clojure -T:build ci` - run the full CI suite (check for outdated dependencies, run the unit tests, run the linters)
* `clojure -T:build install` - build the JAR and install it locally (e.g. so you can test it with downstream code)

Please note that the `deploy` task is restricted to the core development team (and will not function if you run it yourself).

## License

Copyright © 2021 Peter Monks

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

SPDX-License-Identifier: [Apache-2.0](https://spdx.org/licenses/Apache-2.0)
