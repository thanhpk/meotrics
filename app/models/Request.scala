package models

import play.api.mvc.Request

object Request {
	def getOs(implicit request: Request[Any]): Int = {

		4
	}

	def isMobile(implicit request: Request[Any]) : Boolean = ???

	def getBrowser(implicit request: Request[Any]): Int = ???


}
