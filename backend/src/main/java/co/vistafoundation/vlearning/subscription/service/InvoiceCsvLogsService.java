package co.vistafoundation.vlearning.subscription.service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.model.InvoiceCsvLogs;

public interface InvoiceCsvLogsService {

	Document<?> getByAll();
	
	Document<?> save(InvoiceCsvLogs invoiceCsvLogs);
	
	
}
