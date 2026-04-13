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

package uk.gov.hmrc.perftests

import io.gatling.core.Predef._
import uk.gov.hmrc.performance.simulation.JourneySetup

import scala.concurrent.duration._

class ApiUsageSimulation extends Simulation with JourneySetup {

  val benefitEligibilityApiScenarioDefinitions: Seq[ScenarioDefinition] =
    Seq(
      Scenarios.jsaBenefitEligibilityDataJourney(runSingleUserJourney),
      Scenarios.esaBenefitEligibilityDataJourney(runSingleUserJourney),
      Scenarios.maBenefitEligibilityDataJourney(runSingleUserJourney),
      Scenarios.bspBenefitEligibilityDataJourney(runSingleUserJourney),
      Scenarios.gyspBenefitEligibilityDataJourney(runSingleUserJourney),
      Scenarios.searchlightBenefitEligibilityDataJourney(runSingleUserJourney),
      Scenarios.bspBenefitEligibilityDataPaginationJourney(runSingleUserJourney),
      Scenarios.gyspBenefitEligibilityDataPaginationJourney(runSingleUserJourney),
      Scenarios.maBenefitEligibilityDataPaginationJourney(runSingleUserJourney)
    ) ++ Scenarios.paginationBenefitEligibilityDataUnderLoadJourney(runSingleUserJourney) ++ Scenarios
      .underLoadBenefitEligibilityDataJourney(runSingleUserJourney)

  val niccScenarioDefinitions: Seq[ScenarioDefinition] =
    Seq(
      Scenarios.niccJourney(runSingleUserJourney)
    )

  println("Setting up simulation")

  if (runSingleUserJourney) {
    println("'perftest.runSmokeTest' is set to true, ignoring all loads and running with only one user per journey!")
    val injectedBuilders =
      (niccScenarioDefinitions ++ benefitEligibilityApiScenarioDefinitions).map(scenarioDefinition =>
        scenarioDefinition.builder.inject(atOnceUsers(1))
      )

    setUp(injectedBuilders: _*)
      .assertions(global.failedRequests.count.is(0))
  } else {
    setUp(withInjectedLoad(niccScenarioDefinitions ++ benefitEligibilityApiScenarioDefinitions): _*)
      .assertions(global.failedRequests.percent.lte(1))
      .maxDuration(10.minutes)
  }

}
