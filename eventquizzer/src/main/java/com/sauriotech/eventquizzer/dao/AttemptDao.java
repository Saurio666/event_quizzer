package com.sauriotech.eventquizzer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sauriotech.eventquizzer.entity.Attempt;

/**
 * 
 * @author SAURIOTech Solutions.
 *
 */
public interface AttemptDao extends JpaRepository<Attempt, Long> {
	public List<Attempt> findByUserIdAndQuizIdAndRecordStatus(String userId, Long quizId, String status);
	public List<Attempt> findByUserIdAndQuizIdAndRecordStatusAndAttemptEndIsNull(String userId, Long quizId, String status);
}
