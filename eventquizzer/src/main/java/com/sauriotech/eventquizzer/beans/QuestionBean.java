/**
 * 
 */
package com.sauriotech.eventquizzer.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.dao.QuestionDao;
import com.sauriotech.eventquizzer.entity.EntityConstants;
import com.sauriotech.eventquizzer.entity.Question;
import com.sauriotech.eventquizzer.models.AnswerModel;
import com.sauriotech.eventquizzer.models.ModelConstants;
import com.sauriotech.eventquizzer.models.QuestionModel;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class QuestionBean {
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private AnswerBean answerBean;

	public List<QuestionModel> getActiveQuestionsByQuizz(Long quizzId, boolean attachAnswers) {

		List<Question> questions = questionDao.findByRecordStatusAndQuizzIdOrderByIdAsc(EntityConstants.STATUS_ACTIVE,
				quizzId);
		List<QuestionModel> modelList = new ArrayList<>();

		if (!questions.isEmpty()) {
			List<Question> parentQuestions = questions.stream().filter(q -> q.parentQuestionId == null)
					.collect(Collectors.toList());

			for (Question q : parentQuestions) {
				QuestionModel m = new QuestionModel(ModelConstants.OK, null);
				BeanUtils.copyProperties(q, m);

				if (q.getType().equals("parent")) {
					// Get childs
					List<Question> childs = questions.stream()
							.filter(pq -> pq.parentQuestionId != null && pq.parentQuestionId.equals(q.getId()))
							.collect(Collectors.toList());
					if (!childs.isEmpty()) {
						List<QuestionModel> childModelList = new ArrayList<>();
						for (Question ch : childs) {
							QuestionModel mCh = new QuestionModel(ModelConstants.OK, null);
							BeanUtils.copyProperties(ch, mCh);
							setAnswers(mCh, attachAnswers, childModelList, q.getAnswersRandomSort());
						}
						
						if (!childModelList.isEmpty()) {
							m.setSubQuestions(childModelList);
						}
						
						modelList.add(m);
					}
				} else {
					setAnswers(m, attachAnswers, modelList, q.getAnswersRandomSort());
				}
			}
		}

		return modelList;
	}

	private void setAnswers(QuestionModel m, boolean attachAnswers, List<QuestionModel> modelList, int randomSort) {
		if (attachAnswers) {
			List<AnswerModel> answers = answerBean.getActiveAnswersByQuestion(m.getId());
			if (!answers.isEmpty()) {
				if (randomSort == 1) {
					Collections.shuffle(answers);
				}
				m.setAnswers(answers);

				m.setCorrectAnswers((int) answers.stream().filter(a -> a.getWeight() > 0).count());

				modelList.add(m);
			}
		} else {
			modelList.add(m);
		}
	}
}
