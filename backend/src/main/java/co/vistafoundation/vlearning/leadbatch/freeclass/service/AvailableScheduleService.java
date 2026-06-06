package co.vistafoundation.vlearning.leadbatch.freeclass.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.AvailableSchedule;

public interface AvailableScheduleService {
	
	public Document<List<AvailableSchedule>> getAllSchedules();
	

}
