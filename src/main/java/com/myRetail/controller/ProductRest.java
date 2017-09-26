package com.myRetail.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myRetail.exception.BaseException;
import com.myRetail.model.Product;
import com.myRetail.service.ProductService;
/**
 * 
 * @author Nitin Patidar
 *
 */

@RestController
@Validated
@RequestMapping("/products")
public class ProductRest {
	
	
	
	@Autowired
	private ProductService productService;
	
	/**
	 * @param productId
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET,produces = "application/json")
	public @ResponseBody Product getProductInfo(@PathVariable ("id") String productId) throws BaseException {
		return productService.getProductInfo(productId);
	}
	
	/**
	 * 
	 * @param product
	 * @param productId
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = "application/json")
	public void updateProductInfo(@Valid @RequestBody Product product, @PathVariable ("id") String productId) throws BaseException {
		if(product == null) {
			throw new BaseException(HttpStatus.BAD_REQUEST, "http.bad.request.error");
		}
		if(!product.getId().equals(productId)) {
			
			throw new BaseException(HttpStatus.BAD_REQUEST, "http.request.product.id.mismatch");
		}
		
		productService.updateProductInfo(product);	
	}

}
