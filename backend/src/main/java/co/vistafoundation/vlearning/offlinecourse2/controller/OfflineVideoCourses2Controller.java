package co.vistafoundation.vlearning.offlinecourse2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.service.OfflineCourseService;

@RestController
@RequestMapping("api/v2/offline-video-course/")

public class OfflineVideoCourses2Controller {

	@Autowired
	private OfflineCourseService offlineCourseService;

	/**
	 * @author Naveen Kumar A
	 * @param productLineCd
	 * @return This Method will be return top 10 offline video course.
	 */
	@GetMapping(value = "")
	public ResponseEntity<?> getTop10LatestPosts(@RequestParam String productLineCd) {

		Document<List<OfflineVideoCourse>> document = offlineCourseService.getTop10LatestPosts(productLineCd);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idSubject
	 * @return This Method will be return top 10 offline video course.
	 */
	@GetMapping(value = "subject/{idSubject}")
	public ResponseEntity<?> getTop10VideosByIdSubject(@PathVariable Long idSubject) {

		Document<List<OfflineVideoCourse>> document = offlineCourseService.getTop10VideosByIdSubject(idSubject);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
}
