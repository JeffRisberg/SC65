package com.incra.app

import javax.servlet.http.HttpServletRequest

import com.escalatesoft.subcut.inject.Injectable
import com.incra.util.{RestSupport, Logging}
import org.fusesource.scalate.TemplateEngine
import org.fusesource.scalate.layout.DefaultLayoutStrategy
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.scalate.ScalateSupport

import scala.collection.mutable

/**
 * @author Jeffrey Risberg
 * @since 12/04/2014
 */
trait SC65Stack extends ScalatraServlet
with Injectable
with Logging
with JacksonJsonSupport
with RestSupport{

  override val jsonFormats: Formats = DefaultFormats.withDouble
}
