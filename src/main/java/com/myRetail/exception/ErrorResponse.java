package com.myRetail.exception;

public class ErrorResponse {
	
	private int status;
	private String errorMessage;
	
	public ErrorResponse(int httpStatus, String message) {
		this.status = httpStatus;
		this.errorMessage = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
