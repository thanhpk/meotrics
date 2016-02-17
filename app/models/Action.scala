package models

import java.util.Date
import anorm.SqlParser._
import anorm.{ResultSetParser, RowParser, ~, _}
import play.api.db.DB
import play.api.mvc.Request
import utils.RParser
import play.api.Play.current

object Action {
	val PAGEVIEW: Int = 0
	val PURCHASE: Int = 1
	val LEAVE: Int = 2
	val GOOGLECHROME = 1
	val Firefox = 2
	val Opera = 3
	val actionParser: RowParser[Action] = {
		long("id") ~
			long("visitorid") ~
			int("eventid") ~
			int("appid") ~
			byte("osid") ~
			byte("browserid") ~
			short("locationid") ~
			str("referer") ~
			int("campaignid") ~
			short("deviceid") ~
			date("ctime") ~
			str("ip") ~
			str("screenres") ~
			int("totalsec") ~
			short("age") ~
			str("url") ~
			str("s1") ~
			str("s2") ~
			double("n1") ~
			double("n2") ~
			str("lang") ~
			double("browserversion") ~
			short("osversion") map {
			case id ~ visitorid ~ eventid ~ appid ~ osid ~ browserid ~
				locationid ~ referer ~ campaignid ~ deviceid ~ ctime ~ ip ~
				screenres ~ totalsec ~ age ~ url ~ s1 ~ s2 ~ n1 ~ n2 ~ lang ~
				browserversion ~ osversion ⇒
				Action(id, visitorid, eventid, appid, osid, browserid, locationid, referer,
					campaignid, deviceid, ctime, ip, screenres, totalsec, age, url, s1, s2, n1,
					n2, lang, browserversion, osversion)
		}
	}

	case class Action(id: Long, visitorid: Long, eventid: Int, appid: Integer,
	                  osid: Byte, browserid: Byte, locationid: Short, referer: String,
	                  campaignid: Int, deviceid: Short, ctime: Date,
	                  ip: String, screenres: String, totalsec: Int, age: Short,
	                  url: String, s1: String, s2: String, n1: Double, n2: Double,
	                  lang: String, browserversion: BigDecimal, osversion: Short)

	val actionsParser: ResultSetParser[List[Action]] = {
		actionParser *
	}

	def bindRequest(implicit request: Request[Any]): Action = {
		Action(
			RParser.pL("id"),
			RParser.pL("visitorid"),
			RParser.pI("eventid"),
			RParser.pI("appid"),
			RParser.pb("osid"),
			RParser.pb("browserid"),
			RParser.pSh("locationid"),
			RParser.pS("referer"),
			RParser.pI("campaignid"),
			RParser.pSh("deviceid"),
			RParser.pDate("ctime"),
			RParser.pS("ip"),
			RParser.pS("screenres"),
			RParser.pI("totalsec"),
			RParser.pSh("age"),
			RParser.pS("url"),
			RParser.pS("s1"),
			RParser.pS("s2"),
			RParser.pF("n1"),
			RParser.pF("n2"),
			RParser.pS("lang"),
			RParser.pF("browserversion"),
			RParser.pSh("osversion")
		)
	}

	def create(a: Action): Long = DB.withConnection { implicit c ⇒
		//create an user
		val actionid =
			SQL"""insert into tbaction (visitorid, eventid, appid, osid, browserid, locationid, referer,
				campaignid, deviceid, ctime, ip, screenres, totalsec, age, url,
				n1, n2, lang, s1, s2, browserversion, osversion) values
				(${a.visitorid},${a.eventid},${a.appid}, ${a.osid}, ${a.browserid},
				${a.locationid}, ${a.referer}, ${a.campaignid}, ${a.deviceid}, ${new java.util.Date()}, ${a.ip},
				${a.screenres}, ${a.totalsec}, ${a.age}, ${a.url}, ${a.n1}, ${a.n2}, ${a.lang}, ${a.s1},
			${a.s2}, ${a.browserversion}, ${a.osversion}) returning id""".as(SqlParser.long("id").single)

		actionid
	}

	def update(a: Action) {
		DB.withConnection { implicit c ⇒ SQL"""UPDATE public.tbaction
					SET visitorid=${a.visitorid}, eventid=${a.eventid}, appid=${a.appid}, osid=${a.osid}, browserid=${a.browserid}, locationid=${a.locationid},
					referer=${a.referer}, campaignid=${a.campaignid}, deviceid=${a.deviceid}, ctime=${a.ctime}, ip=${a.ip}, screenres=${a.screenres},
					totalsec=${a.totalsec}, age=${a.age}, url=${a.url}, n1=${a.n1}, n2=${a.n2}, lang=${a.lang}, s1=${a.s1},
					s2=${a.s2}, browserversion=${a.browserversion}, osversion=${a.osversion}
					WHERE id = ${a.id}""".execute()
		}
	}

	def updateVisitor(oldVisitorid: Long, visitorid: Long) = DB.withConnection { implicit c ⇒
		SQL"UPDATE public.tbaction SET visitorid=$visitorid WHERE visitorid = $oldVisitorid".execute()
	}

}
