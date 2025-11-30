package tn.vhs.exceptions;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class AppExceptionHandler {

	@Autowired
	MessageSource messageSource;

	@ExceptionHandler(value = NoResourceException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNoResourceException(NoResourceException ex) {
		return messageSource.getMessage(ex.getMessageKey(), null, null);
	}
	

	@ExceptionHandler(value = VhsUnavailableException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
    public String handleNoSuchCustomerExistsException(VhsUnavailableException ex) {
		return messageSource.getMessage("vhs.unavailable", null, null);
    }

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		return Optional.ofNullable(ex.getBindingResult().getFieldError()).map(FieldError::getDefaultMessage)
				.orElse(messageSource.getMessage("validation.error", null, null));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		return messageSource.getMessage("type.error",
				new Object[] { ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName() }, null);
	}
}
