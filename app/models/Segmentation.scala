package models


import java.util.Date
import anorm.SqlParser._
import anorm.{ResultSetParser, RowParser, ~, _}
import play.api.Play.current
import play.api.db.DB
import play.api.mvc.Request
import utils.RParser

object Segmentation {
	def refresh(): Unit = {
		//lap qua toan bo cac segment
		SQL"select * from tbsegmentation order by lastupdatetime limit 1"

		//run lai querry

		//lọc toàn bộ visitor không có trong segment hiện tại,
		//insert user mới, tăng chỉ số index lên 1 dv

		//xóa toàn bộ những visitor không thỏa mãn.
	}

}
