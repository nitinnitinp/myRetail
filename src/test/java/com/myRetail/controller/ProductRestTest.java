package com.myRetail.controller;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpStatusCodeException;

import com.myRetail.app.Application;
import com.myRetail.client.ProductRestClient;
import com.myRetail.exception.BaseException;
import com.myRetail.model.Price;
import com.myRetail.model.Product;
import com.myRetail.repository.PriceMongoRepository;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductRestTest {

	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;

	@Autowired
	ProductRestClient productRestClient;

	@Autowired
	PriceMongoRepository priceMongoRepository;

	@Value("${redsky.target.com}")
	private String url;

	@Test
	public void getProductInfoTest() {

		String productId = "13860428";

		try {
			// get the product name from "redsky.target.com";
			String name = productRestClient.getProductTitle(url, productId);
			//get price details from mongodb
			Price price = priceMongoRepository.findOne(productId);

			//get productinfo from myRetail
			ResponseEntity<Product> response = restTemplate.getForEntity("http://localhost:" + port  + "/myRetail/products/" + productId, Product.class);
			Product returnedInfo = response.getBody();

			assertEquals(productId, returnedInfo.getId());
			assertEquals(name, returnedInfo.getName());
			assertEquals(price.getCurrency_code(), returnedInfo.getPrice().getCurrency_code());
			assertEquals(price.getValue(), returnedInfo.getPrice().getValue(),0.0);

		} catch (BaseException e) {
			Assert.fail();
		}
	}

	@Test
	public void productInfoNotFoundTest() {

		String productId = "138604233";
		ResponseEntity<Product> response = restTemplate.getForEntity("http://localhost:" + port  + "/myRetail/products/" + productId, Product.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

	}

	@Test
	public void badRequestTest() {
		String productId = "13860428";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Product product = new Product();
		HttpEntity<Product> requestEntity = new HttpEntity<Product>(product, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port  + "/myRetail/products/" + productId,HttpMethod.PUT, requestEntity,String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());


	}


	@Test
	public void updateProductInfoTest() {

		String productId = "13860428";

		//get productinfo from myRetail
		ResponseEntity<Product> response = restTemplate.getForEntity("http://localhost:" + port  + "/myRetail/products/" + productId, Product.class);
		Product requestProduct = response.getBody();

		//update the product 
		requestProduct.getPrice().setValue(50.00);

		//update productinfo from myRetail
		restTemplate.put("http://localhost:" + port  + "/myRetail/products/" + productId, requestProduct);

		//get updated productinfo from myRetail
		response = restTemplate.getForEntity("http://localhost:" + port  + "/myRetail/products/" + productId, Product.class);
		Product updatedProduct = response.getBody();


		assertEquals(requestProduct.getId(), updatedProduct.getId());
		assertEquals(requestProduct.getName(), updatedProduct.getName());
		assertEquals(requestProduct.getPrice().getCurrency_code(), updatedProduct.getPrice().getCurrency_code());
		assertEquals(requestProduct.getPrice().getValue(), updatedProduct.getPrice().getValue(),0.0);



	}

}
