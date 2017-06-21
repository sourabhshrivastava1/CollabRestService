package com.niit.collaboration.rest.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Collaboration.DAO.EventDAO;
import com.niit.Collaboration.DAO.UserDAO;
import com.niit.Collaboration.model.Blog;
import com.niit.Collaboration.model.Event;
import com.niit.Collaboration.model.User;

@RestController
public class EventController {
	@Autowired
	private Event event;
	
	@Autowired
	private EventDAO eventDAO;
	
	@Autowired
	private User user;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("events")
	public ResponseEntity<List<Event>> getAllEvent() {

		List<Event> eventList = eventDAO.listEvent();

		return new ResponseEntity<List<Event>>(HttpStatus.OK);
	}
	
	@RequestMapping("event/create")
	public ResponseEntity<Event> createEvent(@RequestBody Event event) {
		//System.out.println("Controller started"+event.getName());
		user = (User) session.getAttribute("user");
		event.setUser_id(user.getId());
	
	
	        
		
			if (eventDAO.saveOrupdate(event) == true) {
				event.setErrorCode("200");
				event.setErrorMessage("Event Created");
			} else {
				event.setErrorCode("404");
				event.setErrorMessage("Event Creation Failed");
			}
			return new ResponseEntity<Event>(event, HttpStatus.OK);
		
		

	}
	@GetMapping("event/{event_id}")
	public ResponseEntity<Event> getBlogById(@PathVariable("id") int id) {
		event = eventDAO.getEventById("id");

		if (event == null) {
			event = new Event();
			event.setErrorCode("404");
			event.setErrorMessage("Event doesn't exist with this id " + id);
		} else {
			event.setErrorCode("200");
			event.setErrorMessage("Success");
		}
		return new ResponseEntity<Event>(event, HttpStatus.OK);

	}
	
	

}