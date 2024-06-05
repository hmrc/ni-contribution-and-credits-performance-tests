/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.nicc

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object NICCRequests extends ServicesConfiguration {

  val bearerToken: String = readProperty("bearerToken", "${authBearerToken}")

  val baseUrl: String = baseUrlFor("nicc") + "/individuals/tax-free-childcare/payments"

  lazy val authBaseUrl: String = baseUrlFor("auth-login-api")

  lazy val authUrl: String = s"$authBaseUrl/government-gateway/session/login"
  val baseUrl: String = baseUrlFor("example-frontend")
  val route: String   = "/check-your-vat-flat-rate"

  val navigateToHomePage: HttpRequestBuilder =
    http("Navigate to Home Page")
      .get(s"$baseUrl$route/vat-return-period")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postVatReturnPeriod: HttpRequestBuilder =
    http("Post VAT return Period")
      .post(s"$baseUrl$route/vat-return-period": String)
      .formParam("vatReturnPeriod", s"$${vatReturnPeriod}")
      .formParam("csrfToken", s"$${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is("/check-your-vat-flat-rate/turnover").saveAs("turnOverPage"))

  val getTurnoverPage: HttpRequestBuilder =
    http("Get Turnover Page")
      .get(s"$baseUrl$${turnOverPage}": String)
      .check(status.is(200))
}
