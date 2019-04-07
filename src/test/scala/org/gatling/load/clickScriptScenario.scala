package org.gatling.load

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.github.clickscript.Predef._
import io.gatling.jdbc.Predef._

class clickScriptScenario extends Simulation {

  val httpProtocol = http
    .baseUrl("http://computer-database.gatling.io")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36")

  val headers_2 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en,en-IN;q=0.9,en-AU;q=0.8,en-US;q=0.7,en-GB;q=0.6",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_5 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en,en-IN;q=0.9,en-AU;q=0.8,en-US;q=0.7,en-GB;q=0.6",
    "Origin" -> "http://computer-database.gatling.io",
    "Upgrade-Insecure-Requests" -> "1")

  val scn = scenario("gatlingExampleWebsite")
    .exec(http("request_3")
      .get("/computers?f=imac")
      .headers(headers_2))
    .pause(9)
    .exec(http("request_4")
      .get("/computers/224")
      .headers(headers_2))
    .pause(13)
    .exec(http("request_5")
      .post("/computers/224")
      .headers(headers_5)
      .formParam("name", "IMac G3")
      .formParam("introduced", "")
      .formParam("discontinued", "")
      .formParam("company", "2"))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)


   scenario("clickScriptScenario")
    .exec(
      clickStep("Go To Homepage")
        .goTo("/index.html")
        // It's a thin wrapper around Gatling's HTTP support, so use checks as you would normally
        .check(css("h1") is "Welcome to our site")
    )
    .exec(
      clickStep("Click Log In")
        .click("#log-in") // CSS Selectors
    )
    .exec(
      clickStep("Enter Log-in Details")
        .form // or, if there's more than one form, .form("#login-form")
        .enterField("username", "${userName}")
        .enterField("password", "${password}")
        .tickCheckbox("remember-me")
        .post // or, if the submit button does something special, .clickPostButton("#submit-button")
    )
    .exec(exitBrowser) // Clear data about the current page - may save you memory
}
