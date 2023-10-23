package com.sauriotech.eventquizzer.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.dao.EventDao;
import com.sauriotech.eventquizzer.entity.Event;
import com.sauriotech.eventquizzer.models.EventModel;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class EventBean extends DummyBean {
	
	public EventModel getActiveEvent(Long eventId, EventDao eventDao) {
		Optional<Event> evt = eventDao.findById(eventId);
		EventModel model = new EventModel();
		if(evt.isPresent())
		{
			Event e = evt.get();			
			BeanUtils.copyProperties(e, model);			
			model.setStatus("OK");			
		} else {
			model.setStatus("ERR");
			model.setMessage("event_not_found");
		}
		return model;
	}
	
	/*public EventModel getEvent(Long eventId, String userId) {
		LoadDummyData();

		if (userId != null) {
			String access = validateAccess(null, eventId, userId);

			if ("OK".equals(access))
				return eventDb.get(eventId);

			return new EventModel("ERR", access);
		} else {
			return eventDb.get(eventId);
		}
	}*/
	
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
