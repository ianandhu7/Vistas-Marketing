package co.vistafoundation.vlearning.liveclass.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.liveclass.model.LiveClassWatchedHistory;

public interface LiveClassWatchedHistoryRepo extends JpaRepository<LiveClassWatchedHistory, Long> {

	public LiveClassWatchedHistory findByIdLiveClassAndIdVlUser(Long idLiveClass, Long idVlUser);
	
    
	@Query("select lcvh from LiveClassWatchedHistory lcvh inner join LiveClass lc on lcvh.idLiveClass = lc.idLiveClass  where lcvh.idVlUser=:idVlUser "
			+ "and (:idSubject is null or lcvh.idSubject = :idSubject) and (:idLanguage is null or lc.idLanguage = :idLanguage) and lc.classType = :type  ORDER BY lcvh.lastAccessedTimestamp DESC")
	public List<LiveClassWatchedHistory> findAllByIdVlUserAndIdSubjectAndIdLanguageOrderByLastAccessedTimestampDesc
	(Long idVlUser,Long idSubject,Long idLanguage,String type, Pageable pageable);
	
	
	public List<LiveClassWatchedHistory> findAllByIdVlUserOrderByLastAccessedTimestampDesc(Long idVlUser,
			Pageable pageable);

}
