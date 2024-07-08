/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.nicc

import io.gatling.http.Predef.HttpHeaderNames
import play.api.libs.json.JsValue
import uk.gov.hmrc.performance.conf.ServicesConfiguration

trait CommonUtils extends ServicesConfiguration {

  val requestHeaders: Map[CharSequence, String] = Map(
    HttpHeaderNames.ContentType -> "application/json; charset=utf-8"
  )

  val requestHeadersWithAuth: Map[CharSequence, String] =
    requestHeaders ++ Map("Authorization" -> JWTRequest.internalToken)

  val aehBaseUrl: String = baseUrlFor("nicc")
  val aehRoute = "/nicc-json-service/v1/api/national-insurance"

  def baseSignalBody(events: JsValue): String = {
    s"""
       |{
       |"iss": "https://ssf.account.gov.uk/",
       |"jti": "756E69717565206964656E746966696572",
       |"iat": 1615305159,
       |"aud": "https://audience.hmrc.gov/",
       |"events": $events
       |}
       |""".stripMargin
  }
}
