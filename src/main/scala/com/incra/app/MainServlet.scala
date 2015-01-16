package com.incra.app

import com.escalatesoft.subcut.inject.BindingModule
import com.incra.services.{LeaderboardService, ChallengeService, ActivityService}

/**
 * @author Jeff Risberg
 * @since late August 2014
 */
class MainServlet(implicit val bindingModule: BindingModule) extends SC65Stack {

  private def activityService = inject[ActivityService]

  private def challengeService = inject[ChallengeService]

  private def leaderboardService = inject[LeaderboardService]

  get("/") {
    contentType = "text/html"

    ssp("/index")
  }

  get("/activity") {
    contentType = "text/html"

    val activities = activityService.getEntityList()

    ssp("/activity/index")
  }

  get("/activity.json") {
    contentType = formats("json")

    trapData {
      val activities = activityService.getEntityList()

      activities
    }
  }

  get("/activity/:id") {
    contentType = "text/html"

    val activityOpt = activityService.findById(params("id").toLong)

    if (activityOpt.isDefined) {
      val activity = activityOpt.get

      ssp("/activity/show")
    }
    else {
      redirect("/activity")
    }
  }

  get("/challenge") {
    contentType = "text/html"

    val challenges = challengeService.getEntityList()

    ssp("/challenge/index")
  }

  get("/challenge.json") {
    contentType = formats("json")

    trapData {
      val challenges = challengeService.getEntityList()

      challenges
    }
  }

  get("/challenge/:id") {
    contentType = "text/html"

    val challengeOpt = challengeService.findById(params("id").toLong)
    if (challengeOpt.isDefined) {
      val challenge = challengeOpt.get

      val data1 = List("title" -> "SC65 Challenge")
      val data2 = data1 ++ List("challenge" -> challenge)

      ssp("/challenge/show", data2.toSeq: _*)
    }
    else {
      redirect("/challenge")
    }
  }

  get("/leaderboard") {
    contentType = "text/html"

    val leaderboards = leaderboardService.getEntityList()

    val data1 = List("title" -> "SC65 Leaderboards")
    val data2 = data1 ++ List("name" -> "Brocade-San Jose", "leaderboards" -> leaderboards)

    ssp("/leaderboard/index", data2.toSeq: _*)
  }

  get("/leaderboard.json") {
    contentType = formats("json")

    trapData {
      val leaderboards = leaderboardService.getEntityList()

      leaderboards
    }
  }

  get("/leaderboard/:id") {
    contentType = "text/html"

    val leaderboardOpt = leaderboardService.findById(params("id").toLong)
    if (leaderboardOpt.isDefined) {
      val leaderboard = leaderboardOpt.get

      val data1 = List("title" -> "SC65 Leaderboard")
      val data2 = data1 ++ List("leaderboard" -> leaderboard)

      ssp("/leaderboard/show", data2.toSeq: _*)
    }
    else {
      redirect("/leaderboard")
    }
  }
}
