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
import com.sauriotech.eventquizzer.dao.EventDao;
import com.sauriotech.eventquizzer.models.EventModel;
import com.sauriotech.eventquizzer.utils.DummyLogger;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "*")
public class EventService {
	@Autowired
	private EventBean eventBean;
	
	@GetMapping("/{eventId}")
	public ResponseEntity<EventModel> getEvent(@PathVariable("eventId") Long eventId) {
		try {
			EventModel data = eventBean.getActiveEvent(eventId);
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
		
		DummyLogger.sysout("EventService", "registerAttendance", data);
		
		Long eventId = null;
		Map<String, Object> responseData = null;
		try {
			eventId = Long.parseLong(eId);
			responseData = eventBean.registerAttendance(eventId, uId);			
		}catch (Exception ex) {
			data.put("attendance", "ERR");
		}		
		
		return new ResponseEntity<>(new ApiResponse("OK", "", responseData), HttpStatus.OK);
	}
}
