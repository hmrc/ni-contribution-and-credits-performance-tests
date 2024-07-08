/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.nicc

import uk.gov.hmrc.performance.conf.ServicesConfiguration

import akka.actor.ActorSystem
import org.slf4j.LoggerFactory
import play.api.libs.ws.ahc.StandaloneAhcWSClient
import play.api.libs.json.Json
import play.api.libs.ws.JsonBodyWritables._

import scala.collection.Seq
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object JWTRequest extends ServicesConfiguration {

  private val logger                       = LoggerFactory.getLogger(classOf[JWTRequest.type])
  private implicit val system: ActorSystem = ActorSystem()
  lazy val wsClient: StandaloneAhcWSClient = StandaloneAhcWSClient()
  lazy val baseUrl: String                 = baseUrlFor("internal-auth")
  lazy val route: String                   = s"$baseUrl/test-only/token"
  val internalToken: String                = readProperty("internal-auth.token")

  lazy val requestHeaders: List[(String, String)] = List(
    "ContentType"   -> "application/json; charset=utf-8",
    "Authorization" -> internalToken
  )

  def ensureValidAuthToken(): Unit = {
    val tokenIsValid = Await
      .result(
        wsClient
          .url(route)
          .withHttpHeaders(requestHeaders: _*)
          .get(),
        5.seconds
      )
      .status == 200

    if (tokenIsValid) {
      logger.info("auth token is already valid")
    } else {
      logger.info("creating valid auth token")
      val response      = Await.result(
        wsClient
          .url(route)
          .post(
            Json.obj(
              "token"       -> internalToken,
              "principal"   -> "test",
              "permissions" -> Seq(
                Json.obj(
                  "resourceType"     -> "nicc",
                  "resourceLocation" -> "*",
                  "actions"          -> List("*")
                )
              )
            )
          ),
        5.seconds
      )
      val responseError = response.body
      require(response.status == 201, s"Unable to create auth token due to $responseError")
      logger.info("auth token created")
    }
  }
}
