package com.deccom.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.deccom.domain.Event;

/**
 * Service Interface for managing Event.
 */
public interface EventService {
	
	/**
     * Create an event.
     *
     * @return the created event
     */
    Event create();
	
	/**
     * Save a event.
     *
     * @param event the entity to save
     * @return the persisted entity
     */
    Event save(Event event);

    /**
     *  Get all the events.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Event> findAll(Pageable pageable);

    /**
     *  Get the "id" event.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Event findOne(String id);

    /**
     *  Delete the "id" event.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

}
