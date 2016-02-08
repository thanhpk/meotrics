package models

import java.util.{Date, Calendar}

import play.api.data.Form
import play.api.db._
import play.api.data.Forms._
import play.api.mvc.Request
import utils.RParser


case class UserData(id: Long,
                    username: String,
                    name: String,
                    email: String,
                    phone: String,
                    ctime: Calendar,
                    hashpassword: String)


object User {


	def login(usersname: String, passwod: String): Long = {
		throw new ClassNotFoundException
	}

	def read(id: Long): UserData = {
		throw new RuntimeException
	}

	def read(username: String) = {
		throw new RuntimeException
	}

	def update(user: UserData) = {

	}

	def create(user: UserData): Unit = {

	}

	def bindRequest()(implicit request: Request[Any]) = { UserData(
		RParser.parseLong("id", RParser.parseLong("user_id")),
		RParser.parseString("username", RParser.parseString("user_username")),
		RParser.parseString("name", RParser.parseString("user_name")),
		RParser.parseString("email", RParser.parseString("user_name")),
		RParser.parseString("phone", RParser.parseString("user_name")),
		RParser.parseDate("ctime", RParser.parseDate("user_ctime")),
		RParser.parseString("hashpassword", RParser.parseString("user_hashpassword"))
	)

	}


}
