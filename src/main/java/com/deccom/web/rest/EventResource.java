package com.deccom.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.Event;
import com.deccom.service.EventService;
import com.deccom.service.impl.util.EventServiceException;
import com.deccom.web.rest.util.HeaderUtil;
import com.deccom.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Event.
 */
@RestController
@RequestMapping("/api")
public class EventResource {

	private final Logger log = LoggerFactory.getLogger(EventResource.class);

	private static final String ENTITY_NAME = "event";

	private final EventService eventService;

	public EventResource(EventService eventService) {
		this.eventService = eventService;
	}

	/**
	 * Create a new event.
	 *
	 * 
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@PostMapping("/event/create")
	@Timed
	public ResponseEntity<Event> create(@RequestBody @Valid Event event) throws URISyntaxException {

		// event = eventService.create();

		log.debug("REST request to save Event : {}", event);

		LocalDateTime creationMoment;

		creationMoment = LocalDateTime.now();

		event.setCreationMoment(creationMoment);

		if (event.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new result cannot already have an ID"))
					.body(null);
		}

		eventService.save(event);

		return ResponseEntity.created(new URI("/api/event/create" + event.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, event.getId().toString())).body(event);

	}

	/**
	 * PUT /events : Updates an existing event.
	 *
	 * @param event
	 *            the event to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         event, or with status 400 (Bad Request) if the event is not valid, or
	 *         with status 500 (Internal Server Error) if the event couldn't be
	 *         updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/event/update")
	@Timed
	public ResponseEntity<Event> updateEvent(@Valid @RequestBody Event event) throws URISyntaxException {
		log.debug("REST request to update Event : {}", event);
		if (event.getId() == null) {
			return create(event);
		}
		Event result = eventService.save(event);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, event.getId().toString()))
				.body(result);
	}

	/**
	 * GET /events : get all the events.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of events in
	 *         body
	 */
	@GetMapping("/event/findAll")
	@Timed
	public ResponseEntity<List<Event>> getAllEvents(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of Events");
		Page<Event> page = eventService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/events");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /events : get the events between two dates.
	 *
	 * @param pageable
	 *            the pagination information
	 * @param startingDate
	 *            the starting date of the range
	 * @param endingDate
	 *            the ending date of the range
	 * @return the ResponseEntity with status 200 (OK) and the list of events in
	 *         body
	 */
	@GetMapping("/event/dates")
	@Timed
	public ResponseEntity<List<Event>> getEventsBetweenDates(@ApiParam Pageable pageable, String startingDate,
			String endingDate) {
		log.debug("REST request to get a page of events between two dates");
		Page<Event> page = eventService.findEventsBetweenDates(pageable, startingDate, endingDate);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/events");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /events/:id : get the "id" event.
	 *
	 * @param id
	 *            the id of the event to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the event, or
	 *         with status 404 (Not Found)
	 */
	@GetMapping("/event/findOne/{id}")
	@Timed
	public ResponseEntity<Event> getEvent(@PathVariable String id) {
		log.debug("REST request to get Event : {}", id);
		Event event = eventService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(event));
	}

	/**
	 * DELETE /events/:id : delete the "id" event.
	 *
	 * @param id
	 *            the id of the event to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/event/delete/{id}")
	@Timed
	public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
		log.debug("REST request to delete Event : {}", id);
		eventService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
	}

	@ExceptionHandler(EventServiceException.class)
	public ResponseEntity<String> panic(EventServiceException oops) {
		return ResponseEntity.badRequest()
				.headers(HeaderUtil.createFailureAlert(oops.getEntity(), oops.getI18nCode(), oops.getMessage()))
				.body("{ \"error\": \"" + oops.getMessage() + "\" }");
	}

}
