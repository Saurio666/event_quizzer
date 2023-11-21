package com.sauriotech.eventquizzer.beans;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sauriotech.eventquizzer.dao.AttemptDao;
import com.sauriotech.eventquizzer.dao.QuizzDao;
import com.sauriotech.eventquizzer.entity.Attempt;
import com.sauriotech.eventquizzer.entity.EntityConstants;
import com.sauriotech.eventquizzer.entity.Quizz;
import com.sauriotech.eventquizzer.models.AnswerModel;
import com.sauriotech.eventquizzer.models.AttemptModel;
import com.sauriotech.eventquizzer.models.EventAuthorizedUserModel;
import com.sauriotech.eventquizzer.models.EventModel;
import com.sauriotech.eventquizzer.models.ModelConstants;
import com.sauriotech.eventquizzer.models.QualifiedAnswerModel;
import com.sauriotech.eventquizzer.models.QuestionModel;
import com.sauriotech.eventquizzer.models.QuizzModel;
import com.sauriotech.eventquizzer.security.BasicAuthorizer;
import com.sauriotech.eventquizzer.utils.DummyLogger;
import com.sauriotech.eventquizzer.utils.Language;
import com.sauriotech.eventquizzer.utils.TimezoneHelper;
import com.sauriotech.eventquizzer.utils.UIMessage;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class QuizzBean extends DummyBean {
	@Autowired
	private QuizzDao quizzDao;
	@Autowired
	private QuestionBean questionBean;
	@Autowired
	private EventBean eventBean;
	@Autowired
	private AttemptBean attemptBean;
	@Autowired
	private BasicAuthorizer basicAuthorizer;
	@Autowired
	private EventAuthorizedUserBean eventAuthorizedUserBean;
	@Autowired
	private AttemptDao attemptDao;

	public List<QuizzModel> getActiveQuizzesByEvent(Long eventId, boolean setQuestions,
			Map<String, Object> responseData) {
		EventModel em = eventBean.getActiveEvent(eventId);
		responseData.put("event", em);

		/*
		 * EventAuthorizedUserModel authorization =
		 * eventAuthorizedUserBean.getAuthorization(eventId,
		 * String.valueOf(responseData.get("u_id")));
		 * 
		 * if (ModelConstants.OK.equals(authorization.getStatus())) {
		 */
		List<Quizz> quizzez = quizzDao.findByRecordStatusAndEventId(EntityConstants.STATUS_ACTIVE, eventId);

		List<QuizzModel> models = new ArrayList<>();

		if (!quizzez.isEmpty()) {

			for (Quizz q : quizzez) {
				DummyLogger.sysout("QuizzBean", "getActiveQuizzesByEvent",
						"q.getQuizzStartDate() " + q.getQuizzStartDate());
				DummyLogger.sysout("QuizzBean", "getActiveQuizzesByEvent",
						"q.getQuizzEndDate() " + q.getQuizzEndDate());

				Date check1 = TimezoneHelper.getDateFromTimezone(em.getEventTimezone());
				DummyLogger.sysout("QuizzBean", "getActiveQuizzesByEvent", "check1 " + check1);
				DummyLogger.sysout("QuizzBean", "getActiveQuizzesByEvent", "check --> "
						+ TimezoneHelper.checkDateBetweenTwoDates(q.getQuizzStartDate(), q.getQuizzEndDate(), check1));

				boolean inDate = TimezoneHelper.checkDateBetweenTwoDates(q.getQuizzStartDate(), q.getQuizzEndDate(),
						check1);

				if (inDate) {
					QuizzModel m = transformEntity(q, setQuestions);
					if (responseData.containsKey("u_id")) {
						m.setQuizUrl(
								basicAuthorizer.encryptData(q.getId() + ";" + responseData.get("u_id").toString()));
					}
					models.add(m);
				}
			}
		}

		return models;
		/*
		 * } responseData.put("", authorization); return new ArrayList<>();
		 */
	}

	public QuizzModel getQuizzByIdNonStart(Long quizzId) {
		return getQuizzById(quizzId, null, false, null);
	}

	public QuizzModel getQuizzByIdStartAttempt(Long quizzId, String userId, Map<String, Object> responseData) {
		return getQuizzById(quizzId, userId, true, responseData);
	}

	private QuizzModel getQuizzById(Long quizzId, String userId, boolean startAttempt,
			Map<String, Object> responseData) {
		QuizzModel m = ThreadSafeBean.getInstance().getQuizzModel(quizzId);

		if (m == null) {
			DummyLogger.sysout("QuizzBean", "getQuizzById", "Try get quizz " + quizzId + " from database");
			Optional<Quizz> q = quizzDao.findById(quizzId);

			if (q.isPresent()) {
				DummyLogger.sysout("QuizzBean", "getQuizzById", "Quizz finded " + quizzId + " from database");
				m = transformEntity(q.get(), true);
				ThreadSafeBean.getInstance().addQuizzModel(q.get().getId(), m);
			} else {
				return new QuizzModel("ERR", Language.getInstance().getMessage(null, BeansConstants.Quizz.NOT_FOUND));
			}
		}

		DummyLogger.sysout("QuizzBean", "getQuizzById", "Get quizz " + quizzId + " from cache");

		// Only in this case not set the attempt into cache
		if (startAttempt) {
			// Verify max attempts
			DummyLogger.sysout("QuizzBean", "getQuizzById", "Check for previus attempts");
			DummyLogger.sysout("QuizzBean", "getQuizzById", "userId " + userId);
			DummyLogger.sysout("QuizzBean", "getQuizzById", "quizzId " + quizzId);
			Date check1 = TimezoneHelper.getDateFromTimezone(m.getEventTimezone());
			DummyLogger.sysout("QuizzBean", "getActiveQuizzesByEvent", "check --> "
					+ TimezoneHelper.checkDateBetweenTwoDates(m.getQuizzStartDate(), m.getQuizzEndDate(), check1));
			boolean inDate = TimezoneHelper.checkDateBetweenTwoDates(m.getQuizzStartDate(), m.getQuizzEndDate(),
					check1);
			if (inDate) {

				AttemptModel am = attemptBean.startAttempt(userId, quizzId, m.getEventTimezone(),
						m.getAttemptsPerUser());
				if (am != null) {
					responseData.put("att_id", am.getId());
					responseData.put("att_str", TimezoneHelper.convertStringDateFull(am.getAttemptStart()));
					Date currentTime = TimezoneHelper.getDateFromTimezone(m.getEventTimezone());
					responseData.put("att_elp",
							TimezoneHelper.getDifferenceInMinutes(am.getAttemptStart(), currentTime));
					// responseData.
				} else {
					responseData.put("authorization",
							Language.getInstance().getMessage(null, BeansConstants.Quizz.MAX_ATTEMPTS));
					return null;
				}
			} else {
				
				UIMessage message = Language.getInstance().getMessage(null, BeansConstants.Quizz.OUT_OF_TIME);
				message.setMessage(message.getMessage().replace("#start",
						TimezoneHelper.convertStringDateFull(m.getQuizzStartDate())));
				message.setMessage(message.getMessage().replace("#end",
						TimezoneHelper.convertStringDateFull(m.getQuizzEndDate())));
				responseData.put("authorization", message);
				return null;
			}
		}

		return m;
	}

	private QuizzModel transformEntity(Quizz q, boolean setQuestions) {
		QuizzModel m = new QuizzModel(ModelConstants.OK, "");
		BeanUtils.copyProperties(q, m);
		m.setShowScoreOnFinish(q.getShowScoreOnFinish() == 1);
		m.setRequiresAuthorization(q.getRequiresAuthorization() == 1);
		m.setQuizzTime(q.getQuizzTime());

		// Get data from event
		EventModel em = eventBean.getActiveEvent(q.getEventId());
		m.setEventTimezone(em.getEventTimezone());

		if (setQuestions) {
			List<QuestionModel> qm = questionBean.getActiveQuestionsByQuizz(q.getId(), true);
			if (q.getQuestionsRandomSort() == 1) {
				Collections.shuffle(qm);
			}
			m.setQuestions(qm);
		}
		return m;
	}

	/*
	 * public QuizzModel getQuizz(Long quizzId, String userId) { if
	 * (answeredQuizzes.containsKey(userId)) { return new QuizzModel("ERR",
	 * "quizz_msg_already"); }
	 * 
	 * LoadDummyData();
	 * 
	 * String access = validateAccess(quizzId, null, userId);
	 * 
	 * if ("OK".equals(access)) return quizzDb.get(23L);
	 * 
	 * return new QuizzModel("ERR", access); }
	 */

	public Map<String, Object> saveQuizz(String userId, Long quizzId, Long attemptId, String action,
			Map<String, String> answers) {
		DummyLogger.sysout(this.getClass().getName(), "saveQuizz", "userId: " + userId);
		DummyLogger.sysout(this.getClass().getName(), "saveQuizz", "quizzId: " + quizzId);
		DummyLogger.sysout(this.getClass().getName(), "saveQuizz", "attemptId: " + attemptId);
		QuizzModel model = getQuizzByIdNonStart(quizzId);

		Double score = 0D;
		Double totalScore = 0D;
		Map<Long, QualifiedAnswerModel> qualified = new HashMap<>();
		for (QuestionModel question : model.getQuestions()) {
			DummyLogger.sysout("QuizzBean", "saveQuizz", "Question " + question.getId());
			if ("multiple".equals(question.getType())) {

			} else if ("unique".equals(question.getType())) {

			}

			if ("parent".equals(question.getType())) {
				for (QuestionModel child : question.getSubQuestions()) {
					DummyLogger.sysout("QuizzBean", "saveQuizz", "Question child " + child.getId());
					Double childTotalScore = 0D;
					Double childScore = 0D;
					for (AnswerModel answer : child.getAnswers()) {
						DummyLogger.sysout("QuizzBean", "saveQuizz", "\tanswer " + answer.getId());
						if (answer.getWeight() > 0) {
							childTotalScore += answer.getWeight();
							boolean correct = false;
							boolean selected = false;
							if (answers.containsKey(child.getId().toString())) {
								String response = answers.get(child.getId().toString());
								if (response.contains(answer.getId().toString())) {
									DummyLogger.sysout("QuizzBean", "saveQuizz", "\t\tuser select correct answer ");
									childScore += answer.getWeight();
									correct = true;
									selected = true;
								}
							}
							qualified.put(answer.getId(),
									new QualifiedAnswerModel(answer.getId(), correct, selected, child.getId(), true));
						} else {
							if (answers.containsKey(child.getId().toString())) {
								String response = answers.get(child.getId().toString());
								if (response.contains(answer.getId().toString())) {
									DummyLogger.sysout("QuizzBean", "saveQuizz", "\t\tuser select incorrect answer ");
									qualified.put(answer.getId(), new QualifiedAnswerModel(answer.getId(), false, true,
											child.getId(), false));
								}
							}
						}
					}

					DummyLogger.sysout("QuizzBean", "saveQuizz", "Child total score " + childTotalScore);
					DummyLogger.sysout("QuizzBean", "saveQuizz", "Child score " + childScore);

					totalScore += childTotalScore;
					score += childScore;
				}
			} else {
				for (AnswerModel answer : question.getAnswers()) {
					DummyLogger.sysout("QuizzBean", "saveQuizz", "\tanswer " + answer.getId());
					if (answer.getWeight() > 0) {
						totalScore += answer.getWeight();
						boolean correct = false;
						boolean selected = false;
						if (answers.containsKey(question.getId().toString())) {
							String response = answers.get(question.getId().toString());
							if (response.contains(answer.getId().toString())) {
								DummyLogger.sysout("QuizzBean", "saveQuizz", "\t\tuser select correct answer ");
								score += answer.getWeight();
								correct = true;
								selected = true;
							}
						}
						qualified.put(answer.getId(),
								new QualifiedAnswerModel(answer.getId(), correct, selected, question.getId(), true));
					} else {
						if (answers.containsKey(question.getId().toString())) {
							String response = answers.get(question.getId().toString());
							if (response.contains(answer.getId().toString())) {
								DummyLogger.sysout("QuizzBean", "saveQuizz", "\t\tuser select incorrect answer ");
								qualified.put(answer.getId(),
										new QualifiedAnswerModel(answer.getId(), false, true, question.getId(), false));
							}
						}
					}
				}
			}
		}

		DummyLogger.sysout("QuizzBean", "saveQuizz", "-----------> score: " + score);
		score = Math.round(score * 10) / 10D;
		DummyLogger.sysout("QuizzBean", "saveQuizz", "-----------> rounded score: " + score);
		Map<String, Object> data = new HashMap<>();
		data.put("score", score);
		// answeredQuizzes.put(userId, quizzId);
		totalScore = Math.round(totalScore * 10) / 10D;
		data.put("totalScore", totalScore);

		double percentage = score * 100 / totalScore;

		percentage = Math.round(percentage * 10) / 10D;

		data.put("percentage", percentage + "%");

		data.put("qualified", qualified);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String qualifiedData = mapper.writeValueAsString(qualified);
			DummyLogger.sysout("QuizzBean", "saveQuizz", "qualified: " + qualifiedData);
			String submitedData = mapper.writeValueAsString(answers);
			// AttemptModel m = attemptBean.submitAttemptInfo(userId, quizzId, submitedData,
			// qualifiedData, score, model.getEventTimezone(), action);
			AttemptModel m = attemptBean.submitAttemptInfo(attemptId, submitedData, qualifiedData, score,
					model.getEventTimezone(), action);

			data.put("attemptModel", m);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	public String updateData(String userId, Long quizzId) {
		DummyLogger.sysout(this.getClass().getName(), "updateData", "userId: " + userId);
		DummyLogger.sysout(this.getClass().getName(), "updateData", "quizzId: " + quizzId);

		ThreadSafeBean.getInstance().refreshQuizzModel(quizzId);

		return "Quiz " + quizzId + " has been updated successfully";
	}

	public byte[] getReport(Long eventId) {
		EventModel em = eventBean.getActiveEvent(eventId);
		List<Quizz> quizzes = quizzDao.findByRecordStatusAndEventIdOrderByQuizzStartDateAsc(EntityConstants.STATUS_ACTIVE, eventId);
		List<Long> quizzId = new ArrayList<>();
		List<String> quizzShortDesc = new ArrayList<>();
		
		for(Quizz q : quizzes) {
			quizzId.add(q.getId());
			quizzShortDesc.add(q.getShortDescription());
		}
		
		return attemptBean.getReport(quizzId, quizzShortDesc);
		
		
	}
}
