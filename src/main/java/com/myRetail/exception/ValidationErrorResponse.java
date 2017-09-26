package com.myRetail.exception;

import java.util.HashMap;

public class ValidationErrorResponse {

	private HashMap<String, String> fieldErrors ;

	public HashMap<String, String> getFieldErrors() {
		
		if(fieldErrors == null) {
			fieldErrors = new HashMap<String, String>();
		}
		return fieldErrors;
	}

	public void setFieldErrors(HashMap<String, String> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}


}
