package co.vistafoundation.vlearning.firebase.config.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.firebase.config.dto.PageDto;
import co.vistafoundation.vlearning.firebase.config.dto.RequestParameter;
import co.vistafoundation.vlearning.firebase.config.dto.ResponseParameter;

public interface FirebaseConfigService {

	public Document<PageDto> getPages(int pageNumber, int pageSize);

	public Document<ResponseParameter> getPageParameterList(String pageName);

	public Document<String> updatePageParameter(String pageName,RequestParameter requestParameter);

	
}
