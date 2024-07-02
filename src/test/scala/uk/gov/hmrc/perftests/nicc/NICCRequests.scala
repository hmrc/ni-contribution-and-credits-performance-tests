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
    "nino" -> "AA111111A",
    "gender" -> "M",
    "checkBrick" -> "SMIJ",
    "finalise" -> false
  ).toString()

  val finalCalcRequestBody: String = Json.obj(
    "nino" -> "AA123456A",
    "gender" -> "M",
    "checkBrick" -> "SMIJ",
    "finalise" -> true
  ).toString()

  val headers: Map[String, String] = Map(
    HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson,
    "CorrelationId" -> UUID.randomUUID().toString
  )

  val callInitialCalculation: HttpRequestBuilder =
    http("Calls the calculation endpoint with an initial calc payload")
      .post(s"$baseUrl/calculation")
      .body(StringBody(initialCalcRequestBody))
      .headers(headers)
      .check(status.is(201))

  val callFinalCalculation: HttpRequestBuilder =
    http("Calls the calculation endpoint with an final calc payload")
      .post(s"$baseUrl/calculation")
      .body(StringBody(finalCalcRequestBody))
      .headers(headers)
      .check(status.is(201))

}
