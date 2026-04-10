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

package uk.gov.hmrc.perftests.benefitEligibilityData

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object BenefitEligibilityDataRequests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("nicc")
  val postBenefitEligibilityDataUrl: String = "benefit-eligibility-info"
  val getPaginateUrl: String = "benefit-eligibility-info"
  val nationalInsuranceNumber: String = "AA000002A"
  val startTaxYear: Int = 2019
  val endTaxYear: Int = 2023
  val token: String = AuthRetriever.getAuthToken

  val jsaRequestBody: String =
    s"""{
    "benefitType":"JSA",
    "nationalInsuranceNumber":"$nationalInsuranceNumber",
    "niContributionsAndCredits":{
      "dateOfBirth":"1960-04-05",
      "startTaxYear":$startTaxYear,
      "endTaxYear":$endTaxYear
    }
  }""".stripMargin

  val esaRequestBody: String =
    s"""{
    "benefitType":"ESA",
    "nationalInsuranceNumber":"$nationalInsuranceNumber",
    "niContributionsAndCredits":{
      "dateOfBirth":"1960-04-05",
      "startTaxYear":$startTaxYear,
      "endTaxYear":$endTaxYear
    }
  }""".stripMargin

  val maRequestBody: String =
    s"""{
    "benefitType":"MA",
    "nationalInsuranceNumber":"$nationalInsuranceNumber",
    "niContributionsAndCredits":{
      "dateOfBirth":"1960-04-05",
      "startTaxYear":$startTaxYear,
      "endTaxYear":$endTaxYear
    },
    "liabilities":{
      "searchCategories": ["ABROAD"]
    }
  }""".stripMargin

  val bspRequestBody: String =
    s"""{
    "benefitType":"BSP",
    "nationalInsuranceNumber":"$nationalInsuranceNumber",
    "niContributionsAndCredits":{
      "dateOfBirth":"1960-04-05",
      "startTaxYear":$startTaxYear,
      "endTaxYear":$endTaxYear
    }
  }""".stripMargin

  val gyspRequestBody: String =
    s"""{
    "benefitType":"GYSP",
    "nationalInsuranceNumber":"$nationalInsuranceNumber",
    "niContributionsAndCredits":{
      "dateOfBirth":"1960-04-05",
      "startTaxYear":$startTaxYear,
      "endTaxYear":$endTaxYear
    },
    "longTermBenefitCalculation" : {}
  }""".stripMargin

  val searchlightRequestBody: String =
    s"""{
    "system" : "SEARCHLIGHT",
    "benefitType":"BSP",
    "nationalInsuranceNumber":"$nationalInsuranceNumber",
    "niContributionsAndCredits":{
      "dateOfBirth":"1960-04-05",
      "startTaxYear":$startTaxYear,
      "endTaxYear":$endTaxYear
    },
    "longTermBenefitCalculation" : {}
  }""".stripMargin

  val maPaginateId: String =
    s"""{
    "benefitType":"MA",
    "nationalInsuranceNumber":"$nationalInsuranceNumber",
    "niContributionsAndCredits":{
      "dateOfBirth":"1960-04-05",
      "startTaxYear":$startTaxYear,
      "endTaxYear":$endTaxYear
    },
    "liabilities":{
      "searchCategories": ["ABROAD", "ABSOLUTE-WAIVER"]
    }
  }""".stripMargin

  val bspPaginateId: String =
    s"""{
    "benefitType":"BSP",
    "nationalInsuranceNumber":"$nationalInsuranceNumber",
    "niContributionsAndCredits":{
      "dateOfBirth":"1960-04-05",
      "startTaxYear":2014,
      "endTaxYear":$endTaxYear
    }
  }""".stripMargin

  val gyspPaginateId: String =
    s"""{
    "benefitType":"GYSP",
    "nationalInsuranceNumber":"$nationalInsuranceNumber",
    "niContributionsAndCredits":{
      "dateOfBirth":"1960-04-05",
      "startTaxYear":2014,
      "endTaxYear":$endTaxYear
    },
    "longTermBenefitCalculation" : {}
  }""".stripMargin

  def postBenefitEligibilityDataRequest(request: String): ChainBuilder = {
    exec(
      http("Post benefit eligibility request - without pagination")
        .post(s"$baseUrl/$postBenefitEligibilityDataUrl")
        .header("Content-Type", "application/json")
        .header("Authorization", s"$token")
        .header("CorrelationId", "eba473d1-c34b-498d-925f-af8d2514fa92")
        .body(StringBody(request))
        .check(status.is(200))
    )
  }

  def paginationRequest(request: String): ChainBuilder = {
    exec(
      http("Post benefit eligibility request - with pagination")
        .post(s"$baseUrl/$postBenefitEligibilityDataUrl")
        .header("Content-Type", "application/json")
        .header("Authorization", s"$token")
        .header("CorrelationId", "47205584-6403-47c0-97ab-88e9e115a58e")
        .body(StringBody(request))
        .check(status.is(200))
        .check(jsonPath("$.nextCursor").saveAs("nextCursor"))
    ).exec(
      http("Get remaining paginated data")
        .get(session => s"$baseUrl/$getPaginateUrl?cursorId=${session("nextCursor").as[String]}")
        .header("Content-Type", "application/json")
        .header("Authorization", s"$token")
        .header("CorrelationId", "f926f232-36fa-431c-a196-fcc3e5736f0d")
        .check(status.is(200))
    )
  }
}
