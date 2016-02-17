package models

import play.api.mvc.Request

// Thanh
object Browser {


	val MobileOsPattern = "(iPhone|webOS|iPod|Android|BlackBerry|mobile|SAMSUNG|IEMobile|OperaMobi)".r.unanchored

	def getId(implicit request: Request[Any]): Int = ???

	def getName(implicit request: Request[Any]): String = ???

	def getVersion(implicit request: Request[Any]): BigDecimal = ???

	def isMobile(implicit request: Request[Any]): Boolean = {
		request.headers.get("User-Agent").exists {
			case MobileOsPattern(a) => true
			case _ => false
		}

	}

}
