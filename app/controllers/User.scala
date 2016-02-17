package controllers

import models.User
import play.api.mvc._
import utils.RParser

class User extends Controller {
	def viewSignup = Action { implicit request ⇒
		// check if we get error redirect from other
		val error = request.flash.get("error")
		Ok(views.html.signup(error.getOrElse("false").toBoolean))
	}

	def signup = Action { implicit request ⇒
		// parse parameter from request
		val userData = User.bindRequest
		val appname = RParser pS "appname"

		val id = User.create(userData, appname)
		id match {
			case None ⇒
				// user infomation not valid
				Redirect(routes.User.viewSignup()).flashing("error" → "true")
			case Some(i) ⇒
				val user = User.read(i)
				user match {
					case None ⇒ throw new RuntimeException("it had deleted before i read it")
					case _ ⇒ Redirect(routes.Application index()).withSession(
						"email" → userData.email,
						"password" → userData.password)
				}
		}
	}

	def viewLogin = Action { implicit request ⇒
		request.session.get("email") match {
			case None ⇒ Ok(views.html.login())
			case _ ⇒ BadRequest("You have aldready login !")
		}
	}

	def logout = Action { implicit request ⇒
		// điều hướng qua viewLogin
		// xóa session
		request.session.get("email") match {
			case None ⇒ BadRequest("You have not login yet !")
			case _ ⇒ Redirect(routes.User.viewLogin()).withNewSession
		}
	}

	def login = Action { implicit request ⇒
		//get parameters from the request
		val userData = User.bindRequest

		val user = User.login(userData.email, userData.password)

		user match {
			case None ⇒ BadRequest(views.html.login(true))
			case Some(u) ⇒ Redirect(routes.Application.index()).withSession(
				"email" → u.email,
				"password" → u.password)
		}
	}

}
