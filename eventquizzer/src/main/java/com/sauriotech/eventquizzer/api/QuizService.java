package com.sauriotech.eventquizzer.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sauriotech.eventquizzer.beans.QuizzBean;
import com.sauriotech.eventquizzer.models.QuizzModel;
import com.sauriotech.eventquizzer.security.BasicAuthorizer;
import com.sauriotech.eventquizzer.security.Credentials;
import com.sauriotech.eventquizzer.utils.DummyLogger;
import com.sauriotech.eventquizzer.utils.Language;

@RestController
@RequestMapping("/api/quizz")
@CrossOrigin(origins = "*")
public class QuizService {
	@Autowired
	private QuizzBean quizzBean;
	@Autowired
	private BasicAuthorizer basicAuthorizer;

	/*@GetMapping("/{quizId}/{userId}")
	public ResponseEntity<QuizzModel> getQuizz(@PathVariable("quizId") Long quizId,
			@PathVariable("userId") String userId) {
		try {
			QuizzModel data = quizzBean.getQuizzById(quizId);
			if (data == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/

	@GetMapping("/{quizId}")
	public ResponseEntity<QuizzModel> getQuizz(@PathVariable("quizId") Long quizId) {
		try {
			QuizzModel data = quizzBean.getQuizzByIdNonStart(quizId);
			if (data == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/admin/update/{token}")
	public ResponseEntity<ApiResponse> refreshQuizz(@PathVariable("token") String token) {
		try {
			Credentials credentials = basicAuthorizer.parseToken(token);

			if (credentials != null) {

				String data = quizzBean.updateData(credentials.getUserId(), credentials.getQuizId());
				return new ResponseEntity<>(new ApiResponse("OK", data), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("ERR", "Bad request"), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/dummy/{eventId}")
	public ResponseEntity<List<QuizzModel>> getQuizzes(@PathVariable("eventId") Long eventId) {
		try {
			Map<String, Object> responseData = new HashMap<>();
			List<QuizzModel> data = quizzBean.getActiveQuizzesByEvent(eventId, true, responseData);
			if (data == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/event/{eventId}")
	public ResponseEntity<ApiResponse> getQuizzesByEvent(@PathVariable("eventId") Long eventId) {
		try {
			Map<String, Object> responseData = new HashMap<>();
			List<QuizzModel> data = quizzBean.getActiveQuizzesByEvent(eventId, false, responseData);
			if (data == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			responseData.put("quiz", data);
			return new ResponseEntity<>(new ApiResponse("OK", "", responseData), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/event/{eventId}/{userId}")
	public ResponseEntity<ApiResponse> getQuizzesByEventAndUser(@PathVariable("eventId") Long eventId,
			@PathVariable("userId") String userId) {
		DummyLogger.sysout("QuizService", "getQuizzesByEventAndUser","eventId ---> " + eventId);
		DummyLogger.sysout("QuizService", "getQuizzesByEventAndUser","userId ---> " + userId);
		try {
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("u_id", userId);
			List<QuizzModel> data = quizzBean.getActiveQuizzesByEvent(eventId, false, responseData);
			if (data == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			responseData.put("quiz", data);
			return new ResponseEntity<>(new ApiResponse("OK", "", responseData), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/tkn/{token}")
	public ResponseEntity<ApiResponse> getQuizz(@PathVariable("token") String token) {
		try {
			Map<String, Object> responseData = new HashMap<>();
			Credentials credentials = basicAuthorizer.parseToken(token);

			if (credentials != null) {
				//QuizzModel data = quizzBean.getQuizz(credentials.getQuizId(), credentials.getUserId());
				QuizzModel data = quizzBean.getQuizzByIdStartAttempt(credentials.getQuizId(), credentials.getUserId(), responseData);
				if (data == null) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
				responseData.put("model", data);
				return new ResponseEntity<>(new ApiResponse("OK", "", responseData), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("ERR", Language.getInstance().getMessage(null, "BAD_REQUEST")), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/submit")
	public ResponseEntity<ApiResponse> submitQuizz(@RequestBody Map<String, Object> data) {
		DummyLogger.sysout("QuizService", "submitQuizz", data);

		Map<String, String> answers = (Map<String, String>) data.get("answers");
		DummyLogger.sysout("QuizService", "submitQuizz","Answers ---> " + answers);
		DummyLogger.sysout("QuizService", "submitQuizz", "Action --> " + data.get("smt_act"));
		DummyLogger.sysout("QuizService", "submitQuizz", "AttemptId --> " + data.get("att_id"));
		
		String userId = data.get("userId").toString();
		String qId = data.get("id").toString();
		String smtAct = String.valueOf(data.get("smt_act"));
		Long attemptId = Long.parseLong(String.valueOf(data.get("att_id")));
		Long quizzId = null;
		if("tkn".equals(qId)) {
			Credentials credentials = basicAuthorizer.parseToken(userId);
			if (credentials != null) {				
				userId = credentials.getUserId();
				quizzId = credentials.getQuizId();
			} else {
				return new ResponseEntity<>(new ApiResponse("ERR", "Bad request"), HttpStatus.OK);
			}
		} else {
			quizzId = Long.parseLong(data.get("id").toString());			
			//quizzBean.saveQuizz(userId, quizzId, answers);
		}
		
		Map<String, Object> responseData = quizzBean.saveQuizz(userId, quizzId, attemptId, smtAct, answers);

		return new ResponseEntity<>(new ApiResponse("OK", "", responseData), HttpStatus.OK);
	}
}
