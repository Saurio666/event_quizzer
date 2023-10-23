package com.sauriotech.eventquizzer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.entity.Event;

/**
 * 
 * @author SAURIOTech Solutions.
 *
 */
public interface EventDao extends JpaRepository<Event, Long> {
	public List<Event> findByStatus(String status);
}
