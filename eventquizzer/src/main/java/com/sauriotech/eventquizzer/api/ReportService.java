package com.sauriotech.eventquizzer.api;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sauriotech.eventquizzer.beans.EventBean;
import com.sauriotech.eventquizzer.beans.QuizzBean;

@RestController
@RequestMapping("/api/report")
@CrossOrigin(origins = "*")
public class ReportService {
	@Autowired
	private EventBean eventBean;
	@Autowired
	private QuizzBean quizzBean;

	@GetMapping("/attendance_report/{eventId}")
	public ResponseEntity<Resource> getAttendance(@PathVariable("eventId") Long eventId) {

		InputStreamResource resource = new InputStreamResource(
				new ByteArrayInputStream(eventBean.getAttendanceReport(eventId)));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attendance_report.csv")
				.contentType(MediaType.parseMediaType("application/csv")).body(resource);
	}

	@GetMapping("/attempt_report/{eventId}")
	public ResponseEntity<Resource> getAttemptReport(@PathVariable("eventId") Long eventId) {
		InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(quizzBean.getReport(eventId)));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attempts_report.csv")
				.contentType(MediaType.parseMediaType("application/csv")).body(resource);
	}
}
