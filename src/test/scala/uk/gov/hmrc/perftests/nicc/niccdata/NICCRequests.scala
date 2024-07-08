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

package uk.gov.hmrc.perftests.nicc.niccdata

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.nicc.JWTRequest

object NICCRequests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("nicc")
  val postNiccUrl: String = "nicc-json-service/v1/api/national-insurance"
  val nationalInsuranceNumber: String = "BB000200B"
  val startTaxYear: String = "2019"
  val endTaxYear: String = "2023"

  val niccRequestBody: String = "{  \"dateOfBirth\": \"1960-04-05\" }".stripMargin

  val requestHeaders: Map[CharSequence, String] = Map(
    HttpHeaderNames.ContentType -> "application/json",
    "Authorization"             -> JWTRequest.internalToken
  )


  val niccRequest: HttpRequestBuilder = {
      http("Get niContribution and niCredit for NI number, start tax year date and end tax year date")
        .post(s"$baseUrl/$postNiccUrl/$nationalInsuranceNumber/from/$startTaxYear/to/$endTaxYear")
        .header("Content-Type", "application/json")
        .body(StringBody(niccRequestBody))
        .check(status.is(200))
  }
}