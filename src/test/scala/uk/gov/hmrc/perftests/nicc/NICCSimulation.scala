/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.nicc

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import uk.gov.hmrc.performance.simulation.{Journey, JourneySetup}

import scala.concurrent.duration._

class NICCSimulation extends Simulation with JourneySetup {

  val scenarioDefinitions: Seq[ScenarioDefinition] =
    Seq(
      Scenarios.niccJourney(runSingleUserJourney)
    )

  println(s"Setting up simulation")

  if (runSingleUserJourney) {
    println(s"'perftest.runSmokeTest' is set to true, ignoring all loads and running with only one user per journey!")
    val injectedBuilders = scenarioDefinitions.map { scenarioDefinition =>
      scenarioDefinition.builder.inject(atOnceUsers(1))
    }

    setUp(injectedBuilders: _*)
      .assertions(global.failedRequests.count.is(0))
  } else {
    setUp(withInjectedLoad(scenarioDefinitions): _*)
      .assertions(global.failedRequests.percent.lte(1))
      .maxDuration(10 minutes)
  }

}

case class ScenarioDefinition(builder: ScenarioBuilder, load: Double) extends Journey {
  def this(scenarioBuilder: ScenarioBuilder) {
    this(scenarioBuilder, 1.0)
  }
}