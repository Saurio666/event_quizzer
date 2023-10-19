package com.sauriotech.eventquizzer.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sauriotech.eventquizzer.beans.EventBean;
import com.sauriotech.eventquizzer.models.EventModel;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "http://localhost:4200/")
public class EventService {
	@Autowired
	private EventBean eventBean;
	
	@GetMapping("/{eventId}")
	public ResponseEntity<EventModel> getQuizz(@PathVariable("eventId") Long eventId) {
		try {
			EventModel data = eventBean.getEvent(eventId, null);
			if (data == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> registerAttendance(@RequestBody Map<String, Object> data) {
		System.out.println(data);
		String eId = data.get("id").toString();
		String uId = data.get("u_id").toString();
		String date = data.get("a_date").toString();
		
		Long eventId = null;
		Map<String, Object> responseData = null;
		try {
			eventId = Long.parseLong(eId);
			responseData = eventBean.registerAttendance(eventId, uId, date);			
		}catch (Exception ex) {
			data.put("attendance", "ERR");
		}		
		
		return new ResponseEntity<>(new ApiResponse("OK", "", responseData), HttpStatus.OK);
	}
}
