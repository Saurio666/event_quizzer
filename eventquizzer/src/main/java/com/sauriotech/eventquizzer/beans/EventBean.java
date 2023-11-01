package com.sauriotech.eventquizzer.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.event.EventConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.dao.EventDao;
import com.sauriotech.eventquizzer.entity.Event;
import com.sauriotech.eventquizzer.models.EventAuthorizedUserModel;
import com.sauriotech.eventquizzer.models.EventModel;
import com.sauriotech.eventquizzer.models.ModelConstants;
import com.sauriotech.eventquizzer.utils.DummyLogger;
import com.sauriotech.eventquizzer.utils.Language;
import com.sauriotech.eventquizzer.utils.TimezoneHelper;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class EventBean extends DummyBean {
	@Autowired
	private EventDao eventDao;
	@Autowired
	private EventAuthorizedUserBean authorizedUserBean;
	@Autowired
	private AttendanceBean attendanceBean;

	public EventModel getActiveEvent(Long eventId) {
		EventModel model = ThreadSafeBean.getInstance().getEventModel(eventId);
		if (model == null) {
			DummyLogger.sysout("EventBean", "getActiveEvent", "Search event " + eventId + " from database");
			Optional<Event> evt = eventDao.findById(eventId);
			if (evt.isPresent()) {
				DummyLogger.sysout("EventBean", "getActiveEvent", "Finded event " + eventId + " from database");
				
				model = transformEntity(evt.get());
				
				ThreadSafeBean.getInstance().addEventModel(eventId, model);
				
				return model;
			}
			return new EventModel(ModelConstants.ERROR, Language.getInstance().getMessage(null, BeansConstants.Event.NOT_FOUND));
		}

		DummyLogger.sysout("EventBean", "getActiveEvent", "Get event " + eventId + " from cache");
		return model;
	}

	private EventModel transformEntity(Event event) {

		EventModel model = new EventModel(ModelConstants.OK, "");
		BeanUtils.copyProperties(event, model);
		model.setRequiresAuthorization(event.getRequiresAuthorization() == 1);
		return model;

	}

	/*
	 * public EventModel getEvent(Long eventId, String userId) { LoadDummyData();
	 * 
	 * if (userId != null) { String access = validateAccess(null, eventId, userId);
	 * 
	 * if ("OK".equals(access)) return eventDb.get(eventId);
	 * 
	 * return new EventModel("ERR", access); } else { return eventDb.get(eventId); }
	 * }
	 */

	public Map<String, Object> registerAttendance(Long eventId, String userId) {		
		EventModel m = ThreadSafeBean.getInstance().getEventModel(eventId);
		if (m == null) {
			m = getActiveEvent(eventId);
		}

		Map<String, Object> data = new HashMap<>();		
		if (m == null) {
			data.put("attendance", Language.getInstance().getMessage(null, BeansConstants.Event.NOT_FOUND));
		} else {
			String date = TimezoneHelper.getStringDateFromTimezone(m.getEventTimezone());
			data.put("attendance_date", date);
			if (m.isRequiresAuthorization()) {
				DummyLogger.sysout("EventBean", "registerAttendance", "Event requires authorization");
				// Search authorized users map
				List<EventAuthorizedUserModel> authorizedUsers = authorizedUserBean.getActiveAuthorizationByEvent(eventId);

				DummyLogger.sysout("EventBean", "registerAttendance", "authorizedUsers " + authorizedUsers);
				
				Optional<EventAuthorizedUserModel> authorized = authorizedUsers.stream().filter(a -> a.getUserId().equals(userId)).findFirst();
							
				if (!authorized.isPresent()) {
					data.put("attendance", Language.getInstance().getMessage(null, BeansConstants.Event.NOT_AUTHORIZED));
				} else {
					DummyLogger.sysout("EventBean", "registerAttendance", "User " + userId + " authorized");
					
					DummyLogger.sysout("EventBean", "registerAttendance", "Date from timezone " + TimezoneHelper.getDateFromTimezone(m.getEventTimezone()));
					//Search for registration
					
					attendanceBean.registerAttendance(eventId, authorized.get(), date, data);					
				}

			} else {
				DummyLogger.sysout("EventBean", "registerAttendance", "Event NOT requires authorization");
				EventAuthorizedUserModel auth = new EventAuthorizedUserModel(null, null);
				auth.setUserId(userId);
				attendanceBean.registerAttendance(eventId, auth, date, data);				
			}
		}
		return data;
	}
}
