package com.incra.app

import java.sql.Timestamp

import com.escalatesoft.subcut.inject.BindingModule
import com.incra.model.{ChallengeConverter, TeamworkType, Challenge}
import com.incra.services.{LeaderboardService, ChallengeService, ActivityService}
import org.json4s.DefaultFormats
import org.json4s.JsonAST.JObject
import org.scalatra.Ok

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
      challengeService.getEntityList().map(ChallengeConverter.unparse)
    }
  }

  get("/challenge/:id") {
    contentType = formats("json")

    trapData {
      challengeService.findById(params("id").toLong)
    }
  }

  put("/challenge") {
    trapUnit {
      try {
        val doc = parsedBody.asInstanceOf[JObject].values

        val challenge = ChallengeConverter.parse(doc)

        challengeService.addChallenge(challenge)
      }
      catch {
        case exception: Exception =>
          exception.printStackTrace()
      }
    }
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

  def error(handler: Any): Unit = {}
}
