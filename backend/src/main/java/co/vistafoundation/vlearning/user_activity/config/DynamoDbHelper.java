/**
 * 
 */
package co.vistafoundation.vlearning.user_activity.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;

/**
 * @author NaveenKumar
 *
 */

@Component
public class DynamoDbHelper {
	
	@Autowired
	StudentSubscriptionRepository  studentSubscriptionRepository;
	
	
	/**
	 * Retrieves a composite key combining the provided userId and subjectId
	 * based on the requested product line.
	 *
	 * @param idVluser The ID of the VL user.
	 * @param idProductline The ID of the requested product line.
	 * @return A list of composite keys combining the VL user ID and subject ID.
	 *         Each composite key is represented as a string.
	 */
	public List<String> getCompositeKeyForVSL(Long idVluser , Long idProductline)
	{    
		List<String> compositeKeys = new ArrayList<>();
		
		List<Long> idSubjectList = studentSubscriptionRepository.getListofIdSubjectBasedOnSubscription(idVluser,idProductline,Boolean.TRUE);
		
		idSubjectList.stream().forEach(idSubject -> compositeKeys.add(idVluser+"#"+idSubject));
		
		return compositeKeys;
	}

}
