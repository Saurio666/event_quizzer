/**
 * 
 */
package com.sauriotech.eventquizzer.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		
		List<Question> questions = questionDao.findByRecordStatusAndQuizzId(EntityConstants.STATUS_ACTIVE, quizzId);
		List<QuestionModel> modelList = new ArrayList<>();
		
		if(!questions.isEmpty()) {
			for(Question q : questions) {
				QuestionModel m = new QuestionModel(ModelConstants.OK, null);
				BeanUtils.copyProperties(q, m);
				
				if (attachAnswers) {
					List<AnswerModel> answers = answerBean.getActiveAnswersByQuestion(q.getId());
					
					if (q.getAnswersRandomSort() == 1) {
						Collections.shuffle(answers);
					}
					m.setAnswers(answers);
				}
				
				modelList.add(m);
			}
		}
		
		return modelList;
	}
}
