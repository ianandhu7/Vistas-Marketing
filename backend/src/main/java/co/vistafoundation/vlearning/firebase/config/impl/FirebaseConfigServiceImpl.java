package co.vistafoundation.vlearning.firebase.config.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.Template;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.firebase.config.dto.ConfigDTO;
import co.vistafoundation.vlearning.firebase.config.dto.PageDto;
import co.vistafoundation.vlearning.firebase.config.dto.Parameter;
import co.vistafoundation.vlearning.firebase.config.dto.ParameterGroup;
import co.vistafoundation.vlearning.firebase.config.dto.ParameterValue;
import co.vistafoundation.vlearning.firebase.config.dto.RequestParameter;
import co.vistafoundation.vlearning.firebase.config.dto.ResponseParameter;
import co.vistafoundation.vlearning.firebase.config.service.FirebaseConfigService;
import co.vistafoundation.vlearning.firebase.config.utility.FirebaseConfigUtility;

@Service
public class FirebaseConfigServiceImpl implements FirebaseConfigService {

	@Autowired
	FirebaseConfigUtility firebaseConfigUtility;

	@Value("${firebase.remote.config}")
	private String firebaseRemoteConfig;

	@Override
	public Document<PageDto> getPages(int pageNumber, int pageSize) {

		Document<PageDto> result = new Document<>();
		
		
		try {

			Template template = FirebaseRemoteConfig.getInstance().getTemplateAsync().get();

			ObjectMapper oMapper = new ObjectMapper();

			ConfigDTO configDTO = oMapper.convertValue(template, ConfigDTO.class);

			ParameterGroup parameterGroup = configDTO.getParameterGroups().get(firebaseRemoteConfig);

			Map<String, Parameter> parameters = parameterGroup.getParameters();

			List<String> pagesList = new ArrayList<>();
			
			PageDto pageDto =new PageDto();
			for (Map.Entry<String, Parameter> parameter : parameters.entrySet()) {

				pagesList.add(parameter.getKey());

			}
			int size=pagesList.size();
			
			pagesList = paginateList(pagesList, pageSize, pageNumber);
			pageDto.setPageList(pagesList);
			pageDto.setSize(size);
			
			result.setData(pageDto);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
		return result;
	}

	public static <T> List<T> paginateList(List<T> fullList, int itemsPerPage, int currentPage) {
		// Calculate the total number of pages
		int totalItems = fullList.size();
		int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

		// Ensure the current page is within the valid range
		if (currentPage > totalPages) {
			currentPage = totalPages;
		}
		if (currentPage < 1) {
			currentPage = 1;
		}

		// Calculate start and end index for the current page
		int startIndex = (currentPage - 1) * itemsPerPage;
		int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

		// Return the sublist representing the paginated portion
		return fullList.subList(startIndex, endIndex);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document<ResponseParameter> getPageParameterList(String pageName) {

		Document<ResponseParameter> result = new Document<>();
		try {

			Template template = FirebaseRemoteConfig.getInstance().getTemplateAsync().get();

			ObjectMapper oMapper = new ObjectMapper();

			ConfigDTO configDTO = oMapper.convertValue(template, ConfigDTO.class);

			ParameterGroup parameterGroup = configDTO.getParameterGroups().get(firebaseRemoteConfig);
			Parameter parameter = parameterGroup.getParameters().get(pageName);

			ObjectMapper objectMapper = new ObjectMapper();

			ResponseParameter parameter1 = new ResponseParameter();

			Map<String, Boolean> defaultValueMap = objectMapper
					.readValue(parameter.getDefaultValue().getValue().toString(), Map.class);

			parameter1.setDefaultValueMap(defaultValueMap);

			Map<String, Map<String, Boolean>> resConditionalValueMap = new HashMap<>();

			for (Entry<String, ParameterValue> conditionValue : parameter.getConditionalValues().entrySet()) {

				System.out.println(conditionValue.getKey());

				Map<String, Boolean> valueMap = objectMapper.readValue(conditionValue.getValue().getValue().toString(),
						Map.class);

				resConditionalValueMap.put(conditionValue.getKey(), valueMap);

			}

			parameter1.setConditionalValuesMap(resConditionalValueMap);

			parameter1.setDescription(parameter.getDescription());

			parameter1.setValueType(parameter.getValueType());

			result.setData(parameter1);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
		return result;

	}

	@Override
	public Document<String> updatePageParameter(String pageName, RequestParameter requestParameter) {

		Document<String> result = new Document<>();
		try {
		
			Template template = firebaseConfigUtility.updatePageParameter(pageName, requestParameter);

				
			try {
				  Template validatedTemplate = FirebaseRemoteConfig.getInstance()
				          .validateTemplateAsync(template).get();
				  System.out.println("Template was valid and safe to use");
				} catch (ExecutionException e) {
				  if (e.getCause() instanceof FirebaseRemoteConfigException) {
				    FirebaseRemoteConfigException rcError = (FirebaseRemoteConfigException) e.getCause();
				    System.out.println("Template is invalid and cannot be published");
				    System.out.println(rcError.getMessage());
				  }
				}
			
			try {
				  Template publishedTemplate = FirebaseRemoteConfig.getInstance()
				          .publishTemplateAsync(template).get();
				  System.out.println("Template has been published");
				  // See the ETag of the published template.
				  System.out.println("ETag from server: " + publishedTemplate.getETag());
				} catch (ExecutionException e) {
				  if (e.getCause() instanceof FirebaseRemoteConfigException) {
				    FirebaseRemoteConfigException rcError = (FirebaseRemoteConfigException) e.getCause();
				    System.out.println("Unable to publish template.");
				    System.out.println(rcError.getMessage());
				  }
				}

			result.setData("Template has been published");
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}
		return result;

	}

}
