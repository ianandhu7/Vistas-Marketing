package co.vistafoundation.vlearning.user.util;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUser {

	private final JdbcTemplate jdbcTemplate;

	public DeleteUser(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public boolean deleteDataForIdvlUser(Long idvlUser) {
		System.out.println("deleting.........");
		try {
			 jdbcTemplate.execute("START TRANSACTION; ");
	          jdbcTemplate.execute("DELETE FROM blog_comment WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM change_mobile WHERE user_sur_id = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM user_notification WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM extra_curricular_lead_attended_class WHERE id_vl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM forgot_password WHERE idvluser = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM lead_attended_class WHERE id_vl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM lead_batch_details WHERE id_vl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM lead_batch_details_extra_curricular WHERE id_vluser = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM lead_batch_log WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM live_class_q_and_a WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM lead_batch_log_extra_curricular WHERE id_vluser = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM live_class_question WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM notify_live_class WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM report WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM social_video_rating WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM user_video_view_history WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM video_comment WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM video_like_dislike WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM video_view WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM social_video WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM staging_student_subscription WHERE idVL_USER = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM staging_user_subscr_pmt WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM user_subscr_pmt WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM offline_video_course_rating WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM notification WHERE from_user_id = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM notification WHERE to_user_id = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM user_device WHERE idvluser = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM user_live_class_attended WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM user_cart WHERE idvluser = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM signup_platform WHERE idVL_USER = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM student_subscription WHERE idVL_USER = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM student_order WHERE idVL_USER = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM student WHERE idvl_user = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM user_role_map WHERE vl_user_sur_id = " + idvlUser );
	          jdbcTemplate.execute("DELETE FROM vl_user WHERE user_sur_id = " + idvlUser );
	    
	      jdbcTemplate.execute("COMMIT;");
	      return true;
	    } catch (DataAccessException e) {
	        jdbcTemplate.execute("ROLLBACK;");
	        throw e;  
	    }
	}
}
