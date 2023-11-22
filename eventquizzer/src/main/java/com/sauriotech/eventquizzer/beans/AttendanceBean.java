package com.sauriotech.eventquizzer.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.dao.AttendanceDao;
import com.sauriotech.eventquizzer.entity.Attendance;
import com.sauriotech.eventquizzer.entity.EntityConstants;
import com.sauriotech.eventquizzer.models.EventAuthorizedUserModel;
import com.sauriotech.eventquizzer.utils.DummyLogger;
import com.sauriotech.eventquizzer.utils.Language;
import com.sauriotech.eventquizzer.utils.TimezoneHelper;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class AttendanceBean {
	private static String SEPARATOR = ";";
	@Autowired
	private AttendanceDao attendanceDao;

	public void registerAttendance(Long eventId, EventAuthorizedUserModel user, String date, Map<String, Object> data) {
		DummyLogger.sysout("AttendanceBean", "registerAttendance",
				"Start attendance registration event:" + eventId + ", userId: " + user.getUserId() + ", date: " + date);
		Attendance a = new Attendance();
		a.setAttendanceDate(date);
		a.setEventId(eventId);
		a.setUserId(user.getUserId());
		// a.setId(0L);
		// a.setRecordStatus(EntityConstants.STATUS_ACTIVE);

		try {
			Attendance newA = attendanceDao.saveAndFlush(a);
			DummyLogger.sysout("AttendanceBean", "registerAttendance", "New entity ID " + newA.getId());

			data.put("attendance", Language.getInstance().getMessage(null, BeansConstants.Event.ATTENDANCE_OK));
			if (user.getUserName() != null) {
				data.put("attendance_user", getUserName(user.getUserName()));
			}
		} catch (Exception ex) {
			if (ex.getMessage().contains("uk_attendance")) {
				data.put("attendance",
						Language.getInstance().getMessage(null, BeansConstants.Event.ATTENDANCE_DUPLICATE));
			} else {
				ex.printStackTrace();
			}
		}
		// attendance_user
	}

	private String getUserName(String fullName) {
		if (fullName != null) {
			return fullName.split(" ")[0];
		}

		return fullName;
	}

	public byte[] getAttendanceReport(Long eventId, Date start, Date end) {
		StringBuilder sb = new StringBuilder("user" + SEPARATOR);
		List<Attendance> allAttendance = attendanceDao.findByEventIdAndRecordStatusOrderByUserId(eventId,
				EntityConstants.STATUS_ACTIVE);

		DummyLogger.sysout("AttendanceBean", "getAttendanceReport", "Start " + start);
		DummyLogger.sysout("AttendanceBean", "getAttendanceReport", "End " + end);

		List<String> dates = new ArrayList<>();
		dates.add(TimezoneHelper.convertStringDateShort(start));
		sb.append(TimezoneHelper.convertStringDateShort(start) + SEPARATOR);
		int countHelper = 0;
		Date newDate = start;
		while (true) {

			Calendar c = Calendar.getInstance();
			c.setTime(newDate);
			c.add(Calendar.DATE, 1);
			newDate = c.getTime();
			DummyLogger.sysout("AttendanceBean", "getAttendanceReport", "newDate " + newDate);
			if (newDate.compareTo(end) <= 0) {
				dates.add(TimezoneHelper.convertStringDateShort(newDate));
				sb.append(TimezoneHelper.convertStringDateShort(newDate) + SEPARATOR);
			} else {
				break;
			}

			countHelper++;

			if (countHelper > 1000) {
				break;
			}
		}

		sb.append("\n");

		DummyLogger.sysout("AttendanceBean", "getAttendanceReport", "date.size() " + dates.size());
		DummyLogger.sysout("AttendanceBean", "getAttendanceReport", "date " + dates);
		DummyLogger.sysout("AttendanceBean", "getAttendanceReport", "countHelper " + countHelper);

		Map<String, String> allData = new HashMap<>();
		if (allAttendance != null && !allAttendance.isEmpty()) {
			for (Attendance a : allAttendance) {
				if (!allData.containsKey(a.getUserId())) {
					allData.put(a.getUserId(), "");
				}
			}

			for (String key : allData.keySet()) {
				sb.append(key + SEPARATOR);
				for (String date : dates) {
					DummyLogger.sysout("AttendanceBean", "getAttendanceReport", "get info for date " + date);
					Optional<Attendance> findByDate = allAttendance.stream()
							.filter(at -> at.getUserId().equals(key) && date.equals(at.getAttendanceDate()))
							.findFirst();
					if (findByDate.isPresent()) {
						sb.append("X" + SEPARATOR);
					} else {
						sb.append(SEPARATOR);
					}
				}
				sb.append("\n");
			}
		}
		
		DummyLogger.sysout("AttendanceBean", "getAttendanceReport", "sb " + sb.toString());

		return sb.toString().getBytes();
	}
}
