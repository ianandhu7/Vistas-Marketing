package co.vistafoundation.vlearning.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.model.InvoiceCsvLogs;
import co.vistafoundation.vlearning.subscription.repository.InvoiceCsvLogsRepository;
@Service
public class InvoiceCsvLogsServiceImpl implements InvoiceCsvLogsService {
	@Autowired
	InvoiceCsvLogsRepository invoiceCsvLogsRepository;

	@Override
	public Document<?> getByAll() {
		
		return null;
	}

	@Override
	public Document<?> save(InvoiceCsvLogs invoiceCsvLogs) {
		Document <InvoiceCsvLogs> result = new Document<>();
		try {
			InvoiceCsvLogs invoiceCsvLogsRes= invoiceCsvLogsRepository.save(invoiceCsvLogs);
			
			result.setData(invoiceCsvLogsRes);
			result.setMessage("Successful");
			result.setStatusCode(HttpStatus.OK.value());
			
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		return result;
	}


	
	

}
