package com.sauriotech.eventquizzer.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.dao.AnswerDao;
import com.sauriotech.eventquizzer.entity.Answer;
import com.sauriotech.eventquizzer.entity.EntityConstants;
import com.sauriotech.eventquizzer.models.AnswerModel;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class AnswerBean {
	@Autowired
	private AnswerDao answerDao;
	
	public List<AnswerModel> getActiveAnswersByQuestion(Long questionId) {
		List<Answer> answers = answerDao.findByRecordStatusAndQuestionId(EntityConstants.STATUS_ACTIVE, questionId);
		List<AnswerModel> modelList = new ArrayList<>();
		
		if(!answers.isEmpty()) {
			for(Answer a : answers) {
				AnswerModel m = new AnswerModel(EntityConstants.STATUS_ACTIVE, null);
				BeanUtils.copyProperties(a, m);
				modelList.add(m);
			}
		}
		
		
		return modelList;
	}
}
