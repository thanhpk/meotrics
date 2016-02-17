package utils

import java.util.{Date, Calendar}
import play.api.Logger
import play.api.mvc.{AnyContentAsFormUrlEncoded, Request}

object RParser {


	def pI(ref: String, default: Int = 0)(implicit request: Request[Any]): Integer = {
		try {
			request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head.toInt
		} catch {
			case e: Exception => default
		}
	}
	def pb(ref: String, default: Byte = 0)(implicit request: Request[Any]): Byte = {
		try {
			request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head.toByte
		} catch {
			case e: Exception => default
		}
	}
		def pSh(ref: String, default: Short = 0)(implicit request: Request[Any]): Short = {
		try {
			request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head.toShort
		} catch {
			case e: Exception => default
		}
	}

	def pS(ref: String, default: String = "")(implicit request: Request[Any]): String = {
		try request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head
		catch {
			case e: Exception => default
		}
	}

	def pDate(ref: String, default: Date = new Date)(implicit request: Request[Any]): Date = {
		try {
			val value = request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head
			Logger.info(value)
		} catch {
			case e: Exception => default
		}
		new Date()
	}

	def pL(ref: String, default: Long = 0)(implicit request: Request[Any]): Long = {
		try request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head.toLong
		catch {
			case e: Exception => default
		}
	}

	def pF(ref: String, default: Double = 0)(implicit request: Request[Any]): Double = {
		try request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head.toDouble
		catch {
			case e: Exception => default
		}
	}

	def pDec(ref: String, default: BigDecimal = 0)(implicit request: Request[Any]): BigDecimal = {
		try BigDecimal.exact(request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head)
		catch {
			case e: Exception => default
		}
	}

	def pB(ref: String, default: Boolean = false)(implicit request: Request[Any]): Boolean = {
		try request.body.asInstanceOf[AnyContentAsFormUrlEncoded].data.get(ref).get.head.toBoolean
		catch {
			case e: Exception => default
		}
	}

}
