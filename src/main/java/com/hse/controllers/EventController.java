package com.hse.controllers;

import com.hse.models.Event;
import com.hse.models.EventRegistrationData;
import com.hse.models.User;
import com.hse.services.EventService;
import com.hse.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @PostMapping(consumes = {"application/json"})
    @ApiOperation(value = "", nickname = "Create new event.", tags = {"Events"})
    public ResponseEntity<Void> createEvent(@RequestBody EventRegistrationData eventRegistrationData) {
        eventService.createEvent(eventRegistrationData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{eventId}", consumes = {"application/json"})
    @ApiOperation(value = "", nickname = "Update event.", tags = {"Events"})
    public ResponseEntity<Void> updateEvent(@PathVariable("eventId") long eventId, @RequestBody Event event) {
        eventService.updateEvent(eventId, event);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{eventId}", produces = {"application/json"})
    @ApiOperation(value = "", nickname = "Get event.", tags = {"Events"})
    public ResponseEntity<Event> getEvent(@PathVariable("eventId") long eventId) {
        Event event = eventService.getEvent(eventId);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping(value = "/{eventId}/participants", produces = {"application/json"})
    @ApiOperation(value = "", nickname = "Get event participants.", tags = {"Events"})
    public ResponseEntity<List<User>> getEventParticipants(@PathVariable("eventId") long eventId) {
        return new ResponseEntity<>(userService.getParticipants(eventId), HttpStatus.OK);
    }

    @GetMapping(value = "/{eventId}/creators", produces = {"application/json"})
    @ApiOperation(value = "", nickname = "Get creators that will be at the event", tags = {"Events"})
    public ResponseEntity<List<User>> getEventCreators(@PathVariable("eventId") long eventId){
        return new ResponseEntity<>(userService.getEventCreators(eventId), HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = {"application/json"})
    @ApiOperation(value = "", nickname = "Search events.", tags = {"Events"})
    public ResponseEntity<List<Event>> searchEvents(@RequestParam("eventName") String eventName,
                                                    @RequestParam("offset") int offset,
                                                    @RequestParam("size") int size) {
        return new ResponseEntity<>(eventService.searchEvents(eventName, offset, size), HttpStatus.OK);
    }

    @PostMapping(value = "/{eventId}/views/{userId}")
    @ApiOperation(value = "", nickname = "Search events.", tags = {"Events"})
    public ResponseEntity<Void> markThatUserViewedEvent(@PathVariable("eventId") int eventId,
                                                        @PathVariable("userId") int userId) {
        eventService.markThatUserViewedEvent(userId, eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/updatePastEvents", consumes = {"application/json"})
    @ApiOperation(value = "", nickname = "Update pastEvents", tags = {"Events"})
    public ResponseEntity<Void> updatePastEvents(@RequestBody Timestamp date){
        eventService.updatePastEvents(date);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
