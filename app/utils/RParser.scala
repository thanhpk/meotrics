package utils

import java.util.Calendar
import play.api.Logger
import play.api.mvc.{AnyContentAsFormUrlEncoded, Request}

object RParser {

	def parseInt(ref: String, default: Int = 0)(implicit request: Request[Any]): Integer = {
		try {
			request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head.toInt
		} catch {
			case e: Exception => default
		}
	}

	def parseString(ref: String, default: String = null)(implicit request: Request[Any]): String = {
		try request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head
		catch {
			case e: Exception => default
		}
	}

	def parseDate(ref: String, default: Calendar = null)(implicit request: Request[Any]): Calendar = {
		try {
			val value = request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head
			Logger.info(value)
		} catch {
			case e: Exception => default
		}
		Calendar.getInstance()
	}

	def parseLong(ref: String, default: Long = 0)(implicit request: Request[Any]): Long = {

		try request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head.toLong
		catch {
			case e: Exception => default
		}
	}

	def parseBool(ref: String, default: Boolean = false)(implicit request: Request[Any]): Boolean = {
		try request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head.toBoolean
		catch {
			case e: Exception => default
		}
	}

}
