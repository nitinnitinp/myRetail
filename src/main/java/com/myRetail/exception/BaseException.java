package com.myRetail.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception{

	private HttpStatus status;
	private String errorMessage;

	public BaseException(HttpStatus statusCode, String errorMessage) {
		if(statusCode != null) {
			
			this.status = statusCode;
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}


}
