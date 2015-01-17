package com.incra.services

import java.sql.Date

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.incra.model.{Challenge, ChallengeTable, TeamworkType}

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.jdbc.meta.MTable

/**
 * @author Jeff Risberg
 * @since 10/08/2014
 */
class ChallengeService(implicit val bindingModule: BindingModule) extends Injectable {
  private def mainDatabase = inject[Database]

  println("InitChallengeService")
  mainDatabase withSession {
    implicit session =>
      val challenges = TableQuery[ChallengeTable]

      // Create the tables, including primary and foreign keys
      if (MTable.getTables("challenge").list().isEmpty) {
        (challenges.ddl).create

        challenges += Challenge(None, "Fall Hiking", TeamworkType.Team,
          Date.valueOf("2014-10-03 22:10:10.0"), Date.valueOf("2014-10-31 23:59:59.0"), false)
        challenges += Challenge(Some(101), "Walk to the Moon", TeamworkType.Individual,
          Date.valueOf("2014-07-20 22:00:00.0"), Date.valueOf("2014-12-31 23:59:59.0"), true)
        challenges += Challenge(None, "Holiday Ship-Shape", TeamworkType.Team,
          Date.valueOf("2014-12-01 00:00:00.0"), Date.valueOf("2014-12-31 23:59:59.0"), false)
      }
  }
  println("EndInitChallengeService")

  /**
   *
   */
  def getEntityList(): List[Challenge] = {
    mainDatabase withSession {
      implicit session =>

        TableQuery[ChallengeTable].list
    }
  }

  /**
   *
   */
  def findById(id: Long): Option[Challenge] = {
    mainDatabase withSession {
      implicit session =>

        TableQuery[ChallengeTable].where(_.id === id).firstOption
    }
  }

  /**
   *
   */
  def addChallenge(newChallenge: Challenge): Unit = {
    mainDatabase withSession {
      implicit session =>
        val challenges = TableQuery[ChallengeTable]

        challenges += newChallenge
    }
  }
}
