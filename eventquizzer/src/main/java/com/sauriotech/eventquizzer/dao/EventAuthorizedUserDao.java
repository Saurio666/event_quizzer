package com.sauriotech.eventquizzer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sauriotech.eventquizzer.entity.EventAuthorizedUser;

/**
 * 
 * @author SAURIOTech Solutions.
 *
 */
public interface EventAuthorizedUserDao extends JpaRepository<EventAuthorizedUser, Long> {
	public List<EventAuthorizedUser> findByRecordStatusAndEventId(String recordStatus, Long eventId);
	public List<EventAuthorizedUser> findByUserIdAndEventIdAndRecordStatus(String userId, Long eventId, String recordStatus);
}
