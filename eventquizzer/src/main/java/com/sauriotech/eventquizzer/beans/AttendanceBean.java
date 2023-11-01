package com.sauriotech.eventquizzer.beans;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.dao.AttendanceDao;
import com.sauriotech.eventquizzer.entity.Attendance;
import com.sauriotech.eventquizzer.entity.EntityConstants;
import com.sauriotech.eventquizzer.models.EventAuthorizedUserModel;
import com.sauriotech.eventquizzer.utils.DummyLogger;
import com.sauriotech.eventquizzer.utils.Language;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class AttendanceBean {
	@Autowired
	private AttendanceDao attendanceDao;
	
	public void registerAttendance(Long eventId, EventAuthorizedUserModel user, String date, Map<String, Object> data) {
		DummyLogger.sysout("AttendanceBean", "registerAttendance", "Start attendance registration event:" + eventId + ", userId: " + user.getUserId() + ", date: " + date);
		Attendance a = new Attendance();
		a.setAttendanceDate(date);
		a.setEventId(eventId);
		a.setUserId(user.getUserId());
		//a.setId(0L);
		//a.setRecordStatus(EntityConstants.STATUS_ACTIVE);
		
		try {
			Attendance newA = attendanceDao.saveAndFlush(a);		
			DummyLogger.sysout("AttendanceBean", "registerAttendance", "New entity ID " + newA.getId());
			
			data.put("attendance", Language.getInstance().getMessage(null, BeansConstants.Event.ATTENDANCE_OK));
			if(user.getUserName() != null) {
				data.put("attendance_user", user.getUserName());
			}
		} catch (Exception ex) {			
			if (ex.getMessage().contains("uk_attendance")) {
				data.put("attendance", Language.getInstance().getMessage(null, BeansConstants.Event.ATTENDANCE_DUPLICATE));
			} else {
				ex.printStackTrace();
			}
		}
		//attendance_user		
	}
}
