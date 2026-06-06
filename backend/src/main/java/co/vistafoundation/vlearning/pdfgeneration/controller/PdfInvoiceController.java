package co.vistafoundation.vlearning.pdfgeneration.controller;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import co.vistafoundation.vlearning.pdfgeneration.service.PdfInvoiceService;

@RestController
@RequestMapping("/api/v1/pdf")
public class PdfInvoiceController {
	@Autowired
	PdfInvoiceService invoiceService;

	@GetMapping(value = "/generate-invoice", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> generatePdf(@RequestParam String orderId) throws IOException, DocumentException {
	 
	  ByteArrayInputStream bis = invoiceService.generatePdf(orderId);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attachment; filename="+orderId+"-invoice.pdf");

	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(bis));
	}

}
