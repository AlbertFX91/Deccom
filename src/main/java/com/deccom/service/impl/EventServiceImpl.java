package com.deccom.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.deccom.domain.Event;
import com.deccom.repository.EventRepository;
import com.deccom.service.EventService;

/**
 * Service Implementation for managing Event.
 */
@Service
public class EventServiceImpl implements EventService {

	private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
	private static final String i18nCodeRoot = "operations.event";

	private final EventRepository eventRepository;

	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	/**
	 * Create an event.
	 *
	 * @return the created event
	 */
	public Event create() {

		Event result;
		LocalDateTime creationMoment;

		result = new Event();
		creationMoment = LocalDateTime.now();

		result.setCreationMoment(creationMoment);

		return result;

	}

	/**
	 * Save an event.
	 *
	 * @param event
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public Event save(Event event) {

		log.debug("Request to save Event : {}", event);

		if (event.getId() == null) {

			LocalDateTime creationMoment;

			creationMoment = LocalDateTime.now();

			event.setCreationMoment(creationMoment);

		}

		if (event.getEndingDate() != null) {

			Assert.isTrue(event.getStartingDate().isBefore(event.getEndingDate())
					|| event.getStartingDate().isEqual(event.getEndingDate()), i18nCodeRoot + ".wrongDates");

		}

		return eventRepository.save(event);

	}

	/**
	 * Get all the events.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	public Page<Event> findAll(Pageable pageable) {
		log.debug("Request to get all Events");
		return eventRepository.findAll(pageable);
	}

	/**
	 * Get one event by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	public Event findOne(String id) {
		log.debug("Request to get Event : {}", id);
		return eventRepository.findOne(id);
	}

	/**
	 * Delete the event by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(String id) {
		log.debug("Request to delete Event : {}", id);
		eventRepository.delete(id);
	}

}
