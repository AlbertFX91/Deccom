package com.deccom.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.deccom.domain.Event;

/**
 * Spring Data MongoDB repository for the Event entity.
 */
@Repository
public interface EventRepository extends MongoRepository<Event, String> {

}
