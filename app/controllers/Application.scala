package controllers

import play.api._
import play.api.cache.CacheApi
import play.api.mvc._
import play.api.db._
import play.api.Play.current
import javax.inject.Inject

class Application @Inject()(cache: CacheApi) extends Controller {

	val ds = DB.getDataSource()


	def landing = Action{

		Ok(views.html.landing())
	}

	def index() = Action { request =>
		implicit  val session: Session = request.session
		var outString = "Number is "
		val conn = DB.getConnection()
		try {
			val stmt = conn.createStatement
			val rs = stmt.executeQuery("SELECT 9 as testkey ")
			while (rs.next()) {
				outString += rs.getString("testkey")
			}
		} finally {
			conn.close()
		}
		Ok(views.html.admin())

	}

}
