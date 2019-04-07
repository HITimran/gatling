package org.gatling.load

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class addNewComputer extends Simulation {

  val csvFeeder = csv("computerItems.csv").circular

  val httpProtocol = http
    .baseUrl("http://computer-database.gatling.io")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36")

  val headers_2 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_4 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Origin" -> "http://computer-database.gatling.io",
    "Upgrade-Insecure-Requests" -> "1")


  val scn =
    scenario("addNewComputer")
      .exec(http("On New Computer section")
        .get("/computers/new")
        .headers(headers_2))
      .pause(39)
      .exec(http("Add New Computer")
        .post("/computers")
        .headers(headers_4)
        .formParam("name", "Hello ImranaZ")
        .formParam("introduced", "1947-09-14")
        .formParam("discontinued", "1948-09-14")
        .formParam("company", "4")
        .check(css("#main > div.alert-message.warning") is "Done! Computer Hello ImranaZ0 has been created")
      )

  //setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)

  def getItemsForComputer()  ={
    repeat(5)
    {
      feed(csvFeeder)
        .exec(http("getSpecificComputerItems")
        .get("/computers/new")
          .headers(headers_2))
        .pause(5)
        .exec(http("Add new Computer")
        .post("/computers")
          .headers(headers_4)
          .formParam("name", "${computerName}")
          .formParam("introduced", "${introduced}")
          .formParam("discontinued", "${discontinued}")
          .formParam("company", "${company}")
          .check(css("#main > div.alert-message.warning") is "Done! Computer ${computerName} has been created"))
          .pause(1)
    }
  }

  val csvSCN= scenario("computer data entry")
    .exec(getItemsForComputer())

  setUp(csvSCN.inject(atOnceUsers(1))).protocols(httpProtocol)


}