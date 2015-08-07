package com.incra.app

import com.escalatesoft.subcut.inject.BindingModule
import com.incra.model.ChallengeConverter
import com.incra.services.{ActivityService, ChallengeService, LeaderboardService}
import com.incra.util.ApiResult
import com.iv.commons.app.ApiResult
import com.iv.service.ActivityLogService
import org.json4s.JsonAST.JObject

/**
 * @author Jeff Risberg
 * @since 04/15/2015
 */
class ChallengeServlet(implicit val bindingModule: BindingModule) extends SC65Stack {

  private def challengeService = inject[ChallengeService]

  get("/") {
    contentType = formats("json")

    trapData {
      challengeService.getEntityList().map(ChallengeConverter.unparse)
    }
  }

  get("/:id") {
    contentType = formats("json")

    trapData {
      challengeService.findById(params("id").toLong)
    }
  }

  get("x") {
    contentType = formats("json")
    trapApiResult {
      val start = params.getOrElse("start", "0").toInt
      val limit = params.getOrElse("limit", "25").toInt
      val activities = challengeService.getEntityList(limit, start)
      var count = challengeService.countDocuments()
      ApiResult(success = true, activities, Map.empty, count)
    }
  }

  put("/") {
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

  def error(handler: Any): Unit = {}
}
