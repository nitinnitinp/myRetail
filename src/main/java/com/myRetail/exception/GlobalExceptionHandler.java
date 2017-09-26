package com.myRetail.exception;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler { 
	@Autowired
	private MessageSource msgSource;

	@ExceptionHandler(BaseException.class)
	@ResponseBody
	ResponseEntity<Object> handleBaseExceptionException(BaseException e) {

		return new ResponseEntity<Object>(new ErrorResponse(e.getStatus().value(),getErrorMessage(e.getErrorMessage())),e.getStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	ResponseEntity<Object> handleBaseExceptionException(MethodArgumentNotValidException e) {
		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		ValidationErrorResponse  validationResponse = new ValidationErrorResponse();
		for(FieldError error :errors ) {
			if(error != null) {
				validationResponse.getFieldErrors().put(error.getField(), getErrorMessage(error.getDefaultMessage()));
			}
		}
		return new ResponseEntity<Object>(validationResponse,HttpStatus.BAD_REQUEST);
	} 

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	ResponseEntity<Object> handleBaseExceptionException(HttpMessageNotReadableException e) {
		return new ResponseEntity<Object>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),getErrorMessage("http.bad.request.error")),HttpStatus.BAD_REQUEST);
	}


	private String getErrorMessage(String  key) {
		try {
			Locale currentLocale = LocaleContextHolder.getLocale();
			String message = msgSource.getMessage(key, null, currentLocale);
			return message;
		}
		catch(NoSuchMessageException e) {
			return key;
		}

	}
}
