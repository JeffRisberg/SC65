package com.incra.util

/**
 * Utility class to hold results of api calls, with status and content.
 */
case class ApiResult(
                      success: Boolean,
                      data: List[Any],
                      metaData: Map[String, Any],
                      total: Long)
