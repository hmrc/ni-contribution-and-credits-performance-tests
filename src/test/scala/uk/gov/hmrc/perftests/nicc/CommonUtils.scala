/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.nicc

import client.HttpClient
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import io.gatling.http.Predef.HttpHeaderNames
import org.scalatest.matchers.must.Matchers.not
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.StandaloneWSRequest

import scala.concurrent.Await
import scala.util.Random

trait CommonUtils extends ServicesConfiguration with HttpClient {

  def stubDataForPost(
                       url: String,
                       json: JsValue,
                       token: String = "token"
                     ): concurrent.Future[StandaloneWSRequest#Self#Response] =
    post(
      url,
      Json.stringify(json),
      ("Content-Type", "application/json"),
      ("Accept", "application/vnd.hmrc.P1.0+json"),
      ("Authorization", s"$token")
    )

  def generateToken(
                     resourceType: String = "account-event-history",
                     configToken: Option[String] = None,
                     principal: String = "account-event-history"
                   ): String = {

    val token: String = configToken.getOrElse(Random.alphanumeric.take(10).mkString)

    def tokenBodyRequest: String =
      s"""{
         |"token": "$token",
         |"principal": "$principal",
         |"permissions": [{
         |"resourceType": "$resourceType",
         |"resourceLocation": "*",
         |"actions": ["*"]
         |}]}""".stripMargin

    val tokenJsonRequest = Json.parse(tokenBodyRequest)
    val tokenEndpoint = s"http://localhost:8470/test-only/token"
    val tokenResult = Await.result(stubDataForPost(tokenEndpoint, tokenJsonRequest), 10.seconds)

    tokenResult.status should not be (400)
    token
  }

  val requestHeaders: Map[CharSequence, String] = Map(
    HttpHeaderNames.ContentType -> "application/json",
    HttpHeaderNames.Authorization -> generateToken()
  )

  val requestHeadersWithAuth: Map[CharSequence, String] =
    requestHeaders ++ Map("Authorization" -> JWTRequest.internalToken)

  val niccBaseUrl: String = baseUrlFor("nicc")
  val postNiccUrl = "/nicc-json-service/v1/api/contribution-and-credits"

  val startTaxYear: String = "2019"
  val endTaxYear: String = "2023"
 val niccRequestBody: String = "{  \"nationalInsuranceNumber\": \"BB000200B\" , \"dateOfBirth\": \"1960-04-05\" , \"customerCorrelationID\": \"e470d658-99f7-4292-a4a1-ed12c72f1337\" }".stripMargin

}
