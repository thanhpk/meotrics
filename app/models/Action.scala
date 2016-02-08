package models

import play.api.db._

case class Action(id: Long, os: String, location: String)

object Action {
  def create(os: String): Unit = {
//    DB.withConnection { implicit c =>
//      SQL("insert into action () values ()").on(
//        'label -> os
//      ).excuteUpdate()
//    }

  }

}
