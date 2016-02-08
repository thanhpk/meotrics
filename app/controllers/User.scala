package controllers

import models.User
import play.api.Logger
import play.api.mvc._

class User extends Controller {


	def viewSignup = Action {
		Ok(views.html.signup())
	}

	def signup = Action { implicit request =>
		val userData = User.bindRequest

		models.User.create(userData)
		Redirect(routes.Application.index()).withSession(
			"email" -> userData.email,
			"password" -> userData.hashpassword)
	}

	def viewLogin = Action { implicit request =>

		Logger.info("why here")
		request.session.get("email") match {
			case None => Ok(views.html.login())
			case _ => BadRequest("You are aldready login !")
		}
	}

	def logout = Action {
		Redirect(routes.User.viewLogin()).withNewSession
	}


	def login = Action { implicit request =>
		val userData = User.bindRequest
		Redirect(routes.Application.index()).withSession(
			"email" -> userData.email,
			"password" -> userData.hashpassword)

	}


}
