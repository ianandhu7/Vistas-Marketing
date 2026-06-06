package co.vistafoundation.vlearning.user.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	LanguageRepository languageRepository;
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getAllLanguages() {
		Document result = new Document();	
		
		try {
			List<Language> languages = languageRepository.findAll();
			if(languages.isEmpty())
				throw new AppException("No Languages found!!!");
			
			result.setData(languages);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;
		}
		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

}
