package com.sauriotech.eventquizzer.beans;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.models.EventModel;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class EventBean extends DummyBean {
	public EventModel getEvent(Long eventId, String userId) {
		LoadDummyData();

		if (userId != null) {
			String access = validateAccess(null, eventId, userId);

			if ("OK".equals(access))
				return eventDb.get(eventId);

			return new EventModel("ERR", access);
		} else {
			return eventDb.get(eventId);
		}
	}
	
	public Map<String, Object> registerAttendance(Long eventId, String userId, String date) {
		
		Map<String, Object> data = new HashMap<>();		
		
		if(existsAttendance(eventId, userId, date)) {
			data.put("attendance", "event_msg_attendance_duplicate");
		} else {
			setAttendance(eventId, userId, date);
			data.put("attendance", "event_msg_attendance_ok");
		}
		
		return data;
	}
}
