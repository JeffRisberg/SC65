package com.incra.app

import com.escalatesoft.subcut.inject.BindingModule
import com.incra.services.{ActivityService, ChallengeService, LeaderboardService}

/**
 * @author Jeff Risberg
 * @since 12/05/2014
 */
class ActivityServlet(implicit val bindingModule: BindingModule) extends SC65Stack {

  private def activityService = inject[ActivityService]

  get("/") {
    contentType = formats("json")

    trapData {
      activityService.getEntityList()
    }
  }

  get("/:id") {
    contentType = formats("json")

    trapData {
      activityService.findById(params("id").toLong)
    }
  }

  def error(handler: Any): Unit = {}
}
