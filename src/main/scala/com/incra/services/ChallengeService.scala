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
          new Date(113, 8, 1), new Date(113, 10, 20), false)
        challenges += Challenge(Some(101), "Walk to the Moon", TeamworkType.Individual,
          new Date(113, 5, 6), new Date(113, 6, 8), true)
        challenges += Challenge(None, "Holiday Ship-Shape", TeamworkType.Team,
          new Date(113, 10, 1), new Date(114, 1, 3), false)
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
}
