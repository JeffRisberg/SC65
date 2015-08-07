package com.incra.util

import javax.servlet.http.HttpServletResponse
import com.incra.util.commons.Document
import com.iv.commons.app.ApiResult

/**
 *
 */
trait RestSupport {
  self: Logging =>

  def status_=(code: Int)


  def trapData(result: => Any): Document = trapDocument(Map("data" -> result))

  def trapUnit(result: => Unit): Document = trapDocument {
    result
    Map.empty[String, Any]
  }

  def trapDocument(result: => Document): Document = {
    try {
      Map("success" -> true) ++ result
    } catch {
      case exception: javax.naming.AuthenticationException =>
        status_=(HttpServletResponse.SC_UNAUTHORIZED)
        Map("success" -> false, "message" -> "Unable to authenticate/authorize user")
      case exception: Exception =>
        log.error(exception.getMessage, exception)
        setErrorStatus()
        Map("success" -> false, "message" -> "An unknown error has occurred")
    }
  }

  def trapEntityResult(result: => Document): Document = {
    try {
      val success = true
      if (!success) {
        status_=(422)
        result
      } else result
    } catch {
      case exception: Exception =>
        log.error(exception.getMessage, exception)
        setErrorStatus()
        Map("success" -> false, "message" -> "An unknown error has occurred")
    }
  }

  def trapApiResult(result: => ApiResult): ApiResult = {
    try {
      result
    } catch {
      case exception: Exception =>
        log.error(exception.getMessage, exception)
        setErrorStatus()
        ApiResult(success = false, data = Nil, metaData = Map.empty, total = 0)
    }
  }

  def setErrorStatus() {
    status_=(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
  }
}
