package com.deccom.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.deccom.domain.RESTControlVar;

/**
 * Spring Data MongoDB repository for the RESTControlVar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RESTControlVarRepository extends
		MongoRepository<RESTControlVar, String> {

}
