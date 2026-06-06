package co.vistafoundation.vlearning.pdfgeneration.service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.DocumentException;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.utils.EmailServiceUtil;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.specialoffer.model.Coupon;
import co.vistafoundation.vlearning.specialoffer.repository.CouponRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.model.StagingStudentSubscription;
import co.vistafoundation.vlearning.subscription.model.StudentOrder;
import co.vistafoundation.vlearning.subscription.repository.StagingStudentSubscriptionRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentOrderRepository;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionService;

/*
 * @author Abdul Elahi 
 */

@Service
public class PdfInvoiceServiceImpl implements PdfInvoiceService {
	
	@Autowired
	Environment environment;

	@Autowired
	ClassRepository classStandardRepo;

	@Autowired
	SubjectRepository subjectRepo;
	
	@Autowired
	StagingStudentSubscriptionRepository stagingStudentSubscriptionRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	EmailServiceUtil emailServiceUtil;
	
	@Autowired
	@org.springframework.context.annotation.Lazy
	StudentSubscriptionService studentSubscriptionService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StudentOrderRepository studentOrderRepository;
	
	@Autowired
	CouponRepository couponRepository;

	public ByteArrayInputStream generatePdf(String orderId) throws IOException, DocumentException {
		
		Document<?> doc= new Document<>();
		
			StudentOrder sOrder = studentOrderRepository.findByOrderId(orderId); 
		    if (sOrder == null) {
		        doc.setData(null);
		        doc.setStatusCode(500);
		        doc.setMessage("Student Subscription not found.");
		        return new ByteArrayInputStream(null);
		    }
		    StagingStudentSubscription sss = stagingStudentSubscriptionRepository.getByOrderId(orderId);

		    if (sss == null) {
		        doc.setData(null);
		        doc.setStatusCode(500);
		        doc.setMessage("Student Subscription not found.");
		        return new ByteArrayInputStream(null);
		
		    }  else {
		    String orderStatus = sss != null ? sss.getPaymentStatus() : null;
		
		    String amount = sss != null ? sss.getPurchaseAmount() : null;
		    
		    User user = userRepository.findByUserSurId(sss != null ? sss.getUserSurId():null);
		    
		    if (user == null)
		    	throw new AppException("Invalid User data found.");
		    
		    Instant purchaseDateTime = sss != null && sss.getPurchaseDate() != null ? sss.getPurchaseDate() : (sss != null ? sss.getCreatedAt() : null);

		    LocalDateTime purchaseDate = LocalDateTime.ofInstant(purchaseDateTime, ZoneId.systemDefault());
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		    String formattedPurchaseDate = formatter.format(purchaseDate);

		    Coupon coupon =null;
		    if(sOrder.getCouponCode()!=null){
		    	coupon = couponRepository.findByCouponCode(sOrder.getCouponCode().trim());
		    }
		    
		    String subscriptionType= sss.getSubscriptionType()==null?"--":sss.getSubscriptionType();
	    
		    String particulars = ("Vistas Learning Premium "+subscriptionType +" Subscription").toUpperCase();

		    if (amount == null || formattedPurchaseDate == null || particulars == null || user == null) {
		        doc.setData(null);
		        doc.setStatusCode(500);
		        doc.setMessage("Incomplete subscription details");
		        return new ByteArrayInputStream(null);
		    }

		    String custName = user.getFirstName() != null ? user.getFirstName() : "";
		    float amountFloat = Float.parseFloat(amount);
		    float cgst = (amountFloat * 9) / 100;
		    float sgst = (amountFloat * 9) / 100;
		    float priceWithoutGST = (amountFloat * 82) / 100;
		    String couponCode = sOrder.getCouponCode()==null?"NA":sOrder.getCouponCode();
		    String couponDescription = "NA";
		    String actualAmount =  sss != null ? sss.getPurchaseAmount() : null;
		    if(sOrder.getCouponCode()!=null) {
		    couponDescription = coupon.getCouponType().equalsIgnoreCase("DISCOUNT_PRICE")?String.valueOf(coupon.getDiscount()):
		    	String.valueOf(coupon.getDiscount()).concat(" %");
		    actualAmount = sOrder.getActualAmount().toString();
		    	
		    }
		    String amountInWords="";
		   
		    if(amountFloat==999) {
		    	amountInWords="Nine hundred ninty nine rupees.";
		    }else if(amountFloat==399) {
		    	amountInWords="Three hundred ninty nine rupees.";
		    }
		    int taxPercentage=9;
		    int quantity=1;
	    
		    String successData = "<!DOCTYPE html>\r\n"
		    		+ "<html>\r\n"
		    		+ "<head>\r\n"
		    		+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
		    		+ "<title>Invoice</title>\r\n"
		    		+ "<style>\r\n"
		    		+ "    * {\r\n"
		    		+ "        font-family: arial, sans-serif;\r\n"
		    		+ "  box-sizing: border-box;\r\n"
		    		+ "}\r\n"
		    		+ "\r\n"
		    		+ "    .column {\r\n"
		    		+ "  float: left;\r\n"
		    		+ "  width: 50%;\r\n"
		    		+ "  padding: 10px;\r\n"
		    		+ "  height: 300px; /* Should be removed. Only for demonstration */\r\n"
		    		+ "}\r\n"
		    		+ "\r\n"
		    		+ "/* Clear floats after the columns */\r\n"
		    		+ ".row:after {\r\n"
		    		+ "  content: \"\";\r\n"
		    		+ "  display: table;\r\n"
		    		+ "  clear: both;\r\n"
		    		+ "}\r\n"
		    		+ "    p{\r\n"
		    		+ "        font-size: 12px;\r\n"
		    		+ "    }\r\n"
		    		+ "    .main-container{\r\n"
		    		+ "        padding: 0px;\r\n"
		    		+ "    }\r\n"
		    		+ "    table {\r\n"
		    		+ "        font-family: arial, sans-serif;\r\n"
		    		+ "        border-collapse: collapse;\r\n"
		    		+ "        width: 100%;\r\n"
		    		+ "    }\r\n"
		    		+ "    table td, th {\r\n"
		    		+ "        border: 1px solid #dddddd;\r\n"
		    		+ "        text-align: left;\r\n"
		    		+ "        padding: 8px;\r\n"
		    		+ "    }\r\n"
		    		+ "		th{padding:15px;}"
		    		+ "    .container{\r\n"
		    		+ "        border: 1px solid #000;\r\n"
		    		+ "        height: 130vh;\r\n"
		    		+ "    }\r\n"
		    		+ "    .header{\r\n"
		    		+ "        padding: 10px;\r\n"
		    		+ "    }\r\n"
		    		+ "    .left-header{\r\n"
		    		+ "        display: inline-block;\r\n"
		    		+ "        padding-left: 30px;\r\n"
		    		+ "    }\r\n"
		    		+ "    .right-header{\r\n"
		    		+ "        display: inline-block;\r\n"
		    		+ "        padding-left: 50px;\r\n"
		    		+ "        width: 50%;\r\n"
		    		+ "    }\r\n"
		    		+ "    .right-header-heading{\r\n"
		    		+ "        font-size: 20px;\r\n"
		    		+ "        font-weight: bold;\r\n"
		    		+ "    }\r\n"
		    		+ "    .header-sub-heading{\r\n"
		    		+ "        line-height: 1.5;\r\n"
		    		+ "    }\r\n"
		    		+ "    .left-col-tbl{\r\n"
		    		+ "        display: inline-block;\r\n"
		    		+ "    }\r\n"
		    		+ "    .right-col-tbl{\r\n"
		    		+ "        display: inline-block;\r\n"
		    		+ "        padding-left: 20px;\r\n"
		    		+ "    }\r\n"
		    		+ "    .left-col-tbl p{\r\n"
		    		+ "        padding-left: 30px;\r\n"
		    		+ "        margin: unset;\r\n"
		    		+ "        line-height: 1.5;\r\n"
		    		+ "    }\r\n"
		    		+ "    .right-col-tbl p{\r\n"
		    		+ "        font-weight: bold;\r\n"
		    		+ "        margin: unset;\r\n"
		    		+ "        padding-left: 30px;\r\n"
		    		+ "        line-height: 1.5;\r\n"
		    		+ "    }\r\n"
		    		+ "    .bill-to{\r\n"
		    		+ "        border: 1px solid #dddddd;\r\n"
		    		+ "        background-color: #dfdfdf;\r\n"
		    		+ "        padding-left: 10px;\r\n"
		    		+ "        padding-top: 5px;\r\n"
		    		+ "        padding-bottom: 5px\r\n"
		    		+ "    }\r\n"
		    		+ "    .user-name{\r\n"
		    		+ "        padding: 10px;\r\n"
		    		+ "    }\r\n"
		    		+ "    .inner-th{\r\n"
		    		+ "        padding: unset;\r\n"
		    		+ "    }\r\n"
		    		+ "    .inner-th-item1{\r\n"
		    		+ "        padding-top: 10px;\r\n"
		    		+ "        padding-bottom: 10px;\r\n"
		    		+ "        border-bottom: 1px solid #000;\r\n"
		    		+ "        text-align: center;\r\n"
		    		+ "    }\r\n"
		    		+ "    .inner-th-item2{\r\n"
		    		+ "        padding-top: 10px;\r\n"
		    		+ "        padding-bottom: 10px;\r\n"
		    		+ "        width: 48%;\r\n"
		    		+ "        text-align: center;\r\n"
		    		+ "        display: inline-block;\r\n"
		    		+ "    }\r\n"
		    		+ "    .inner-th-item3{\r\n"
		    		+ "        border-left: 1px solid #000;\r\n"
		    		+ "        padding-top: 10px;\r\n"
		    		+ "        padding-bottom: 10px;\r\n"
		    		+ "        width: 48%;\r\n"
		    		+ "        text-align: center;\r\n"
		    		+ "        display: inline-block;\r\n"
		    		+ "    }\r\n"
		    		+ "    .item-details-tbl th{\r\n"
		    		+ "        background-color: #dfdfdf;\r\n"
		    		+ "        border: 1px solid #000;\r\n"
		    		+ "        padding: 1px !important;\r\n"
		    		+ "    }\r\n"
		    		+ "    .item-details-tbl th, td{\r\n"
		    		+ "        width:10%;\r\n"
		    		+ "        text-align: center;\r\n"
		    		+ "        font-size: 12px;\r\n"
		    		+ "    }\r\n"
		    		+ "    .footer-item-left{\r\n"
		    		+ "        padding-top: 30px;\r\n"
		    		+ "        padding-left: 30px;\r\n"
		    		+ "        \r\n"
		    		+ "    }\r\n"
		    		+ "    .footer-item-right{\r\n"
		    		+ "        padding-top: 10px;\r\n"
		    		+ "    }\r\n"
		    		+ "    .right-col-tbl{\r\n"
		    		+ "        padding-right: 20px;\r\n"
		    		+ "    }\r\n"
		    		+ "    .grid{\r\n"
		    		+ "        list-style: none;\r\n"
		    		+ "        margin: 0;\r\n"
		    		+ "        padding: 0;\r\n"
		    		+ "        display: grid;\r\n"
		    		+ "        grid-auto-flow: column;\r\n"
		    		+ "    }\r\n"
		    		+ "    .txt-end{\r\n"
		    		+ "        text-align: end;\r\n"
		    		+ "    }\r\n"
		    		+ "    .price-details p{\r\n"
		    		+ "        margin: unset;\r\n"
		    		+ "        list-style: 1.5;\r\n"
		    		+ "    }\r\n"
		    		+ "    .brd{\r\n"
		    		+ "        border: 1px solid #dfdfdf;\r\n"
		    		+ "    }\r\n"
		    		+ "    .footer-item-right{\r\n"
		    		+ "        border: 1px solid #dfdfdf;\r\n"
		    		+ "    }\r\n"
		    		+ "    .signature-txt{\r\n"
		    		+ "        text-align: center;\r\n"
		    		+ "        padding-top: 80px;\r\n"
		    		+ "    }\r\n"
		    		+ "    .signature-box{\r\n"
		    		+ "        border: 1px solid #dfdfdf;\r\n"
		    		+ "    }\r\n"
		    		+ "    .fr-pad{\r\n"
		    		+ "        padding-top: 0;\r\n"
		    		+ "    padding-right: 0;\r\n"
		    		+ "    }\r\n"
		    		+ "    /* .grid-col{\r\n"
		    		+ "    padding: 1%;\r\n"
		    		+ "    } */\r\n"
		    		+ "		td{"
		    		+ "			text-align:center;"
		    		+ "		}"
		    		+ "</style>\r\n"
		    		+ "</head>\r\n"
		    		+ "<body>\r\n"
		    		+ "    <div class=\"main-container\">\r\n"
		    		+ "        <div>\r\n"
		    		+ "            <div class=\"container\">\r\n"
		    		+ "                <div class=\"header\">\r\n"
		    		+ "                    <div class=\"left-header\">\r\n"
		    		+ "                        <h2>Dreammithra Private Limited</h2>\r\n"
		    		+ "                        <p class=\"header-sub-heading\">Site No. 35, No. 1, 2nd Floor, NGR Residency, Roopena Agrahara,</br>\r\n"
		    		+ "                            Begur Hobli, Bengaluru,</br>\r\n"
		    		+ "                            Karnataka 560068, India</br>\r\n"
		    		+ "                            GSTIN 29AAGCD9650E1ZF</br>\r\n"
		    		+ "                            CIN No: U72900KA2018PTC118831</br>\r\n"
		    		+ "                        </p>\r\n"
		    		+ "                    </div>\r\n"
		    		+ "                    <div class=\"right-header\">\r\n"
		    		+ "                        <div class=\"right-header-heading\">TAX INVOICE</div>\r\n"
		    		+ "                    </div>\r\n"
		    		+ "                </div>\r\n"
		    		+ "                <div class=\"header-table\">\r\n"
		    		+ "                    <table class=\"invoice-header-table\">\r\n"
		    		+ "                        <tr>\r\n"
		    		+ "                            <td>\r\n"
		    		+ "                                <div class=\"left-col-tbl\">\r\n"
		    		+ "                                    <p>Invoice No</p>\r\n"
		    		+ "                                    <p>Invoice Date</p>\r\n"
		    		+ "                                    <p>Due Date</p>\r\n"
		    		+ "                                    <p>Terms</p>\r\n"
		    		+ "                                </div>\r\n"
		    		+ "                                <div class=\"right-col-tbl\">\r\n"
		    		+ "                                    <p>: "+orderId+ " </p>\r\n"
		    		+ "                                    <p>: "+formattedPurchaseDate+ "</p>\r\n"
		    		+ "                                    <p>: "+formattedPurchaseDate+"</p>\r\n"
		    		+ "                                    <p>: Due on Receipt</p>\r\n"
		    		+ "                                </div>\r\n"
		    		+ "                            </td> \r\n"
		    		+ "                            <td>\r\n"
		    		+ "                                <div class=\"left-col-tbl\">\r\n"
		    		+ "                                    <p>Place of Supply</p>\r\n"
		    		+ "                                </div>\r\n"
		    		+ "                                <div class=\"right-col-tbl\">\r\n"
		    		+ "                                    <p>: Bangalore</p>\r\n"
		    		+ "                                </div>\r\n"
		    		+ "                            </td> \r\n"
		    		+ "                        </tr>\r\n"
		    		+ "                    </table>\r\n"
		    		+ "                </div>\r\n"
		    		+ "                <div class=\"bill-to\">\r\n"
		    		+ "                    Bill to\r\n"
		    		+ "                </div>\r\n"
		    		+ "                <div class=\"user-name\">\r\n"
		    		+ "                    <h3> "+custName+"</h3>\r\n"
		    		+ "                </div>\r\n"
		    		+ "                <div class=\"item-details\">\r\n"
		    		+ "                    <table class=\"item-details-tbl\">\r\n"
		    		+ "                        <tr>\r\n"
		    		+ "                            <th style='padding-top:10px !important; padding-bottom:10px !important;'>#</th>\r\n"
		    		+ "                            <th style='padding-top:10px !important; padding-bottom:10px !important;'>Item & Description</th>\r\n"
		    		+ "                            <th style='padding-top:10px !important; padding-bottom:10px !important;'>HSN/SAC</th>\r\n"
		    		+ "                            <th style='padding-top:10px !important; padding-bottom:10px !important;'>Qty</th>\r\n"
		    		+ "                            <th style='padding-top:10px !important; padding-bottom:10px !important;'>Rate</th>\r\n"
		    		+ "                            <th style='padding-top:10px !important; padding-bottom:10px !important;'>Coupon Code</th>\r\n"
		    		+ "                            <th style='padding-top:10px !important; padding-bottom:10px !important;'>Discount</th>\r\n"

//		    		+ "                            <th class=\"inner-th\" colspan=\"2\">\r\n"
//		    		+ "                                <div class=\"inner-th-item1\">CGST</div>\r\n"
//		    		+ "                                <div class=\"inner-th-item2\">%</div>\r\n"
//		    		+ "                                <div class=\"inner-th-item3\">Amount</div>\r\n"
//		    		+ "                            </th>\r\n"
//		    		+ "                            <th class=\"inner-th\" colspan=\"2\">\r\n"
//		    		+ "                                <div class=\"inner-th-item1\">SGST</div>\r\n"
//		    		+ "                                <div class=\"inner-th-item2\">%</div>\r\n"
//		    		+ "                                <div class=\"inner-th-item3\">Amount</div>\r\n"
//		    		+ "                            </th>\r\n"
		    		+ "                            <th>Amount</th>\r\n"
		    		+ "                        </tr>\r\n"
		    		+ "                        <tr>\r\n"
		    		+ "                            <td style=\"text-align: center;\" class=\"tbl-td\">   1</td>\r\n"
		    		+ "                            <td class=\"tbl-td\">   "  +particulars+"</td>\r\n"
		    		+ "                            <td style=\"text-align: center;\" class=\"tbl-td\">   999294</td>\r\n"
		    		+ "                            <td style=\"text-align: center;\" class=\"tbl-td\">   "  +quantity+"</td>\r\n"
		    		+ "                            <td style=\"text-align: center;\" class=\"tbl-td\">   "  +actualAmount+"</td>\r\n"
		    		+ "                            <td style=\"text-align: center;\" class=\"tbl-td\">   "+couponCode+"</td>\r\n"
		    		+ "                            <td style=\"text-align: center;\" class=\"tbl-td\">   "+couponDescription+"</td>\r\n"

//		    		+ "                            <td style=\"text-align: center;\" class=\"tbl-td\">   "  +taxPercentage+"%</td>\r\n"
//		    		+ "                            <td style=\"text-align: center;\" class=\"tbl-td\">   "  +cgst+"</td>\r\n"
//		    		+ "                            <td style=\"text-align: center;\" class=\"tbl-td\">   "  +taxPercentage+"%</td>\r\n"
//		    		+ "                            <td style=\"text-align: center;\" class=\"tbl-td\">   "  +sgst+"</td>\r\n"
		    		+ "                            <td style=\"text-align: center;\" class=\"tbl-td\">   "  +amount+"</td>\r\n"
		    		+ "\r\n"
		    		+ "                        </tr>\r\n"
		    		+ "                    </table>\r\n"
		    		+ "                </div>\r\n"
		    		+ "                \r\n"
		    		+ "\r\n"
		    		+ "                <div class=\"row\">\r\n"
		    		+ "                    <div class=\"column\">\r\n"
		    		+ "                        <p>Total in words</p>\r\n"
		    		+ "                        <p><b> "+amountInWords+"</b></p>\r\n"
		    		+ "                        <br><br>\r\n"
		    		+ "                        <p>Thanks for your business</p>\r\n"
		    		+ "                    </div>\r\n"
		    		+ "                    <div class=\"column fr-pad\">\r\n"
		    		+ "                        <ul class=\"price-details brd grid\">\r\n"
		    		+ "                            <li class=\"left-col-tbl \">\r\n"

//		    		+ "                                <p>Coupon Code</p>\r\n"
//		    		+ "                                <p>Discount</p>\r\n"
		    		+ "                                <p>Sub total</p>\r\n"
		    		+ "                                <p>CGST (9%)</p>\r\n"
		    		+ "                                <p>SGST (9%)</p>\r\n"
		    		+ "                                <p>Rounding</p>\r\n"
		    		+ "                                <p><b>Total</b></p>\r\n"
		    		+ "                                <p><b>Balance Due</b></p>\r\n"
		    		+ "\r\n"
		    		+ "                            </li>\r\n"
		    		+ "                            <li class=\"right-col-tbl\">\r\n"
//		 
//		    		+ "                                <p>"+couponCode+"</p>\r\n"
//		    		+ "                                <p> "+couponDescription+"</p>\r\n"
		    	   	+ "                                <p> "+priceWithoutGST+"</p>\r\n"
		    		+ "                                <p> "+cgst+"</p>\r\n"
		    		+ "                                <p> "+sgst+"</p>\r\n"
		    		+ "                                <p> 0</p>\r\n"
		    		+ "                                <p><b> "+amount+"</b></p>\r\n"
		    		+ "                                <p><b> 0.0</b></p>\r\n"
		    		+ "                            </li>\r\n"
		    		+ "                        </ul>\r\n"
		    		+ "                        <div class=\"signature-box\">\r\n"
		    		+ "                            <p class=\"signature-txt\">Authorized Signature</p>\r\n"
		    		+ "                        </div>\r\n"
		    		+ "                    </div>\r\n"
		    		+ "                </div>\r\n"
		    		+ "            </div>\r\n"
		    		+ "        </div>\r\n"
		    		+ "    </div>\r\n"
		    		+ "</body>\r\n"
		    		+ "</html>";
		   
		   
		   String failData="<!DOCTYPE html>\n"
					+ "    <html lang=\"en\">\n"
					+ " <head>\n"
					+ "   <meta charset=\"UTF-8\">\n"
					+ "   <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
					+ "   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
					+ "   <title>Document</title>\n"
					+ "   <!-- <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css'> -->\n"
					+ " <style>\n"
					+ "body{"
					+ "margin:0; padding:0;"
					+ "}"
					+ " .container{background-color:#B4CDE6}\n"
					+ "   .main-div{\n"
					+ "       padding-top:3%;\n"

					+ "   }\n"
					+ "   .header-heading{\n"
					+ "       text-align: center;\n"
					+ "       width: 100%;\n"
					+ "   }\n"
					+ "   .address-col-right{\n"
					+ "       text-align: end;\n"
					+ "   }\n"
					+ "   .last-col{\n"
					+ "       background-color: #f7caac;\n"
					+ "       text-align: center;\n"
					+ "   }\n"
					+ "\n"
					+ "   .footer{\n"
					+ "       text-align: center;\n"
					+ "   }\n"
					+ "   \n"
					+ "   .mt-10{\n"
					+ "       margin-top: 100;\n"
					+ "   }\n"
					+ "   .mt-20{\n"
					+ "       margin-top: 200;\n"
					+ "   }\n"
					+ "   .col-2{\n"
					+ "       display: inline-block;\n"
					+ "       width: 60%;\n"
					
					+ "   }\n"
					+ "   .col-6{\n"
					+ "       margin-left: 0px;\n"
					+ "       display: inline-block;\n"
					+ "       width: 90%;\n"
					+ "   }\n"
					+ "   .container{\n"
					+ "       padding: 20px 100;\n"
					+ "   }\n"
					+ "   h4{\n"
					+ "       margin: 10px 0px;\n"
					+ "   }\n"
					+ "   .top-header-msg{\n"
					+ "        width: 100%;\n"
					+ "        background-color: red;\n"
					+ "        font-size: 28px;\n"
					+ "        color: #fff;\n"
					+ "        padding: 20px 5px;\n"
					+ "        text-align: center;\n"
					+ "   }\n"
					+ " \n"
					+ " .txt-center{text-align:center;}\n"
					+ "   .tbl1 th{\n"
					+ "       padding: 5px 10px;\n"
					+ "       border:2px solid #000 !important;\n"
					+ "       border-collapse: collapse;\n"
					+ "   }@media only screen and (max-width: 600px) {\n"
					+ "   .top-col{width:78%}\n"
					+ " }\n"
					+ ".top-col{"
					+ "text-align:center;"
					+ "}\n"
					+ "p{"
					+ "font-size:13pt;\n"
					+ "}"
					+ ".dm-h2{font-size:16pt;}"
					+ ".msg{font-size:15.4pt;}"
					+ " </style>\n"
					+ "</head>\n"
					+ "\n"
					+ "<body>\n"
					+ "   <div class=\"container main-div\"><br>\n"

					+ "           <div class=\"top-col\">\n"
					+ "               <img width=\"300px\" src='https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/vlearning_blue.png'>\n"
					+ "           </div>\n"
					+ "<br>"
					+ "           <div class=\" top-col\">\n"
					+ "               <div class=\"header-heading\">\n"
					+ "                   <h2>DREAMMITRA PRIVATE LIMITED</h2>\n"
					+ "                   <p>LIVE YOUR DREAM, TRUST THE PROCESS<br>CIN: U72900KA2018PTC118831</p>\n"
					+ "               </div>\n"

					+ "       </div>\n"
					+ "\n"
					+ "       <div class=\"top-header-msg\">\n"
					+ "            <span>Payment Failed </span>\n"
					+ "       </div>\n"
					+ "       \n"
					+ "\n"
					+ "				<p class=\"msg\"> <strong>Don't Worry your money is safe!</strong> If money was debited from your account, \n"
					+ "                    it will be refunded automatically in 5-7 working days!</p>\n"
					+ "       <p><strong>Order Id : </strong> "+orderId+" </p>\n"
					+ "       <p><strong>Date : </strong>"+formattedPurchaseDate+" </p>\n"
					+ "\n"
					+ "        <div class=\"bottom-msg mt-10\">\n"
					+ "            <p><strong>For any order related queries, <br> please reach out to us at</strong> <a href='mailto:support@v-learning.in'> support@v-learning.in</a> .<br>\n"
					+ "            <strong>Toll Free : </strong><a href='tel:+919945899989'>99458-99989  /  </a> <a href='tel:+919945899937'>99458-99937</a> <br></p>\n"
					+ "        </div>\n"
					+ "        <footer class=\"footer mt-20\">\n"
					+ "            <p> <a href='https://vistaslearning.com/'>www.vistaslearning.com</a> <br> 35/7, 2 nd Floor, NGR Layout, Roopena Agrahara, Bommanahalli, Bengaluru - 560068</p>\n"
					+ "        </footer>\n"
					+ "           </div>\n"
					+ "       </body>\n"
					+ "       \n"
					+ "       </html>";

		   
		   if(orderStatus!=null && orderStatus.equalsIgnoreCase("success")) {
			   ByteArrayOutputStream out = new ByteArrayOutputStream();
			   HtmlConverter.convertToPdf(successData, out);
			   return new ByteArrayInputStream(out.toByteArray());
		   }else {
			   ByteArrayOutputStream out = new ByteArrayOutputStream();
			   HtmlConverter.convertToPdf(failData, out);
			   return new ByteArrayInputStream(out.toByteArray());
		   }	}
	}

}
