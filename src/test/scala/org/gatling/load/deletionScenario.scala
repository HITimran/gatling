package org.gatling.load

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class deletionScenario extends Simulation {

  val httpProtocol = http
    .baseUrl("http://computer-database.gatling.io")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36")

  val gatlingDBHeader = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en,en-IN;q=0.9,en-AU;q=0.8,en-US;q=0.7,en-GB;q=0.6",
    "Origin" -> "http://computer-database.gatling.io",
    "Upgrade-Insecure-Requests" -> "1")
  val csvFeeder = csv("computerItems.csv").circular

  val scn = scenario("deletionScenario").
    feed(csvFeeder)
    .exec(http("Search for Computer ${computerName}")
      .get("/computers?f=${computerName}")
      // .check(css("a:contains('${computerName}')", "href")
      .check(css("td:nth-child(1) > a", "href")
      .saveAs("computerURL"))
    )
    .pause(4)
    .exec(http("get the request ID for deletion ${computerURL}")
      .get("${computerURL}")
    )
    .pause(4)
    .exec(http("to delete the value")
      .post("${computerURL}/delete")
    )

  setUp(scn.inject(atOnceUsers(5))).protocols(httpProtocol)

}