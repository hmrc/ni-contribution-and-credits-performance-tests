/*
 * Copyright 2023 HM Revenue & Customs
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

import io.gatling.core.Predef.scenario
import uk.gov.hmrc.perftests.nicc.NICCRequests.postNICC

package object Scenarios {

  def niccJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 1
    val nicc = scenario("Retrieve niContribution,niCredit for ni number , Start tax year Date and End tax year Date")
      .exec(
        postNICC
      )
    ScenarioDefinition(nicc, load)
  }

}
