/**
 * Test
 */
package co.vistafoundation.vlearning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Abdul Elahi
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
        super(message);
    }

}
