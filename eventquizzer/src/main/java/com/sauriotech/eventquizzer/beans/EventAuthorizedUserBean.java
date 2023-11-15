package com.sauriotech.eventquizzer.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.dao.EventAuthorizedUserDao;
import com.sauriotech.eventquizzer.entity.EntityConstants;
import com.sauriotech.eventquizzer.entity.EventAuthorizedUser;
import com.sauriotech.eventquizzer.models.EventAuthorizedUserModel;
import com.sauriotech.eventquizzer.models.ModelConstants;
import com.sauriotech.eventquizzer.utils.DummyLogger;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class EventAuthorizedUserBean {
	@Autowired
	private EventAuthorizedUserDao eventAuthorizedUserDao;
	
	public List<EventAuthorizedUserModel> getActiveAuthorizationByEvent(Long eventId) {
		DummyLogger.sysout("EventAuthorizedUserBean", "getActiveAuthorizationByEvent", "Try get authorizations for event " + eventId);
		List<EventAuthorizedUserModel> authorizedUsers = ThreadSafeBean.getInstance().getAuthorizedUsers(eventId);
		if (authorizedUsers == null) {
			DummyLogger.sysout("EventAuthorizedUserBean", "getActiveAuthorizationByEvent", "Try get authorizations for event " + eventId + " from database");
			List<EventAuthorizedUser> users = eventAuthorizedUserDao.findByRecordStatusAndEventId(EntityConstants.STATUS_ACTIVE, eventId);
			if (users != null) {
				DummyLogger.sysout("EventAuthorizedUserBean", "getActiveAuthorizationByEvent", "Get authorizations for event " + eventId + " from database --> " + users.size());
				authorizedUsers = transformToList(eventId, users);
				
				if(authorizedUsers != null) {
					DummyLogger.sysout("EventAuthorizedUserBean", "getActiveAuthorizationByEvent", "Get authorizations for event " + eventId + " from database" + authorizedUsers);
					ThreadSafeBean.getInstance().addAuthorizedUsers(eventId, authorizedUsers);					
					return authorizedUsers;
				}								
			}
			DummyLogger.sysout("EventAuthorizedUserBean", "getActiveAuthorizationByEvent", "Get authorizations for event " + eventId + " no results");
			return new ArrayList<>();
		}
		DummyLogger.sysout("EventAuthorizedUserBean", "getActiveAuthorizationByEvent", "Get authorizations for event " + eventId + " from cache");
		return authorizedUsers;
	}
	
	private List<EventAuthorizedUserModel> transformToList(Long eventId, List<EventAuthorizedUser> users) {
		List<EventAuthorizedUserModel> authorizedUsers = null;
		if(!users.isEmpty()) {
			authorizedUsers = new ArrayList<>();
			for(EventAuthorizedUser a : users) {
				EventAuthorizedUserModel m = new EventAuthorizedUserModel(ModelConstants.OK, null);
				BeanUtils.copyProperties(a, m);
				authorizedUsers.add(m);
			}
		}
		
		return authorizedUsers;
	}
	
	public EventAuthorizedUserModel getAuthorization(Long eventId, String userId) {
		DummyLogger.sysout("EventAuthorizedUserBean", "getActiveAuthorizationByEvent", "Try get authorizations for event " + eventId + " and user " + userId);
		List<EventAuthorizedUser> authorizations = eventAuthorizedUserDao.findByUserIdAndEventIdAndRecordStatus(userId, eventId, EntityConstants.STATUS_ACTIVE);
		
		if(!authorizations.isEmpty()) {
			EventAuthorizedUserModel m = new EventAuthorizedUserModel(ModelConstants.OK, null);
			BeanUtils.copyProperties(authorizations.get(0), m);
			
			return m;
		}
		
		return new EventAuthorizedUserModel(ModelConstants.ERROR, null);
	}
}
