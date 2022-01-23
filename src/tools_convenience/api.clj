;
; Copyright © 2021 Peter Monks
;
; Licensed under the Apache License, Version 2.0 (the "License");
; you may not use this file except in compliance with the License.
; You may obtain a copy of the License at
;
;     http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.
;
; SPDX-License-Identifier: Apache-2.0
;

(ns tools-convenience.api
  "Convenience fns for tools.build scripts."
  (:require [clojure.string          :as s]
            [clojure.tools.build.api :as b]))

(defmulti exec
  "Executes the given command line, expressed as either a string or a sequential (vector or list), optionally with other clojure.tools.build.api/process options as a second argument.

  Throws an ExceptionInfo on non-zero status code, containing the entire execution result (from clojure.tools.build.api/process) in the info map."
  {:arglists '([command-line]
               [command-line opts])}
  (fn [& args] (sequential? (first args))))

(defmethod exec true
  ([command-line] (exec command-line nil))
  ([command-line opts]
    (when-let [command-line (seq (remove s/blank? command-line))]
      (let [result (b/process (into {:command-args command-line} opts))]
        (if (not= 0 (:exit result))
          (throw (ex-info (str "Command '" (s/join " " command-line) "' failed (" (:exit result) ").") result))
          result)))))

(defmethod exec false
  ([command-line] (exec command-line nil))
  ([command-line opts]
    (when-not (s/blank? command-line)
      (exec (s/split command-line #"\s+") opts))))

(defn- ensure-command-fn
  [command-name]
  (if-not (s/blank? command-name)
    (try
      (exec ["sh" "-c" (str "command -v " command-name)] {:out :capture :err :capture})   ; We have to use sh rather than command directly because Linux is stupid sometimes...
      true
      (catch clojure.lang.ExceptionInfo ei
        (throw (ex-info (str "Command '" command-name "' was not found.") {} ei))))
    (throw (ex-info (str "No command name provided.") {}))))
(def
  ^{:doc "Ensures that the given command is available (note: POSIX only). Returns true if it exists, throws an exception otherwise.

  Notes:
  * This fn is memoized, so calling it repeatedly with the same command-name will not hurt performance."
    :arglists '([command-name])}
  ensure-command (memoize ensure-command-fn))

(defn clojure
  "Execute clojure reproducibly (-Srepro) with the given args (strings)."
  [& args]
  (ensure-command "clojure")
  (if-let [args (seq (remove s/blank? args))]
    (exec (concat ["clojure" "-J-Dclojure.main.report=stderr" "-Srepro"] args))
    (throw (ex-info "No clojure arguments provided, but they are mandatory." {}))))   ; Attempt to prevent clojure from dropping into a REPL, since that will cause everything to lock

(defn clojure-silent
  "Execute clojure reproducibly (-Srepro) with the given args (strings), capturing and returning stdout and stderr (as a result map as per clojure.tools.build.api/process)."
  [& args]
  (ensure-command "clojure")
  (if-let [args (seq (remove s/blank? args))]
    (exec (concat ["clojure" "-J-Dclojure.main.report=stderr" "-Srepro"] args) {:out :capture :err :capture})
    (throw (ex-info "No clojure arguments provided, but they are mandatory." {}))))   ; Attempt to prevent clojure from dropping into a REPL, since that will cause everything to lock

(defn- safe-name
  "A nil-safe version of `name`.  🙄"
  [x]
  (when x (name x)))

(defn git
  "Execute git with the given args (which can be strings or keywords), capturing and returning the output (stdout only)."
  [& args]
  (ensure-command "git")
  (if-let [args (seq (remove s/blank? (map safe-name args)))]
    (s/trim (str (:out (exec (concat ["git"] args) {:out :capture}))))
    (throw (ex-info "No git arguments provided, but they are mandatory." {}))))

(defn git-current-branch
  "The current git branch."
  []
  (git :branch "--show-current"))

(defn git-current-commit
  "The sha of the current commit."
  []
  (git :show "-s" "--format=%H"))

(defn git-exact-tag
  "Returns the exact tag for the given sha (or current commit sha if not provided), or nil if there is no tag for that sha."
  ([]    (try (git :describe "--tags" "--exact-match")     (catch clojure.lang.ExceptionInfo _ nil)))
  ([sha] (try (git :describe "--tags" "--exact-match" sha) (catch clojure.lang.ExceptionInfo _ nil))))

(defn git-nearest-tag
  "The nearest tag to the current commit."
  []
  (git :describe "--abbrev=0"))

(defn git-tag-commit
  "Returns the commit sha for the given tag, or nil if the tag doesn't exist."
  [tag]
  (try (git :rev-list "-n" "1" tag) (catch clojure.lang.ExceptionInfo _ nil)))

(defn git-remote
  "The URL of the origin server (if any), without the trailing .git extension"
  []
  (try
    (let [repo (s/replace (git :config "--get" "remote.origin.url") ".git" "")]
      (when-not [s/blank? repo] repo))
    (catch clojure.lang.ExceptionInfo _ nil)))
