package com.myRetail.service;

import com.myRetail.exception.BaseException;
import com.myRetail.model.Product;

public interface ProductService {
	
	public Product getProductInfo(String productId) throws BaseException;
	
	public void updateProductInfo(Product product) throws BaseException;
		
	

}
