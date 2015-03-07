package com.incra.model

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar

import com.incra.model.TeamworkType.TeamworkType
import com.incra.util.RestSupport
import com.incra.util.commons.Document
import org.joda.time.LocalDate
import javax.xml.bind.DatatypeConverter

/**
 * This handles conversion to and from JSON for use by the client code.
 *
 * @author Jeff Risberg
 * @since 12/05/14˜
 */
object ChallengeConverter {
  val dateF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  def unparse(challenge: Challenge): Document = {
    Map(
      "id" -> challenge.id,
      "name" -> challenge.name,
      "teamworkType" -> Map("value" -> challenge.teamworkType.value, "name" -> challenge.teamworkType.name),
      "startDate" -> dateF.format(challenge.startDate),
      "endDate" -> dateF.format(challenge.endDate),
      "active" -> challenge.active
    )
  }

  def parse(doc: Document): Challenge = {
    val teamworkTypeMap = doc("teamworkType").asInstanceOf[Map[String,Any]]
    val teamworkTypeValue = teamworkTypeMap.get("value").get
    val teamworkType = TeamworkType.withKey(teamworkTypeValue.asInstanceOf[BigInt].longValue)

    Challenge(
      doc.getOrElse("id", None).asInstanceOf[Option[Long]],
      doc("name").asInstanceOf[String],
      teamworkType,
      new Date(doc("startDate").asInstanceOf[BigInt].longValue),
      new Date(doc("endDate").asInstanceOf[BigInt].longValue),
      doc("active").asInstanceOf[Boolean]
    )
  }
}
