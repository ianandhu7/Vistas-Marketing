/**
 * 
 */
package co.vistafoundation.vlearning.syllabus.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.syllabus.service.SyllabusService;

/**
 * @author NaveenKumar
 *
 */

@Service
public class SyllabusServiceImpl implements SyllabusService {

	@Autowired
	private SyllabusRepository syllabusRepository;

	@Override
	public Document<List<Syllabus>> getAllSyllabusForPublic() {

		Document<List<Syllabus>> result = new Document<>();

		try {
			List<Syllabus> listsOfAllSyllabus = syllabusRepository.findAll();

			if (listsOfAllSyllabus.isEmpty())
				throw new NullPointerException();

			List<Syllabus> response = listsOfAllSyllabus.stream()
					.filter(s -> !s.getSyllabusName().equalsIgnoreCase("NA")).collect(Collectors.toList());
			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Syllabus Lists");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());

		}

		return result;
	}

}
