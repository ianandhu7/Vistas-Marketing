package co.vistafoundation.vlearning.firebase.config.utility;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.Parameter;
import com.google.firebase.remoteconfig.ParameterValue;
import com.google.firebase.remoteconfig.ParameterValueType;
import com.google.firebase.remoteconfig.Template;

import co.vistafoundation.vlearning.firebase.config.dto.RequestParameter;

@Component
public class FirebaseConfigUtility {

	@Value("${firebase.remote.config}")
	private String firebaseRemoteConfig;

	public Template updatePageParameter(String pageName, RequestParameter requestParameter)
			throws InterruptedException, ExecutionException, JsonProcessingException {

		Template template = FirebaseRemoteConfig.getInstance().getTemplateAsync().get();

		Parameter parameter = new Parameter();
		
		  ObjectMapper mapper = new ObjectMapper();
          String defaultValue = mapper.writeValueAsString(requestParameter.getDefaultValueMap());
           System.out.println(defaultValue);
          
		parameter.setDefaultValue(ParameterValue.Explicit.of(defaultValue));

		for (Entry<String, Map<String, Boolean>> conditionalValue : requestParameter.getConditionalValuesMap()
				.entrySet()) {

			System.out.println(conditionalValue.getKey());
			System.out.println(conditionalValue.getValue().toString());
			
			  String value = mapper.writeValueAsString(conditionalValue.getValue());
			  System.out.println(value);
			parameter.getConditionalValues().put(conditionalValue.getKey(),
					ParameterValue.Explicit.of(value));

		}
		parameter.setDescription(requestParameter.getDescription());
		parameter.setValueType(ParameterValueType.valueOf(requestParameter.getValueType()));

		template.getParameterGroups().get(firebaseRemoteConfig).getParameters().put(pageName, parameter);

		System.out.println(template.toJSON());
		
		return template;

	}

}
