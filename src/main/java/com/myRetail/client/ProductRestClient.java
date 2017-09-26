package com.myRetail.client;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.myRetail.exception.BaseException;

@Repository
public class ProductRestClient  {

	RestTemplate restTemplate = new RestTemplate();;

	public <T> T getProductInfo(String url, String productId, Class<T> t) throws BaseException{
		try {
			return restTemplate.getForObject(url,t,productId);
		
		} catch(HttpStatusCodeException e) {
			if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new BaseException(e.getStatusCode(), "http.resource.not.found");
			}
			
			throw new BaseException(e.getStatusCode(), "http.internal.error");
		}

	}

	public String getProductTitle(String url, String productId) throws BaseException {

		
		Map map = getProductInfo(url, productId, Map.class);
		Map<String,Map> product = (Map<String, Map>) map.get("product");
		Map<String,Map> item = product.get("item");
		Map<String,String> desc = item.get(("product_description"));

		return desc.get("title");

	}

}
