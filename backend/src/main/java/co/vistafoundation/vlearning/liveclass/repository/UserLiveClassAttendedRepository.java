package co.vistafoundation.vlearning.liveclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.liveclass.model.UserLiveClassAttended;

public interface UserLiveClassAttendedRepository extends JpaRepository<UserLiveClassAttended, Long>{

	public UserLiveClassAttended findByIdLiveClassAndUserSurId(Long idLiveClass, Long userSurId);
	
	@Query(value = "select total_conducted.class_conducted_date,total_conducted.total_live_classes,IFNULL(student_attended.total_class_attended_student,0)"
			+ " from"
			+ "	(select date(lc.actual_class_start_date) as class_conducted_date,count(lc.idlive_class) as total_live_classes"
			+ "	from live_class lc where(lc.idclass_standard=:idclass_standard or lc.idclass_standard = 4) and live_completion_flag =:live_completion_flag"
			+ "	group by class_conducted_date) total_conducted"
			+ " LEFT OUTER JOIN"
			+ " (select date(ula.created_at) as class_attended_date,count(ula.iduser_live_class_attended) as total_class_attended_student"
			+ "	from user_live_class_attended ula"
			+ "	join"
			+ "	live_class lc"
			+ " on"
			+ "	lc.idlive_class = ula.idlive_class"
			+ "	where ula.idvl_user =:idvl_user"
			+ "	and (lc.idclass_standard =:idclass_standard or lc.idclass_standard = 4)"
			+ "	group by class_attended_date"
			+ " ) student_attended"
			+ " ON"
			+ " total_conducted.class_conducted_date = student_attended.class_attended_date limit 10",nativeQuery=true)
	public Object[] getGraphData(@Param("idclass_standard") Long idClassStandard,@Param("live_completion_flag") Boolean completionFlag,@Param("idvl_user") Long userSurId);
	
	
	
	
}
