package com.deccom.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.deccom.domain.Event;

/**
 * Spring Data MongoDB repository for the Event entity.
 */
@Repository
public interface EventRepository extends MongoRepository<Event, String> {

	@Query("{ $and: [ { 'startingDate': { $gte: ?0 } }, { 'startingDate': { $lte: ?1 } } ] }")
	Page<Event> findEventsBetweenDates(Pageable pageable, Date startingDate, Date endingDate);

}
