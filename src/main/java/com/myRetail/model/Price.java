package com.myRetail.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "products")
public class Price {

	@DecimalMin(value ="0.0",message="price.value") 
	private double value;
	@NotNull(message="price.currency.code")
	private String currency_code;
	@Id
	@JsonIgnore
	private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
}
