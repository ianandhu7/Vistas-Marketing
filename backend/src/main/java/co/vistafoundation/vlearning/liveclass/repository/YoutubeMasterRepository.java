/**
 * 
 */
package co.vistafoundation.vlearning.liveclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.liveclass.model.YoutubeMaster;

/**
 * @author vk
 *
 */
public interface YoutubeMasterRepository extends JpaRepository<YoutubeMaster, Long>{

	Boolean existsByYoutubeUserId(String youtubeUserId);

	YoutubeMaster findByIdTeacherAndIdYoutubeMaster(Long idTeacher, Long idYoutubeMaster);

	YoutubeMaster findByIdYoutubeMaster(Long idYoutubeMaster);

	YoutubeMaster findByIdTeacher(Long idTeacher);

}
