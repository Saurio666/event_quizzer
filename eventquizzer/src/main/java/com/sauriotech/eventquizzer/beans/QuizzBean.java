package com.sauriotech.eventquizzer.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.dao.QuizzDao;
import com.sauriotech.eventquizzer.entity.EntityConstants;
import com.sauriotech.eventquizzer.entity.Quizz;
import com.sauriotech.eventquizzer.models.AnswerModel;
import com.sauriotech.eventquizzer.models.ModelConstants;
import com.sauriotech.eventquizzer.models.QuestionModel;
import com.sauriotech.eventquizzer.models.QuizzModel;
import com.sauriotech.eventquizzer.utils.DummyLogger;
import com.sauriotech.eventquizzer.utils.Language;

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

	public List<QuizzModel> getActiveQuizzesByEvent(Long eventId, boolean setQuestions) {
		List<Quizz> quizzez = quizzDao.findByRecordStatusAndEventId(EntityConstants.STATUS_ACTIVE, eventId);

		List<QuizzModel> models = new ArrayList<>();

		if (!quizzez.isEmpty()) {

			for (Quizz q : quizzez) {
				QuizzModel m = transformEntity(q, setQuestions);
				models.add(m);
			}
		}

		return models;
	}

	public QuizzModel getQuizzById(Long quizzId) {
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
		return m;
	}

	private QuizzModel transformEntity(Quizz q, boolean setQuestions) {
		QuizzModel m = new QuizzModel(ModelConstants.OK, "");
		BeanUtils.copyProperties(q, m);
		m.setShowScoreOnFinish(q.getShowScoreOnFinish() == 1);
		m.setRequiresAuthorization(q.getRequiresAuthorization() == 1);

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

	public Map<String, Object> saveQuizz(String userId, Long quizzId, Map<String, String> answers) {
		QuizzModel model = getQuizzById(quizzId);

		Double score = 0D;
		for (QuestionModel question : model.getQuestions()) {
			if ("multiple".equals(question.getType())) {

			} else if ("unique".equals(question.getType())) {

			}

			for (AnswerModel answer : question.getAnswers()) {
				if (answer.getWeight() > 0) {
					if (answers.containsKey(question.getId().toString())) {
						String response = answers.get(question.getId().toString());
						if (response.contains(answer.getId().toString()))
							score += answer.getWeight();
					}
				}
			}
		}

		DummyLogger.sysout("QuizzBean", "saveQuizz", "-----------> score: " + score);
		Map<String, Object> data = new HashMap<>();
		data.put("score", score);
		// answeredQuizzes.put(userId, quizzId);

		return data;
	}
}
