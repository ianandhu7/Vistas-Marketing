package co.vistafoundation.vlearning.leadbatch.freeclass.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.AvailableSchedule;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.AvailableScheduleRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.service.AvailableScheduleService;
@Service
public class AvailableScheduleServiceImpl implements AvailableScheduleService{
	
	@Autowired
	private AvailableScheduleRepository availableScheduleRepository;

	@Override
	public Document<List<AvailableSchedule>> getAllSchedules() {
		
		Document<List<AvailableSchedule>> result = new Document<>();
		try {
			
			List<AvailableSchedule> avslist =availableScheduleRepository.findAll();
			if(avslist.isEmpty()) throw new NullPointerException("No Available Scheduled Details found");
			
			result.setData(avslist);
			result.setMessage("List fetched successfully");
			result.setStatusCode(HttpStatus.OK.value());
			return result;
			
		}
		catch(Exception exp) {
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return result;
		}
		
	}

}
