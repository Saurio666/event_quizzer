package com.sauriotech.eventquizzer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sauriotech.eventquizzer.entity.Answer;

/**
 * 
 * @author SAURIOTech Solutions.
 *
 */
public interface AnswerDao extends JpaRepository<Answer, Long> {
	public List<Answer> findByRecordStatus(String recordStatus);
	public List<Answer> findByRecordStatusAndQuestionId(String recordStatus, Long questionId);
}
