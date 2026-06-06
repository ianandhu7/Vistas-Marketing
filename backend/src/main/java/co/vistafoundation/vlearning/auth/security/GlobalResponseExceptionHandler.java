/**
 * 
 */
package co.vistafoundation.vlearning.auth.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.vistafoundation.vlearning.common.response.Document;

/**
 * @author vk
 *
 */

@ControllerAdvice
@RestController public class GlobalResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(AccessDeniedException.class) 
	public final ResponseEntity<Document> handleAccessDeniedException(Exception ex, WebRequest request) { 
		Document document = new Document<>(); document.setData(null);
		document.setMessage("Access Denied, you don't have access to this resource");
		document.setStatusCode(HttpStatus.UNAUTHORIZED.value()); 
		return ResponseEntity.status(document.getStatusCode()).body(document); 
	}

}
