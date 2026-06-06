package co.vistafoundation.vlearning.classes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.service.ClassStandardService;
import co.vistafoundation.vlearning.common.response.Document;

@RestController
@RequestMapping("api/v1/classStandard/")
public class ClassStandardController {

	@Autowired
	private ClassStandardService classStandardService;

	/**
	 * @author Naveen Kumar A
	 * 
	 * @return This method will return list of class standards.
	 */
	@GetMapping(value = "")
	public ResponseEntity<?> getClassStandardMeta() {

		Document<List<ClassStandard>> result = new Document<>();

		List<ClassStandard> listOfClass = classStandardService.findAllClass();

		if (!listOfClass.isEmpty()) {
			result.setData(listOfClass);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
		} else {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage("No Class standard data found");
		}

		return ResponseEntity.status(result.getStatusCode()).body(result);
	}

	/**
	 * @author Naveen Kumar A
	 * 
	 * @return This method will return product group
	 * 
	 */
	@GetMapping(value = "product-group")
	public ResponseEntity<?> getClassStandardByProductGroup() {

		Document<List<ClassStandard>> result =

				classStandardService.getAllClassStandardProdutGroup();

		return ResponseEntity.status(result.getStatusCode()).body(result);
	}

	/**
	 * @author Naveen Kumar A
	 * 
	 * @return This method will return Academic product group
	 * 
	 */
	@GetMapping(value = "academic/product-group")
	public ResponseEntity<?> getClassStandardByAcademicProductGroup() {

		Document<List<ClassStandard>> result =

				classStandardService.getAllClassStandardAcademicProdutGroup();

		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	
	@GetMapping(value = "academic-list")
	public ResponseEntity<?> getClassStandardByIdStateAndSyllabus(@RequestParam Long idSyllabus, @RequestParam Long idState) {

		Document<List<ClassStandard>> result = classStandardService.getAllClassStandardByStateAndSyllabus(idState,idSyllabus);

		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
}
