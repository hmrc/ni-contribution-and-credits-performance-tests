
# ni-contribution-and-credits-performance-tests

Performance test suite for the `national-insurance-contribution-and-credits-api`, using [performance-test-runner](https://github.com/hmrc/performance-test-runner) under the hood.

## Pre-requisites

### Services

Start `NATIONAL_INSURANCE_CONTRIBUTION_AND_CREDITS_ALL` services as follows:

```bash
sm2 --start NATIONAL_INSURANCE_CONTRIBUTION_AND_CREDITS_ALL
```

## Tests

Run smoke test (locally) as follows:

```bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=true Gatling/test
```

Run full performance test (locally) as follows:

```bash
sbt -DrunLocal=true Gatling/test
```

Run smoke test (staging) as follows:

```bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=false Gatling/test
```

## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
