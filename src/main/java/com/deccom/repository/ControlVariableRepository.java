package com.deccom.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.deccom.newcore.domain.ControlVariable;

@Repository
public interface ControlVariableRepository extends
		MongoRepository<ControlVariable, String> {

}
