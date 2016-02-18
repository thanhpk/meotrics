package models

import java.util.Date
import play.api.db.DB
import play.api.mvc.Request
import utils.RParser
import anorm._
import play.api.Play.current
import anorm.SqlParser._
import anorm.ResultSetParser

object User {

	case class UserData(id: Int = -1, name: String = "", username: String = "", email: String = "",
	                    phone: String = "", ctime: Date = new Date, appid: Int = -1, password: String = "")

	val userParser: RowParser[UserData] = {
		int("id") ~ str("name") ~ str("email") ~ str("phone") ~ date("ctime") ~ int("appid") ~ str("password") map {
			case id ~ name ~ email ~ phone ~ ctime ~ appid ~ password ⇒
				UserData(id = id,
					name = name,
					email = email,
					phone = phone,
					ctime = ctime,
					appid = appid,
					password = password)
		}
	}
	val usersParser: ResultSetParser[List[UserData]] = {
		userParser.*
	}

	def bindRequest(implicit request: Request[Any]): UserData = {
		UserData(
			id = RParser.pI("id", RParser.pI("user_id")),
			username = RParser.pS("username", RParser.pS("user_username")),
			name = RParser.pS("name", RParser.pS("user_name")),
			email = RParser.pS("email", RParser.pS("user_name")),
			phone = RParser.pS("phone", RParser.pS("user_name")),
			ctime = RParser.pDate("ctime", RParser.pDate("user_ctime")),
			appid = RParser.pI("appid", RParser.pI("user_appid")),
			password = RParser.pS("password", RParser.pS("user_password"))
		)
	}

	def login(email: String, password: String): Option[UserData] =
		DB.withConnection { implicit c ⇒
			SQL"""select (id,name,email,phone,ctime,appid,password)
				 from tbuser where email=$email and password=$password"""
				.as(userParser.singleOpt)
		}

	def listAll(): List[UserData] = DB.withConnection { implicit c ⇒
		SQL"select * from tbuser".as(usersParser)
	}

	def read(id: Int): Option[UserData] = DB.withConnection { implicit c ⇒
		SQL"select * from tbuser where id = $id".as(userParser.singleOpt)
	}

	def update(user: UserData) = {

	}

	def create(user: UserData, appname: String): Option[Integer] = DB.withConnection { implicit c ⇒
		// make sure user have not existed yet
		val x = SQL"select count(*) as c from tbuser where email = ${user.email}".as(SqlParser.int("c").single)
		if (x != 0)
			return None

		//create an app for user
		val appid = SQL"insert into tbapp(name,ownerid) values($appname,-1) returning id".as(SqlParser.int("id").single)

		//create an user
		val userid = SQL"insert into tbuser (name,email,phone,ctime,appid,password) values (${user.name},${user.email},${user.phone},${new java.util.Date()},$appid,${user.password}) returning id".as(SqlParser.int("id").single)

		// update app owner to user
		SQL"update tbapp set ownerid = $userid where id=$appid".execute()

		return Some(userid)
	}

}
