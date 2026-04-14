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

package uk.gov.hmrc.perftests

import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import uk.gov.hmrc.performance.simulation.Journey
import uk.gov.hmrc.perftests.BenefitEligibilityDataRequests._
import uk.gov.hmrc.perftests.NICCRequests.postNICC

case class ScenarioDefinition(builder: ScenarioBuilder, load: Double) extends Journey {

  def this(scenarioBuilder: ScenarioBuilder) =
    this(scenarioBuilder, 1.0)

}

object Scenarios {

  // Note: Based on system requirements the standard load per second rounded up is:
  // JSA: 1
  // ESA: 7
  // MA: 1
  // BSP: 1
  // GYSP: 2

  def jsaBenefitEligibilityDataJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 1
    val benefitEligibilityData = scenario("Fetch JSA and return")
      .exec(
        postBenefitEligibilityDataRequest("JSA", jsaRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def esaBenefitEligibilityDataJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 7
    val benefitEligibilityData = scenario("Fetch ESA and return")
      .exec(
        postBenefitEligibilityDataRequest("ESA", esaRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def maBenefitEligibilityDataJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 1
    val benefitEligibilityData = scenario("Fetch MA and return")
      .exec(
        postBenefitEligibilityDataRequest("MA", maRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def bspBenefitEligibilityDataJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 1
    val benefitEligibilityData = scenario("Fetch BSP and return")
      .exec(
        postBenefitEligibilityDataRequest("BSP", bspRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def gyspBenefitEligibilityDataJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 2
    val benefitEligibilityData = scenario("Fetch GYSP and return")
      .exec(
        postBenefitEligibilityDataRequest("BSP", gyspRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def searchlightBenefitEligibilityDataJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 7
    val benefitEligibilityData = scenario("Fetch SEARCHLIGHT and return")
      .exec(
        postBenefitEligibilityDataRequest("SEARCHLIGHT", searchlightRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def underLoadBenefitEligibilityDataJourney(smokeTest: Boolean): List[ScenarioDefinition] = {
    // Note: These loads represent triple the systems standard load to stress the system
    val esaload        = 21
    val jsaload        = 3
    val maload         = 3
    val bspload        = 3
    val gyspload       = 6
    val serchLightload = 21

    val searchLightBenefitEligibilityData = scenario("Fetch SEARCHLIGHT underload and return")
      .exec(
        postBenefitEligibilityDataRequest("SEARCHLIGHT", searchlightRequestBody)
      )
    val gyspBenefitEligibilityData = scenario("Fetch GYSP underload and return")
      .exec(
        postBenefitEligibilityDataRequest("GYSP", gyspRequestBody)
      )
    val bspBenefitEligibilityData = scenario("Fetch BSP underload and return")
      .exec(
        postBenefitEligibilityDataRequest("BSP", bspRequestBody)
      )
    val esaBenefitEligibilityData = scenario("Fetch ESA underload and return")
      .exec(
        postBenefitEligibilityDataRequest("ESA", esaRequestBody)
      )
    val maBenefitEligibilityData = scenario("Fetch MA underload and return")
      .exec(
        postBenefitEligibilityDataRequest("MA", maRequestBody)
      )
    val jsaBenefitEligibilityData = scenario("Fetch JSA underload and return")
      .exec(
        postBenefitEligibilityDataRequest("JSA", jsaRequestBody)
      )
    List(
      ScenarioDefinition(esaBenefitEligibilityData, esaload),
      ScenarioDefinition(maBenefitEligibilityData, maload),
      ScenarioDefinition(bspBenefitEligibilityData, bspload),
      ScenarioDefinition(gyspBenefitEligibilityData, gyspload),
      ScenarioDefinition(searchLightBenefitEligibilityData, serchLightload),
      ScenarioDefinition(jsaBenefitEligibilityData, jsaload)
    )
  }

  def bspBenefitEligibilityDataPaginationJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 1
    val benefitEligibilityData = scenario("Fetch BSP pagination and return")
      .exec(
        paginationRequest("BSP", bspRequestWithPagination)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def gyspBenefitEligibilityDataPaginationJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 2
    val benefitEligibilityData = scenario("Fetch GYSP pagination and return")
      .exec(
        paginationRequest("GYSP", gyspRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def maBenefitEligibilityDataPaginationJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 1
    val benefitEligibilityData = scenario("Fetch MA pagination and return")
      .exec(
        paginationRequest("MA", maRequestWithPagination)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def paginationBenefitEligibilityDataUnderLoadJourney(smokeTest: Boolean): List[ScenarioDefinition] = {
    // Note: These loads represent triple the systems standard load to stress the system
    val maload   = 3
    val bspload  = 3
    val gyspload = 6
    val maBenefitEligibilityData = scenario("Fetch MA pagination under load and return")
      .exec(
        paginationRequest("MA", maRequestWithPagination)
      )
    val bspBenefitEligibilityData = scenario("Fetch BSP pagination under load and return")
      .exec(
        paginationRequest("BSP", bspRequestWithPagination)
      )
    val gyspBenefitEligibilityData = scenario("Fetch GYSP pagination under load and return")
      .exec(
        paginationRequest("GYSP", gyspRequestWithPagination)
      )
    List(
      ScenarioDefinition(maBenefitEligibilityData, maload),
      ScenarioDefinition(bspBenefitEligibilityData, bspload),
      ScenarioDefinition(gyspBenefitEligibilityData, gyspload)
    )
  }

  // old BSP scenario definition
  def niccJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 1
    val nicc = scenario("Retrieve niContribution,niCredit for ni number , Start tax year Date and End tax year Date")
      .exec(
        postNICC
      )
    ScenarioDefinition(nicc, load)
  }

}
