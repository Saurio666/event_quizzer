package com.sauriotech.eventquizzer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sauriotech.eventquizzer.entity.Attendance;

/**
 * 
 * @author SAURIOTech Solutions.
 *
 */
public interface AttendanceDao extends JpaRepository<Attendance, Long> {
	public List<Attendance> findByRecordStatus(String recordStatus);
}
