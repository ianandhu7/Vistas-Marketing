/**
 * 
 */
package co.vistafoundation.vlearning.auth.dto;

import java.util.LinkedHashMap;

/**
 * @author NAVEEN
 *
 */
public class PaytmOrderStatusResponse {

	
	
	private LinkedHashMap<String, String> head;
	
	private  PaymentOrderBody  body;

	/**
	 * @return the head
	 */
	public LinkedHashMap<String, String> getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(LinkedHashMap<String, String> head) {
		this.head = head;
	}

	/**
	 * @return the body
	 */
	public PaymentOrderBody getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(PaymentOrderBody body) {
		this.body = body;
	}
	
	

}
