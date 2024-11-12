;
; Copyright © 2022 Peter Monks
;
; This Source Code Form is subject to the terms of the Mozilla Public
; License, v. 2.0. If a copy of the MPL was not distributed with this
; file, You can obtain one at https://mozilla.org/MPL/2.0/.
;
; SPDX-License-Identifier: MPL-2.0
;

(ns tools-convenience.api-test
  (:require [clojure.test          :refer [deftest testing is]]
            [clojure.string        :as s]
            [tools-convenience.api :refer [exec ensure-command clojure-silent git]]))

(deftest exec-tests
  (println)
  (println "###########################################################")
  (println "#                                                         #")
  (println "#  NOTE: These tests write to stdout - this is expected!  #")
  (println "#                                                         #")
  (println "###########################################################")
  (println)
  (flush)
  (testing "Nil, empty or blank commands"
    (is (nil?                        (exec nil)))
    (is (nil?                        (exec "")))
    (is (nil?                        (exec "       ")))
    (is (nil?                        (exec "\n")))
    (is (nil?                        (exec "\t")))
    (is (nil?                        (exec [])))
    (is (nil?                        (exec [nil])))
    (is (nil?                        (exec ["       "])))
    (is (nil?                        (exec [""])))
    (is (nil?                        (exec ["\n"])))
    (is (nil?                        (exec ["\t"])))
    (is (nil?                        (exec [nil "       " "" "\n" "\t"]))))
  (testing "Invalid commands"
    (is (thrown? java.io.IOException (exec "invalid-command-name-that-does-not-exist"))))
  (testing "Valid commands"
    (is (not (nil?                   (exec "ls")))))          ; Note: output appears on stdout
  (testing "Valid commands, capturing stdout"
    (is (not (s/blank?               (:out (exec "ls" {:out :capture}))))))
  (testing "Valid commands, with args"
    (is (not (nil?                   (exec "ls -al"  {:out :capture})))))
    (is (not (nil?                   (exec ["ls" "-al"] {:out :capture}))))
    (is (=                           (:out (exec "ls -al" {:out :capture})) (:out (exec ["ls" "-al"] {:out :capture})))))

(deftest ensure-command-tests
  (testing "Nil, empty or blank commands"
    (is (thrown? clojure.lang.ExceptionInfo (ensure-command nil)))
    (is (thrown? clojure.lang.ExceptionInfo (ensure-command "")))
    (is (thrown? clojure.lang.ExceptionInfo (ensure-command "       ")))
    (is (thrown? clojure.lang.ExceptionInfo (ensure-command "\n")))
    (is (thrown? clojure.lang.ExceptionInfo (ensure-command "\t"))))
  (testing "Non-existent commands"
    (is (thrown? clojure.lang.ExceptionInfo (ensure-command "invalid-command-name-that-does-not-exist"))))
  (testing "Extant commands"
    (is                                     (ensure-command "ls"))
    (is                                     (ensure-command "cat"))
    (is                                     (ensure-command "git"))
    (is                                     (ensure-command "clojure"))))

(deftest clojure-tests
  (testing "Nil, empty or blank args"
    (is (thrown? clojure.lang.ExceptionInfo (clojure-silent nil)))
    (is (thrown? clojure.lang.ExceptionInfo (clojure-silent "")))
    (is (thrown? clojure.lang.ExceptionInfo (clojure-silent "       ")))
    (is (thrown? clojure.lang.ExceptionInfo (clojure-silent "\n")))
    (is (thrown? clojure.lang.ExceptionInfo (clojure-silent "\t")))
    (is (thrown? clojure.lang.ExceptionInfo (clojure-silent nil "" "       " "\n" "\t"))))
  (testing "Invalid arguments"
    (is (thrown? clojure.lang.ExceptionInfo (clojure-silent "-X:invalid-alias")))
    (is (thrown? clojure.lang.ExceptionInfo (clojure-silent "invalid-clojure-file-to-run"))))
  (testing "Valid arguments"
    (is (not (nil?                          (clojure-silent "-Sdescribe"))))
    (is (not (nil?                          (clojure-silent "-Spath"))))))

(deftest git-tests
  (testing "Nil, empty or blank args"
    (is (thrown? clojure.lang.ExceptionInfo (git nil)))
    (is (thrown? clojure.lang.ExceptionInfo (git "")))
    (is (thrown? clojure.lang.ExceptionInfo (git "       ")))
    (is (thrown? clojure.lang.ExceptionInfo (git "\n")))
    (is (thrown? clojure.lang.ExceptionInfo (git "\t")))
    (is (thrown? clojure.lang.ExceptionInfo (git nil "" "       " "\n" "\t"))))
  (testing "Invalid arguments"
    (is (thrown? clojure.lang.ExceptionInfo (git "--INVALID_GIT_ARG")))       ; Note: this test, and the subsequent one, produce a lot of output to stderr that _appears_ to be a test failure but is not.
    (is (thrown? clojure.lang.ExceptionInfo (git "invalid-git-command"))))
  (testing "Valid arguments"
    (is (not (nil?                          (git "config" "-l"))))     ; We use `git config` because it works anywhere - whether CWD is a git repo or not
    (is (not (nil?                          (git :config "-l"))))
    (is (=                                  (git "config" "-l") (git :config "-l")))))
