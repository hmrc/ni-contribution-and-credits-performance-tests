#!/usr/bin/env bash
sbt -Dperftest.runSmokeTest=true -Dperftest.runLocal=true gatling:test