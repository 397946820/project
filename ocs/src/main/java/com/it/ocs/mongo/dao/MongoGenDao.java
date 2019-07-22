package com.it.ocs.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class MongoGenDao<T> {
	@Autowired
	protected MongoTemplate mongoTemplate;
	public void add(T obj) {
		this.mongoTemplate.save(obj);
	}
	
}
