/**
 * 
 */
package com.sauriotech.eventquizzer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sauriotech.eventquizzer.entity.Question;

/**
 * 
 * @author SAURIOTech Solutions.
 *
 */
public interface QuestionDao extends JpaRepository<Question, Long> {
	
	public List<Question> findByRecordStatus(String recordStatus);
	
	public List<Question> findByRecordStatusAndQuizzIdOrderByIdDesc(String recordStatus, Long quizzId);
	
	public List<Question> findByRecordStatusAndQuizzIdOrderByIdAsc(String recordStatus, Long quizzId);
}
