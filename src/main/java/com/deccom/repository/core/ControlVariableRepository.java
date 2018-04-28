package com.deccom.repository.core;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.deccom.domain.core.ControlVariable;

@Repository
public interface ControlVariableRepository extends MongoRepository<ControlVariable, String> {

	@Query(value = "{}", fields = "{'controlVarEntries': { '$slice': ?0 } }")
	Page<ControlVariable> findAllLimitedNumberOfEntriesQuery(Pageable pageable, Integer numberOfEntries);

	@Query(value = "{}", fields = "{ $and: [ { 'status': 'RUNNING' }, { controlVarEntries: { $elemMatch: { creationMoment: { $gte:  ?0 } } } }, { controlVarEntries: { $elemMatch: { creationMoment: { $lte:  ?1 } } } } ] }")
	Page<ControlVariable> findRunningControlVariablelsBetweenDates(Pageable pageable, Date startingDate,
			Date endingDate);

}
