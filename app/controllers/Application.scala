package controllers

import play.api._
import play.api.cache.CacheApi
import play.api.mvc._
import play.api.db._
import play.api.Play.current
import javax.inject.Inject
import ua_parser.{Parser, Client}
;

class Application @Inject()(cache: CacheApi) extends Controller {
	val ds = DB.getDataSource()

	def landing = Action {
		val uaString = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";

		val uaParser = new Parser()

		val c = uaParser.parse(uaString)

		Ok(views.html.landing())
	}

	def index() = Action { request ⇒
		implicit val session: Session = request.session
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

	def setupVisitor() = play.mvc.Results.TODO

	def identifyVisitor() = play.mvc.Results.TODO

	def recordAction() = Action { implicit request ⇒
		val actionData = models.Action.bindRequest
		models.Action.create(actionData)
		Ok(views.html.admin())
	}
}
