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
with ScalateSupport
with RestSupport{

  override val jsonFormats: Formats = DefaultFormats.withDouble

  /* wire up the precompiled templates */
  override protected def defaultTemplatePath: List[String] = List("/WEB-INF/views")

  override protected def createTemplateEngine(config: ConfigT) = {
    val engine = super.createTemplateEngine(config)
    engine.layoutStrategy = new DefaultLayoutStrategy(engine,
      TemplateEngine.templateTypes.map("/WEB-INF/layouts/default." + _): _*)
    engine
  }

  /* end wiring up the precompiled templates */

  override protected def templateAttributes(implicit request: HttpServletRequest): mutable.Map[String, Any] = {
    super.templateAttributes ++ mutable.Map.empty // Add extra attributes here, they need bindings in the build file
  }

  notFound {
    // remove content type in case it was set through an action
    contentType = null
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map {
      path =>
        contentType = "text/html"
        layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }
}
