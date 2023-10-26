package com.sauriotech.eventquizzer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sauriotech.eventquizzer.entity.Quizz;

/**
 * 
 * @author SAURIOTech Solutions.
 *
 */
public interface QuizzDao extends JpaRepository<Quizz, Long> {
	public List<Quizz> findByRecordStatus(String recordStatus);
	
	public List<Quizz> findByRecordStatusAndEventId(String recordStatus, Long eventId);
}
