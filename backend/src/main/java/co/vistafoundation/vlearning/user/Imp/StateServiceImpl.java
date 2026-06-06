/**
 * 
 */
package co.vistafoundation.vlearning.user.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.service.StateService;

/**
 * @author vk
 *
 */
@Service
public class StateServiceImpl implements StateService{
	
	@Autowired
	StateRepository stateRepository;

	@Override
	public List<State> getAllStates() {
		return stateRepository.findAllByOrderByDisplayOrderAsc();
	}

}
