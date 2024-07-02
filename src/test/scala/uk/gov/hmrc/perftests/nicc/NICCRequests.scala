package uk.gov.hmrc.perftests.nicc

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import play.api.libs.json.Json
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object NICCRequests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("nicc")

  val niccRequestBody: String = Json.obj(
    "dateOfBirth" -> "1981-08-24",
    "nino" -> "BB000200B",
    "startTaxYear" -> "2020",
    "endTaxYear" -> "2024",
  ).toString()

  val headers: Map[String, String] = Map(
    HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson,
    "CorrelationId" -> UUID.randomUUID().toString
  )

  val callnicc: HttpRequestBuilder =
    http("Calls the nicc endpoint with payload")
      .post(s"$baseUrl/calculation")
      .body(StringBody(niccRequestBody))
      .headers(headers)
      .check(status.is(200))

  val callFinalCalculation: HttpRequestBuilder =
    http("Calls the calculation endpoint with an final calc payload")
      .post(s"$baseUrl/calculation")
      .body(StringBody(finalCalcRequestBody))
      .headers(headers)
      .check(status.is(201))

}
