package co.vistafoundation.vlearning.user.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SecondaryLanguageMapper {
	public String getCorrespondingLanguages(String id) {
		// Create a map with ID and language name mappings
		Map<Integer, String> languageMap = new HashMap<>();
		languageMap.put(1, "Kannada");
		languageMap.put(2, "Tamil");
		languageMap.put(3, "Telugu");
		languageMap.put(4, "Hindi");
		languageMap.put(5, "Malayalam");
		languageMap.put(6, "Marathi");
		languageMap.put(7, "English");

		return languageMap.get(Integer.parseInt(id));

	}
}
