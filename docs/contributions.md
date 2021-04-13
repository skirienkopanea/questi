# Sergio
* Design Document: HCI base text & base research
* Design Document: RCS base text base & base research
* HomeScreen: Join a lecture as student & moderator (together with Ahmed and Elena)
* ScheduleScreen: Make a lecture with date (together with Ahmed and Elena)
* Testing: basic Junit tests for client side objects & JPA DDL constraint test for user joining non existing lecture & First mockito test (together with Ana)
* Lecture screens: GUI design & Customize/Accessiblity GUI feature & is replying feature (together with Elena)
* Moderator screen: Export questions
* Presentation video editing

# Ahmed
* views (together with Segio & Elena)
* User login (together with Sergio)
* Client objects (together with Sergio & Elena)
* Questions (post a question)
* Lecture speed poll (fully implemented)
* Leave/Start/End lecture functionality (fully implemented)
* Start a direct lecture (fully implemented)
* Question Poll (MPC) (fully implemented)
* Documentation: add a paragraph about the final design

# Elena
* Features: replying to questions, asking questions (together with Victoria), hiding questions (together with Victoria), is replying (together with Sergio)
* Testing: basic Junit tests for client side objects (Lecture, PollOption, Poll, Question, Session, UserList, User)
* Design Document: expanding upon the stakeholder tensions, work on 3 of the 10 heuristics, set up missing kyperlinks
* Screens: made the FXML base structure for screens (together with Ahmed and Sergio), make changes to the screens along the way to adapt to new requirements (together with Sergio and Victoria)
* Other contributions: wireframes/prototype design for all screens 

# Alex
* List of participants: All users in a current lecture are displayed in the moderator and student view (backend & frontend) (fully implemented)
* Short polling: made the list of questions, user list, polls and banned users refresh dynamically every two seconds (fully implemented)
* Banning users: Added the functionality for banning users (backend & frontend) (fully implemented)
* Export logs: The server logs all HTTP requests in a file and moderators can download the file locally (backend & frontend) (fully implemented)
* Database schema: Created the database schema and relational model (together with Teo and Yue)
* Backend: I specifically worked on the Lecture related classes (Entity, Controller, Service, Repository) (fully implemented)
* Tests: Wrote tests for the backend, especially for the Poll, PollOption related classes
* Design document: Wrote a paragraph explaining the reasoning behind banning users by UserId.

# Teo
* Dealt with the database setup that used Amazon AWS RDS (and paid 4.23$ for maintenance on the month of March)
* Worked on the design documents HCI / RCS (worked, not made)
	- Expanded the 
* Testing:
	- Worked on server side tests for Services: 100% of (Question, Lecture, User), >75% of (Poll, PollOption)
	- Worked on all entity tests for Question, Lecture, User, Poll, PollOption
	- Implemented an auto-tester for getters and setters
	- Every test class has 100% method coverage and >90% line coverage
* Dealt with the server side implementation early on of the server entities according to the MCV principle 
	- Division in controllers/service/repos
* Worked on the hide button, delete button and the corresponding events 
* Implemented a dropdown with the asnwered questions in both frontend and backend
* Implemented the sort of questions by hot, new, top
* Made the corresponding FXML changes for the features mentioned above 

# Yue Chen 

### Organisation 
* **Write and update GITLAB issues** on a weekly basis 
	- Many weekly gitlab issues for the Sprint were written and edited by me 
		- Such as but not limited to week 4, week 5, week 6, week 7 
	- Edited gitlab issues so everything is up to standards and accurately reflects implementation details 
* **Divide** the **task distribution** for the **Design Document**
* **Responsible** for dividing the issues so that our group stays ahead
* **Merged multiple branches** on Mondays and connect the features (with Alex during Week 5 + Week 6)
* **Organise** the group so that we had the MUST HAVES by week 6 and the SHOULDS by week 7 

### Documentation
* **Write** first draft of the **Backlog**
* **Design Document**: 
	- Add paragraphs about how our application satisifies the 10 heuristics 
	- Fix typos 

### Implementation 
**FRONTEND + BACKEND** 
* **Ask** a Question for STUDENT.
	- I deprecated previous implementations. I redid them. Previous ones were incomplete. 
* **Upvote** a Question for STUDENT + MODERATOR.
* **Edit** a Question for STUDENT + MODERATOR.
* **Display** all Questions in a Lecture for STUDENT + MODERATOR.
* **Implement fundamental structure** of Student and Moderator Controller classes -> QUESTION GRID is still used. 

**FRONTEND**
* **Connect** the Join Lecture Screen to the corresponding moderator/student screen.
* **Display** Lecture information onto the moderator/student screen.
* **Leave** Button for the moderator/student screen.
* **Introduced** the use of ActionEvents, which all subsequent buttons built on. 
* **Enforced** unique User Alias constraint.
* **Write** about how UI elements would be tested 

**BACKEND**
* **Create** the database schema for the application (with Teo and Alex).
* **Implement** Poll Option and User services, controllers, repositories.
* **Implement** JPA repository tests Question, User, Lecture (see week 4's merge with master), more were added later. 
* **Implement** MockMVC Tests for Lecture, Question, and User - **100% coverage**.
* **Index** the database for Lecture, Question, and User attributes. 


