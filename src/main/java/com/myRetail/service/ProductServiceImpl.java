package com.myRetail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.myRetail.client.ProductRestClient;
import com.myRetail.exception.BaseException;
import com.myRetail.model.Price;
import com.myRetail.model.Product;
import com.myRetail.repository.PriceMongoRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Value("${redsky.target.com}")
	private String url;

	@Autowired
	ProductRestClient productRestClient;
	@Autowired
	PriceMongoRepository priceMongoRepository;


	@Override
	public Product getProductInfo(String productId) throws BaseException {

		Product product = new Product();
		product.setName(productRestClient.getProductTitle(url, productId));
		product.setId(productId);

		Price price =  priceMongoRepository.findOne(productId);
		
		if(price == null) {
			throw new BaseException(HttpStatus.NOT_FOUND, "http.resource.not.found");
		}
		product.setPrice(price);     

		return product;
	}

	@Override
	public void updateProductInfo(Product product) throws BaseException {
		
		Price price = product.getPrice();
		price.setId(product.getId());
		
		if(priceMongoRepository.exists(product.getId())) {
			priceMongoRepository.save(price);
		} else {
			throw new BaseException(HttpStatus.NOT_FOUND, "http.resource.not.found");
		}
	}

}
