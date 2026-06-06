package co.vistafoundation.vlearning.pdfgeneration.service;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;

public interface PdfInvoiceService {
    ByteArrayInputStream generatePdf(String orderId) throws IOException, DocumentException;
}
