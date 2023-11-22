package com.sauriotech.eventquizzer.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sauriotech.eventquizzer.dao.AttemptDao;
import com.sauriotech.eventquizzer.entity.Attempt;
import com.sauriotech.eventquizzer.entity.EntityConstants;
import com.sauriotech.eventquizzer.models.AttemptModel;
import com.sauriotech.eventquizzer.models.QualifiedAnswerModel;
import com.sauriotech.eventquizzer.utils.DummyLogger;
import com.sauriotech.eventquizzer.utils.TimezoneHelper;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class AttemptBean {
	public static final String START = "START";
	public static final String FINISHED_BY_USER = "FINISHED_BY_USER";
	public static final String FINISHED_BY_TIMEOUT = "FINISHED_BY_TIMEOUT";
	
	private static String SEPARATOR = ";";

	@Autowired
	private AttemptDao attemptDao;

	public AttemptModel startAttempt(String userId, Long quizzId, String timeZone, int maxAttempts) {
		List<Attempt> attempts = attemptDao.findByUserIdAndQuizIdAndRecordStatusAndAttemptEndIsNull(userId, quizzId,
				EntityConstants.STATUS_ACTIVE);
		if (attempts.size() == 0) {
			// Verify max attempts
			List<Attempt> prevAttempts = attemptDao.findByUserIdAndQuizIdAndRecordStatusAndAttemptEndIsNotNull(userId,
					quizzId, EntityConstants.STATUS_ACTIVE);
			if (prevAttempts.size() < maxAttempts) {
				Date currentDate = TimezoneHelper.getDateFromTimezone(timeZone);
				Attempt a = new Attempt();
				a.setUserId(userId);
				a.setQuizId(quizzId);
				a.setAttemptStart(currentDate);
				a.setAttemptStatus(START);

				Attempt newA = attemptDao.save(a);

				return transform(newA);
			}
			return null;

		} else {
			return transform(attempts.get(0));
		}
	}

	public AttemptModel submitAttemptInfo(Long attemptId, String data, String qualifiedData, Double score,
			String timeZone, String action) {
		Optional<Attempt> attempt = attemptDao.findById(attemptId);
		if (attempt.isPresent()) {
			Date currentDate = TimezoneHelper.getDateFromTimezone(timeZone);

			Attempt a = attempt.get();

			a.setSubmitedData(data);
			a.setQualifiedData(qualifiedData);
			if ("0".equals(action)) {
				a.setAttemptStatus(FINISHED_BY_USER);
			} else {
				a.setAttemptStatus(FINISHED_BY_TIMEOUT);
			}
			a.setAttemptEnd(currentDate);
			a.setAttemptScore(score);

			Attempt am = attemptDao.saveAndFlush(a);

			return transform(am);
		}

		return new AttemptModel();
	}

	public AttemptModel submitAttemptInfo(String userId, Long quizzId, String data, String qualifiedData, Double score,
			String timeZone, String action) {
		List<Attempt> attempts = attemptDao.findByUserIdAndQuizIdAndRecordStatus(userId, quizzId,
				EntityConstants.STATUS_ACTIVE);
		if (attempts.size() > 0) {
			Date currentDate = TimezoneHelper.getDateFromTimezone(timeZone);

			Attempt a = attempts.get(0);

			a.setSubmitedData(data);
			a.setQualifiedData(qualifiedData);
			if ("0".equals(action)) {
				a.setAttemptStatus(FINISHED_BY_USER);
			} else {
				a.setAttemptStatus(FINISHED_BY_TIMEOUT);
			}
			a.setAttemptEnd(currentDate);
			a.setAttemptScore(score);

			Attempt am = attemptDao.saveAndFlush(a);

			return transform(am);
		}

		return new AttemptModel();
	}

	private AttemptModel transform(Attempt a) {
		AttemptModel m = new AttemptModel();
		BeanUtils.copyProperties(a, m);

		if (a.getQualifiedData() != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				DummyLogger.sysout("AttemptBean", "transform", "qualifiedData --> " + a.getQualifiedData());
				Map<Long, QualifiedAnswerModel> qualifiedData = mapper.readValue(a.getQualifiedData(), Map.class);
				DummyLogger.sysout("AttemptBean", "transform", "qualifiedData --> " + qualifiedData);

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return m;
	}

	public byte[] getReport(List<Long> quizzIdList, List<String> quizShortDesc) {
		List<Attempt> allAttempts = attemptDao.findByQuizIdInAndRecordStatus(quizzIdList,
				EntityConstants.STATUS_ACTIVE);

		StringBuilder sb = new StringBuilder("user"+SEPARATOR+" intento"+SEPARATOR);
		for (String qId : quizShortDesc) {
			sb.append(qId + SEPARATOR);
		}

		List<String> allUsers = new ArrayList<>();
		for (Attempt a : allAttempts) {
			if (!allUsers.contains(a.getUserId())) {
				allUsers.add(a.getUserId());
			}
		}
		sb.append("\n");
		for (String user : allUsers) {
			int attemptCounter = 1;
			sb.append(user + SEPARATOR + attemptCounter + SEPARATOR);
			for (Long qId : quizzIdList) {
				/*
				 * List<Attempt> attempt = allAttempts.stream() .filter(at ->
				 * at.getQuizId().equals(qId) &&
				 * at.getUserId().equals(user)).collect(Collectors.toList()); attempt.sort((o1,
				 * o2) -> o1.getQuizId().compareTo(o2.getQuizId()));
				 * 
				 * for (Attempt a : attempt) { sb.append(attemptCounter + ", "); }
				 */
				Optional<Attempt> attempt = allAttempts.stream()
						.filter(at -> at.getQuizId().equals(qId) && at.getUserId().equals(user)).findFirst();
				if (attempt.isPresent()) {
					if (attempt.get().getAttemptScore() != null) {
						sb.append(attempt.get().getAttemptScore() + SEPARATOR);
					} else {
						sb.append(SEPARATOR);
					}
				} else {
					sb.append(SEPARATOR);
				}
			}

			sb.append("\n");
		}

		return sb.toString().getBytes();
	}
}
