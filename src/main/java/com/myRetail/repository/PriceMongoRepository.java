package com.myRetail.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.myRetail.model.Price;

@Repository
public interface PriceMongoRepository extends MongoRepository<Price,String> {

	
}
