package com.incra.app

import com.escalatesoft.subcut.inject.BindingModule
import com.incra.services.{LeaderboardService, ChallengeService, ActivityService}

/**
 * @author Jeff Risberg
 * @since 12/05/2014
 */
class MainServlet(implicit val bindingModule: BindingModule) extends SC65Stack {

  private def activityService = inject[ActivityService]

  private def challengeService = inject[ChallengeService]

  private def leaderboardService = inject[LeaderboardService]

  get("/") {
    contentType = "text/html"

    ssp("/index")
  }

  get("/activity.json") {
    contentType = formats("json")

    trapData {
      val activities = activityService.getEntityList()

      activities
    }
  }

  get("/activity/:id") {
    contentType = formats("json")

    trapData {
      val activityOpt = activityService.findById(params("id").toLong)

      if (activityOpt.isDefined) {
        activityOpt.get
      }
      else {
        None
      }
    }
  }

  get("/challenge.json") {
    contentType = formats("json")

    trapData {
      val challenges = challengeService.getEntityList()

      challenges
    }
  }

  get("/challenge/:id") {
    contentType = formats("json")

    val challengeOpt = challengeService.findById(params("id").toLong)
    if (challengeOpt.isDefined) {
      challengeOpt.get
    }
    else {
      None
    }
  }

  get("/leaderboard.json") {
    contentType = formats("json")

    trapData {
      val leaderboards = leaderboardService.getEntityList()

      leaderboards
    }
  }

  get("/leaderboard/:id") {
    contentType = formats("json")

    trapData {
      val leaderboardOpt = leaderboardService.findById(params("id").toLong)

      if (leaderboardOpt.isDefined) {
        leaderboardOpt.get
      }
      else {
        None
      }
    }
  }
}
