package com.deccom.repository.core;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.deccom.domain.core.ControlVariable;

@Repository
public interface ControlVariableRepository extends
		MongoRepository<ControlVariable, String> {

}
