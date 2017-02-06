package my.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class TestApiSimulation extends Simulation {

  val csvFile = System.getProperty("csvFile")                            // CSV input file // Getting params from Jenkins
  val duration  = java.lang.Long.getLong("duration", 0L)                 // Duration of run // Getting params from Jenkins
  val rampNUsers  = Integer.getInteger("rampNUsers", 1)                  // Total number of users // Getting params from Jenkins
  val rampDuration  = java.lang.Long.getLong("rampDuration", 0L)          // Total duration when all users are connected // Getting params from Jenkins
  val runInvalidator = System.getProperty("runInvalidator")               // Setting a specific header for each request // Getting params from Jenkins

  val userUrls = csv(csvFile).circular // queue, shuffle, random, circular

  val scn = scenario("Running loadtest")

  // repeat(10)  // repeat the Simulation a number of times

    .doIfOrElse(runInvalidator=="true") {

      during(duration seconds) {
        feed(userUrls)
        .exec(
          http("Search")
          .get("${url}")
          .header("invalidator","true") // by disabling this header one reads directly from couchbase else you read via the orchestrator
        ) // .pause(0,5)  // pause the simulation half a second
      }
  }
      {
      during(duration seconds) {
        feed(userUrls)
          .exec(
            http("Search")
            .get("${url}")
            )
      }
}

  setUp(scn.inject(rampUsers(rampNUsers) over(rampDuration seconds))

  )
}
