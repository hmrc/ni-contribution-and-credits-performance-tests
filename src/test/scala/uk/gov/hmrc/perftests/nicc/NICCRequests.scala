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

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object NICCRequests extends ServicesConfiguration {

  val baseUrl: String     = baseUrlFor("nicc")
  val postNiccUrl: String = "/nicc-json-service/nicc/v1/api/national-insurance/{nationalInsuranceNumber}/contributions-and-credits/from/{startTaxYear}/to/{endTaxYear}"
  val nationalInsuranceNumber: String = "SS000200"
  val startTaxYear: String   = "2019-10-01"
  val endTaxYear: String     = "2023-10-31"

  def postNICC: ChainBuilder =
    exec(
      http("Get niContribution and niCredit for NI number, start tax year date and end tax year date")
        .post(s"$baseUrl$postNiccUrl/$nationalInsuranceNumber?startDate=$startTaxYear&endDate=$endTaxYear")
        .check(status.is(200))
    )

}
