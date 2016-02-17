package controllers

import play.api.mvc._

class SpeedyApply extends Controller {
	def index() = Action {
		Ok(views.html.speedyapply.index())
	}
}
