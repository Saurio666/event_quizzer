/**
 * 
 */
package com.sauriotech.eventquizzer.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sauriotech.eventquizzer.models.AnswerModel;
import com.sauriotech.eventquizzer.models.EventModel;
import com.sauriotech.eventquizzer.models.QuestionModel;
import com.sauriotech.eventquizzer.models.QuizzModel;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class DummyBean {
	Long answerId = 1L;

	protected Map<String, Long> answeredQuizzes = new HashMap<String, Long>();
	protected Map<Long, QuizzModel> quizzDb = new HashMap<Long, QuizzModel>();
	protected Map<String, Object> usersDb = new HashMap<String, Object>();

	protected Map<Long, Map<String, Object>> usersAccessDb = new HashMap<Long, Map<String, Object>>();

	protected Map<Long, EventModel> eventDb = new HashMap<Long, EventModel>();

	private List<AnswerModel> answers;

	protected Map<Long, Map<String, List<String>>> attendanceDb = new HashMap<Long, Map<String, List<String>>>();

	protected void LoadDummyData() {
		if (!quizzDb.containsKey(23L)) {
			quizzDb.put(23L, new QuizzModel(23L, "SITECORE 10", "Simulator for SC10 Developer cert.", getQuestions(23L),
					"OK", null));
		}

		if (!usersDb.containsKey("12345"))
			usersDb.put("12345", null);
		if (!usersDb.containsKey("1111"))
			usersDb.put("1111", null);
		if (!usersDb.containsKey("2222"))
			usersDb.put("2222", null);

		if (!usersAccessDb.containsKey(23L)) {
			usersAccessDb.put(23L, new HashMap<>());

			usersAccessDb.get(23L).put("12345", null);
			usersAccessDb.get(23L).put("1111", null);
		}

		if (!eventDb.containsKey(23L)) {
			eventDb.put(23L, new EventModel(23L, "Lorem ipsum dolor sit amet nullam id egestas urna aliquam",
					"Lorem ipsum dolor sit accumsan interdum nisi, quis tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent. Vestibulum ante ipsum primis in faucibus magna blandit adipiscing eu felis iaculis volutpat lorem ipsum dolor sit amet dolor consequat.",
					"OK", null));
		}

		if (!attendanceDb.containsKey(23L)) {
			attendanceDb.put(23L, new HashMap<>());
		}
	}

	private List<QuestionModel> getQuestions(Long quizzId) {
		List<QuestionModel> questions = new ArrayList<QuestionModel>();
		questions.add(new QuestionModel(1L,
				"You have been hired to create a company's website, and, as you prepare, you must define the data structures in Sitecore to create items and content. What should you use to define your data structures?",
				"unique", true, getAnswers(1L)));
		questions.add(new QuestionModel(2L,
				"While using the Sitecore Install Assistant (SIA), you encounter the error below: Based on the information provided in the image, what is the most likely reason for this install error? ",
				"unique", true, getAnswers(2L)));
		questions.add(new QuestionModel(3L,
				"You want to control the order in which patch files load. Which element could you use in the Layers.config file to accomplish this?",
				"unique", true, getAnswers(3L)));
		questions.add(new QuestionModel(4L, "What is the purpose of a Sitecore Support Package?", "unique", true,
				getAnswers(4L)));
		questions.add(new QuestionModel(5L,
				"You are setting up a Sitecore instance without the use of SXA and want to accommodate three publicfacing websites. Where would you map the correct Home item to its corresponding hostname?",
				"unique", true, getAnswers(5L)));
		questions.add(new QuestionModel(6L,
				"Sitecore utilizes several databases with various purposes. What data is stored in the Master database?",
				"unique", true, getAnswers(6L)));
		questions.add(new QuestionModel(7L, "Which statement characterizes the Sitecore Extranet security domain?",
				"unique", true, getAnswers(7L)));
		questions.add(new QuestionModel(8L,
				"If the Sitecore Content Management instance can be accessed by multiple host names, what would you do to enable users for each site to be able to log in to the Sitecore shell?",
				"unique", true, getAnswers(8L)));
		questions.add(new QuestionModel(9L,
				"A team member incorrectly entered their password several times when attempting to log in to Sitecore and is now locked out of their account. What can you do to unlock this user?",
				"unique", true, getAnswers(9L)));
		questions.add(new QuestionModel(10L, "Which of the following information is available within Workbox?",
				"unique", true, getAnswers(10L)));
		questions.add(new QuestionModel(11L,
				"As a Developer, why should you assign security to roles instead of users when setting up security on a site?",
				"unique", true, getAnswers(11L)));
		questions.add(new QuestionModel(12L,
				"You have configured a new role for graphic designers at your company. How would you add multiple existing users to the role?",
				"unique", true, getAnswers(12L)));
		questions.add(new QuestionModel(13L, "What are the three elements that make up a workflow? (Choose three.)",
				"multiple", true, getAnswers(13L)));
		questions.add(new QuestionModel(14L,
				"Which three areas of Sitecore allow you to view the available workflow commands of an item you are editing? (Choose three.)",
				"multiple", true, getAnswers(14L)));
		questions.add(new QuestionModel(15L,
				"For non-SXA websites, why is it recommended to avoid setting presentation details directly on new content items?",
				"unique", true, getAnswers(15L)));
		questions.add(new QuestionModel(16L,
				"Of the available options, which is a purpose of the field source in data templates?", "unique", true,
				getAnswers(16L)));
		questions.add(new QuestionModel(17L,
				"In a non-SXA multisite implementation, what is the recommended practice for storing reusable content that is shared between the different sites?",
				"unique", true, getAnswers(17L)));
		questions.add(new QuestionModel(18L, "What is the purpose of standard values on data templates?", "unique",
				true, getAnswers(18L)));
		questions.add(new QuestionModel(19L,
				"As you create templates and add standard values, you know items created from the templates should automatically have the Title field populated with the name of the item. What should you do to ensure this occurs?",
				"unique", true, getAnswers(19L)));

		return questions;
	}

	private List<AnswerModel> getAnswers(Long questionId) {
		if (answers == null) {
			answers = new ArrayList<AnswerModel>();
			answers.add(new AnswerModel(1L, "Templates", 1D, 1L));
			answers.add(new AnswerModel(2L, "Config files", 0D, 1L));
			answers.add(new AnswerModel(3L, "SQL tables", 0D, 1L));
			answers.add(new AnswerModel(4L, "Renderings", 0D, 1L));
			answers.add(new AnswerModel(5L, "The Sitecore instance username/password were incorrect.", 0D, 2L));
			answers.add(new AnswerModel(6L, "The Solr install path was set incorrectly.", 1D, 2L));
			answers.add(new AnswerModel(7L, "The Solr service was stopped.", 0D, 2L));
			answers.add(new AnswerModel(8L, "The SQL server username/password were incorrect.", 0D, 2L));
			answers.add(new AnswerModel(9L, "<configLayer>", 0D, 3L));
			answers.add(new AnswerModel(10L, "<loadOrder>", 1D, 3L));
			answers.add(new AnswerModel(11L, "<configLoad>", 0D, 3L));
			answers.add(new AnswerModel(12L, "<patchOrder>", 0D, 3L));
			answers.add(new AnswerModel(13L,
					"To help Sitecore replicate an issue without needing to access the environment directly.", 1D, 4L));
			answers.add(new AnswerModel(14L,
					"To let Sitecore Support Services access active logs running directly in the environment.", 0D,
					4L));
			answers.add(new AnswerModel(15L,
					"To make it easier for you to download update packages and install them into your environment.", 0D,
					4L));
			answers.add(new AnswerModel(16L, "To give Sitecore direct access to your environment for support purposes.",
					0D, 4L));
			answers.add(new AnswerModel(17L, "In the /system/settings section of the Master database", 0D, 5L));
			answers.add(new AnswerModel(18L, "In the Sitecore Core database's settings file", 0D, 5L));
			answers.add(
					new AnswerModel(19L, "In the Internet Information Services website definition settings", 0D, 5L));
			answers.add(
					new AnswerModel(20L, "In a config patch file located in the/App_Config/Include folder", 1D, 5L));
			answers.add(new AnswerModel(21L, "Configuration information about the Sitecore user interfaces", 0D, 6L));
			answers.add(new AnswerModel(22L, "Only the latest versions of items that have been published", 0D, 6L));
			answers.add(new AnswerModel(23L, ".NET membership and security authorization tables", 0D, 6L));
			answers.add(new AnswerModel(24L, "All versions of all the content, including unpublished content", 1D, 6L));
			answers.add(new AnswerModel(25L,
					"It provides default access to the Sitecore identity server for users to log in to the appropriate environment areas.",
					0D, 7L));
			answers.add(new AnswerModel(26L,
					"It is an internal security domain for users that can access the Sitecore client tools and editing functionality.",
					0D, 7L));
			answers.add(new AnswerModel(27L,
					"It is the domain that contains user accounts as well as customized roles for managing read access to the website content.",
					1D, 7L));
			answers.add(new AnswerModel(28L,
					"It provides customized roles for controlling access to third-party environments such as a CRM or payment portal.",
					0D, 7L));
			answers.add(new AnswerModel(29L,
					"Patch the web.config for the Content Management instance with the required permission groups.", 0D,
					8L));
			answers.add(new AnswerModel(30L,
					"Update the role for each user group to include the appropriate site permissions.", 0D, 8L));
			answers.add(new AnswerModel(31L,
					"Remove all specific host names from the Identity Server configuration settings file and replace them with wild-card options.",
					0D, 8L));
			answers.add(new AnswerModel(32L,
					"Add AllowedCorsOriginsGroup for each site to the Identity Server configuration.", 1D, 8L));
			answers.add(new AnswerModel(33L,
					"Go to the User Manager, select the user, and click Edit to update user settings.", 0D, 9L));
			answers.add(new AnswerModel(34L,
					"Go to the Security Editor, select the user, and update their access rights to Sitecore.", 0D, 9L));
			answers.add(new AnswerModel(35L,
					"Go to the User Manager, select the user, and click Change password to unlock the user's account by resetting their password.",
					0D, 9L));
			answers.add(new AnswerModel(36L,
					"Go to the User Manager, select the user, and click Unlock in the navigation ribbon.", 1D, 9L));
			answers.add(new AnswerModel(37L, "Number of editable data source items in all of Sitecore", 0D, 10L));
			answers.add(new AnswerModel(38L, "Information about an item's originating template", 0D, 10L));
			answers.add(new AnswerModel(39L, "Number of active campaigns divided by marketing team", 0D, 10L));
			answers.add(new AnswerModel(40L, "Available workflow commands divided by associated workflow", 1D, 10L));
			answers.add(new AnswerModel(41L,
					"Security can be managed more efficiently as the rights and permissions apply to a group of users.",
					1D, 11L));
			answers.add(new AnswerModel(42L,
					"Role-based security allows multiple users to have the same username but different passwords.", 0D,
					11L));
			answers.add(new AnswerModel(43L,
					"Assigning security to roles enables individual rights and permissions to be configured by users.",
					0D, 11L));
			answers.add(new AnswerModel(44L,
					"Individual users with a role can set their own item security permissions.", 0D, 11L));
			answers.add(new AnswerModel(45L,
					"Go to the Security Editor and use the Assign tool to assign users to the role. ", 0D, 12L));
			answers.add(new AnswerModel(46L, "Go to the Role Manager and click New to add users to the selected role.",
					0D, 12L));
			answers.add(new AnswerModel(47L,
					"Go to the Role Manager and use the Members tool to add users as members of the role.", 1D, 12L));
			answers.add(new AnswerModel(48L,
					"Go to the User Manager and click New to add the selected users to the desired role.", 0D, 12L));
			answers.add(new AnswerModel(49L, "Actions", 1D, 13L));
			answers.add(new AnswerModel(50L, "Submissions", 0D, 13L));
			answers.add(new AnswerModel(51L, "Security", 0D, 13L));
			answers.add(new AnswerModel(52L, "Permissions", 0D, 13L));
			answers.add(new AnswerModel(53L, "States", 1D, 13L));
			answers.add(new AnswerModel(54L, "Commands", 1D, 13L));
			answers.add(new AnswerModel(55L, "Approvals", 0D, 13L));
			answers.add(new AnswerModel(56L, "Security Editor", 0D, 14L));
			answers.add(new AnswerModel(57L, "Workflow Command", 0D, 14L));
			answers.add(new AnswerModel(58L, "Experience Editor", 1D, 14L));
			answers.add(new AnswerModel(59L, "Content Editor", 1D, 14L));
			answers.add(new AnswerModel(60L, "Workbox", 1D, 14L));
			answers.add(new AnswerModel(61L, "Path Analyzer", 0D, 14L));
			answers.add(new AnswerModel(62L,
					"Defining presentation details on standard values ensures all the fields related to an item's presentation are configured for access by content creators.",
					0D, 15L));
			answers.add(new AnswerModel(63L,
					"Sitecore does not allow presentation details to be changed at the content-item level, so they must be set and edited from the standard values of templates.",
					0D, 15L));
			answers.add(new AnswerModel(64L,
					"It allows all content items made from the same template to have the same presentation details by default and eliminates additional work during content creation.",
					1D, 15L));
			answers.add(new AnswerModel(65L,
					"It forces a prompt to appear when new content items are created so Content Authors can finalize the presentation details for the new content items.",
					0D, 15L));
			answers.add(new AnswerModel(66L,
					"The field source is a field property that designates where in the content tree the field obtains its data.",
					1D, 16L));
			answers.add(new AnswerModel(67L,
					"The field source is used to control a user’s access and permissions to folders and files in the content tree.",
					0D, 16L));
			answers.add(new AnswerModel(68L,
					"The field source defines how a field’s data appears in the Content Editor in the UI.", 0D, 16L));
			answers.add(new AnswerModel(69L,
					"A field source maps a field to the underlying class representation in the API.", 0D, 16L));
			answers.add(new AnswerModel(70L,
					"You should create a folder on each site and use cloning for this type of content.", 0D, 17L));
			answers.add(
					new AnswerModel(71L, "It is not a recommended practice to share content between sites.", 0D, 17L));
			answers.add(new AnswerModel(72L,
					"You should store this type of content outside of any one site's content tree structure.", 1D,
					17L));
			answers.add(new AnswerModel(73L, "Location does not matter because content can be retrieved from anywhere.",
					0D, 17L));
			answers.add(new AnswerModel(74L,
					"To give Content Authors approved options for the field values when creating items from the given template",
					0D, 18L));
			answers.add(new AnswerModel(75L,
					"To provide the default values for fields when a new item is created from the template and a fallback value when fields are left NULL",
					1D, 18L));
			answers.add(new AnswerModel(76L,
					"To provide standardized, permanent values for certain fields within the given template that cannot be overridden when a new item is created",
					0D, 18L));
			answers.add(new AnswerModel(77L,
					"To record an example of how to use the template to provide additional guidance when creating content items based on the template",
					0D, 18L));
			answers.add(
					new AnswerModel(78L, "Add $name token to the template's standard values Title field.", 1D, 19L));
			answers.add(new AnswerModel(79L, "Set $name token in the new item's content Title field.", 0D, 19L));
			answers.add(new AnswerModel(80L, "Set_name token in the new item's content Title field.", 0D, 19L));
			answers.add(
					new AnswerModel(81L, "Add _name token to the template's standard values Title field.", 0D, 19L));
		}

		return answers.stream().filter(a -> a.getQuestionId() == questionId).collect(Collectors.toList());
	}

	protected String validateAccess(Long quizzId, Long eventId, String userId) {
		if (usersAccessDb.containsKey(quizzId)) {
			if (quizzId != null) {
				if (usersAccessDb.get(quizzId).containsKey(userId)) {
					return "OK";
				}
				System.out.println("Quizz exists, but not authorized for user " + userId);
				return "Quizz exists, but not authorized for user " + userId;
			} else {
				if (usersAccessDb.get(eventId).containsKey(userId)) {
					return "OK";
				}
				System.out.println("Event exists, but not authorized for user " + userId);
				return "Event exists, but not authorized for user " + userId;
			}
		}

		System.out.println("Quizz " + quizzId + " or event " + eventId + " no exists");

		return "Quizz " + quizzId + " or event " + eventId + " no exists";
	}

	protected boolean existsAttendance(Long eventId, String userId, String date) {
		System.out.println("[existsAttendance] eventId: " + eventId);
		System.out.println("[existsAttendance] userId: " + userId);
		System.out.println("[existsAttendance] date: " + date);
		System.out.println("[existsAttendance] attendanceDb: " + attendanceDb);

		if (attendanceDb.containsKey(eventId)) {

			System.out.println("[existsAttendance] Contains key " + eventId);

			Map<String, List<String>> userData = attendanceDb.get(eventId);
			System.out.println("[existsAttendance] userData: " + userData);

			System.out.println("[existsAttendance] Contains key " + eventId);
			if (!userData.containsKey(userId)) {
				System.out.println("[existsAttendance] userData exists for " + userId);
				userData.put(userId, new ArrayList<>());
			}

			// Find date
			List<String> dates = userData.get(userId);
			System.out.println("[existsAttendance] dates " + dates);
			return dates.contains(date);
		}
		return false;
	}

	protected void setAttendance(Long eventId, String userId, String date) {
		if (attendanceDb.containsKey(eventId)) {
			Map<String, List<String>> userData = attendanceDb.get(eventId);

			if (!userData.containsKey(userId)) {
				userData.put(userId, new ArrayList<>());
			}
			userData.get(userId).add(date);
		}
	}
}
