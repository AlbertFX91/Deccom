package com.deccom.newcore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deccom.newcore.domain.ControlVariable;

public interface ControlVariableRepository extends
		MongoRepository<ControlVariable, String> {

}
