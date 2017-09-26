package com.myRetail.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

	
	@NotNull(message="product.id")
	private String id;
	@NotNull(message="product.name")
	private String name;
	@JsonProperty("current_price")
	@NotNull(message="product.price")
	@Valid
	private Price price;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}


}
