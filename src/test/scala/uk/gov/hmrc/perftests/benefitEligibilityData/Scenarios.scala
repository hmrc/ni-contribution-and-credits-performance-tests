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
import uk.gov.hmrc.perftests.benefitEligibilityData.BenefitEligibilityDataRequests._
import uk.gov.hmrc.perftests.benefitEligibilityData.ScenarioDefinition

package object Scenarios {

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
        postBenefitEligibilityDataRequest(jsaRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def esaBenefitEligibilityDataJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 7
    val benefitEligibilityData = scenario("Fetch ESA and return")
      .exec(
        postBenefitEligibilityDataRequest(esaRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def maBenefitEligibilityDataJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 1
    val benefitEligibilityData = scenario("Fetch MA and return")
      .exec(
        postBenefitEligibilityDataRequest(maRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def bspBenefitEligibilityDataJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 1
    val benefitEligibilityData = scenario("Fetch BSP and return")
      .exec(
        postBenefitEligibilityDataRequest(bspRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def gyspBenefitEligibilityDataJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 2
    val benefitEligibilityData = scenario("Fetch GYSP and return")
      .exec(
        postBenefitEligibilityDataRequest(gyspRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def searchlightBenefitEligibilityDataJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 7
    val benefitEligibilityData = scenario("Fetch SEARCHLIGHT and return")
      .exec(
        postBenefitEligibilityDataRequest(searchlightRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def underLoadBenefitEligibilityDataJourney(smokeTest: Boolean): List[ScenarioDefinition] = {
    // Note: These loads represent triple the systems standard load to stress the system
    val esaload = 21
    val jsaload = 3
    val maload = 3
    val bspload = 3
    val gyspload = 6
    val serchLightload = 21

    val searchLightBenefitEligibilityData = scenario("Fetch SEARCHLIGHT underload and return")
      .exec(
        postBenefitEligibilityDataRequest(searchlightRequestBody)
      )
    val gyspBenefitEligibilityData = scenario("Fetch GYSP underload and return")
      .exec(
        postBenefitEligibilityDataRequest(gyspRequestBody)
      )
    val bspBenefitEligibilityData = scenario("Fetch BSP underload and return")
      .exec(
        postBenefitEligibilityDataRequest(bspRequestBody)
      )
    val esaBenefitEligibilityData = scenario("Fetch ESA underload and return")
      .exec(
        postBenefitEligibilityDataRequest(esaRequestBody)
      )
    val maBenefitEligibilityData = scenario("Fetch MA underload and return")
      .exec(
        postBenefitEligibilityDataRequest(maRequestBody)
      )
    val jsaBenefitEligibilityData = scenario("Fetch JSA underload and return")
      .exec(
        postBenefitEligibilityDataRequest(jsaRequestBody)
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
        paginationRequest(bspPaginateId)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def gyspBenefitEligibilityDataPaginationJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 2
    val benefitEligibilityData = scenario("Fetch GYSP pagination and return")
      .exec(
        paginationRequest(gyspRequestBody)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def maBenefitEligibilityDataPaginationJourney(smokeTest: Boolean): ScenarioDefinition = {
    val load = 1
    val benefitEligibilityData = scenario("Fetch MA pagination and return")
      .exec(
        paginationRequest(maPaginateId)
      )
    ScenarioDefinition(benefitEligibilityData, load)
  }

  def paginationBenefitEligibilityDataUnderLoadJourney(smokeTest: Boolean): List[ScenarioDefinition] = {
    // Note: These loads represent triple the systems standard load to stress the system
    val maload = 3
    val bspload = 3
    val gyspload = 6
    val maBenefitEligibilityData = scenario("Fetch MA pagination under load and return")
      .exec(
        paginationRequest(maPaginateId)
      )
    val bspBenefitEligibilityData = scenario("Fetch BSP pagination under load and return")
      .exec(
        paginationRequest(bspPaginateId)
      )
    val gyspBenefitEligibilityData = scenario("Fetch GYSP pagination under load and return")
      .exec(
        paginationRequest(gyspPaginateId)
      )
    List(
      ScenarioDefinition(maBenefitEligibilityData, maload),
      ScenarioDefinition(bspBenefitEligibilityData, bspload),
      ScenarioDefinition(gyspBenefitEligibilityData, gyspload)
    )
  }
}
