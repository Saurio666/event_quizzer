package com.sauriotech.eventquizzer.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.models.AnswerModel;
import com.sauriotech.eventquizzer.models.QuestionModel;
import com.sauriotech.eventquizzer.models.QuizzModel;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class QuizzBean extends DummyBean {
	
	public List<QuizzModel> getActiveQuizzesByEvent(Long eventId) {
		
		
		
		return null;
	}

	public QuizzModel getQuizz(Long quizzId, String userId) {
		if (answeredQuizzes.containsKey(userId)) {
			return new QuizzModel("ERR", "quizz_msg_already");
		}

		LoadDummyData();

		String access = validateAccess(quizzId, null, userId);

		if ("OK".equals(access))
			return quizzDb.get(23L);

		return new QuizzModel("ERR", access);
	}

	public Map<String, Object> saveQuizz(String userId, Long quizzId, Map<String, String> answers) {
		QuizzModel model = quizzDb.get(quizzId);

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

		System.out.println("-----------> score: " + score);
		Map<String, Object> data = new HashMap<>();
		data.put("score", score);
		//answeredQuizzes.put(userId, quizzId);
		
		return data;
	}
}
