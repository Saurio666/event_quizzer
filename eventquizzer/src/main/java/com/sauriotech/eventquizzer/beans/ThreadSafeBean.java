package com.sauriotech.eventquizzer.beans;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sauriotech.eventquizzer.models.EventAuthorizedUserModel;
import com.sauriotech.eventquizzer.models.EventModel;
import com.sauriotech.eventquizzer.models.QuizzModel;
import com.sauriotech.eventquizzer.utils.DummyLogger;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class ThreadSafeBean {
	private static boolean ENABLED_CACHE = false;
	private static volatile ThreadSafeBean instance = null;
	private Map<Long, QuizzModel> quizzModelMap;
	private Map<Long, EventModel> eventModelMap;
	private Map<Long, List<EventAuthorizedUserModel>> authorizedUsersPerEventMap;

	private ThreadSafeBean() {
		System.out.println("[ThreadSafeBean] [getQuizz] New instance***");
		quizzModelMap = new ConcurrentHashMap<>();
		eventModelMap = new ConcurrentHashMap<>();
		authorizedUsersPerEventMap = new ConcurrentHashMap<>();
	}

	public static ThreadSafeBean getInstance() {
		if (instance == null) {
			synchronized (ThreadSafeBean.class) {
				if (instance == null) {
					instance = new ThreadSafeBean();
				}
			}
		}
		return instance;
	}

	public QuizzModel getQuizzModel(Long quizzId) {
		DummyLogger.sysout("ThreadSafeBean", "getQuizzModel", "quizzId " + quizzId);
		if (!quizzModelMap.containsKey(quizzId)) {
			DummyLogger.sysout("ThreadSafeBean", "getQuizzModel", "Not exists");
			return null;
		}

		DummyLogger.sysout("ThreadSafeBean", "getQuizzModel", "Exists");
		return quizzModelMap.get(quizzId);
	}

	public void addQuizzModel(Long quizzId, QuizzModel quizzModel) {
		DummyLogger.sysout("ThreadSafeBean", "addQuizzModel", "Add quizz model " + quizzId);
		if (ENABLED_CACHE)
			quizzModelMap.put(quizzId, quizzModel);
	}

	public EventModel getEventModel(Long eventId) {
		DummyLogger.sysout("ThreadSafeBean", "getEventModel", "eventId " + eventId);

		if (!eventModelMap.containsKey(eventId)) {
			DummyLogger.sysout("ThreadSafeBean", "getEventModel", "Not exists");
			return null;
		}

		DummyLogger.sysout("ThreadSafeBean", "getEventModel", "Exists");
		return eventModelMap.get(eventId);
	}

	public void addEventModel(Long eventId, EventModel eventModel) {
		DummyLogger.sysout("ThreadSafeBean", "addEventModel", "Add event model " + eventId);
		if (ENABLED_CACHE)
			eventModelMap.put(eventId, eventModel);
	}

	public List<EventAuthorizedUserModel> getAuthorizedUsers(Long eventId) {
		DummyLogger.sysout("ThreadSafeBean", "getAuthorizedUsers", "eventId " + eventId);
		if (!authorizedUsersPerEventMap.containsKey(eventId)) {
			DummyLogger.sysout("ThreadSafeBean", "getAuthorizedUsers", "Not exists");
			return null;
		}

		DummyLogger.sysout("ThreadSafeBean", "getAuthorizedUsers", "Exists");
		return authorizedUsersPerEventMap.get(eventId);
	}

	public void addAuthorizedUsers(Long eventId, List<EventAuthorizedUserModel> authorizedUsers) {
		DummyLogger.sysout("ThreadSafeBean", "addAuthorizedUsers", "Add list " + eventId);
		if (ENABLED_CACHE)
			authorizedUsersPerEventMap.put(eventId, authorizedUsers);
	}
}
