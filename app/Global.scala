import javax.mail.Message.RecipientType
import org.codemonkey.simplejavamail.{TransportStrategy, Mailer, Email}
import play.api._
import play.api.Play.current
import play.api.libs.concurrent._
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.duration._

object Global extends GlobalSettings {
	override def onStart(app: Application) {
		Logger.info("Application has started")
		startAwesomeBackgroundTask
	}

	override def onStop(app: Application) {
		Logger.info("Application shutdown...")
	}

	def startUpdateSegmentation = {
		Akka.system.scheduler.schedule(0.seconds, 5.seconds) {
			models.Segmentation.refresh()
		}
	}

	def startAwesomeBackgroundTask = {
		Akka.system.scheduler.schedule(0 seconds, 5 seconds) {
			val email = new Email()

			email.setFromAddress("FooBar Daemon", "no-reply@gmail.com");
			email.setSubject("Alert!");
			email.addRecipient("Foo Bar", "foo.bar@gmail.com", RecipientType.TO);
			email.setTextHTML("<img src='cid:wink1'><b>There was an alert!</b><img src='cid:wink2'>");

			new Mailer("smtp.gmail.com", 465, "user@gmail.com", "password", TransportStrategy.SMTP_SSL).sendMail(email);
		}
	}
}