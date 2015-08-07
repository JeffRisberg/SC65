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

  private def leaderboardService = inject[LeaderboardService]

  get("/") {
    contentType = "text/html"

    ssp("/index")
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
