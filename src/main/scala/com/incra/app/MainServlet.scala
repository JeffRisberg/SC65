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

  get("/challenge.json") {
    contentType = formats("json")

    trapData {
      challengeService.getEntityList()
    }
  }

  get("/challenge/:id") {
    contentType = formats("json")

    trapData {
      challengeService.findById(params("id").toLong)
    }
  }

  put("challenge") {
    // write to the challengService

  }

  get("/activity.json") {
    contentType = formats("json")

    trapData {
      activityService.getEntityList()
    }
  }

  get("/activity/:id") {
    contentType = formats("json")

    trapData {
      activityService.findById(params("id").toLong)
    }
  }

  get("/leaderboard.json") {
    contentType = formats("json")

    trapData {
      leaderboardService.getEntityList()
    }
  }

  get("/leaderboard/:id") {
    contentType = formats("json")

    trapData {
      leaderboardService.findById(params("id").toLong)
    }
  }
}
