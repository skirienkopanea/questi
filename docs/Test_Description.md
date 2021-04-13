# Description of how we would test Front - End features 

**Start a Lecture directly**
1. Click on the Start lecture button.
2. Pop-up should be displayed asking for Lecture name and then for User name. 
3. Click confirm.
4. User, who has role moderator, is directed to a lecture.
5. Corresponding join codes for other moderators/students are displayed on the screen. 

**Schedule a new lecture**
1. User is directed to a scheduling page.
2. User is prompted for necessary information, such as start time and lecture name.
3. Upon clicking confirm, user receives two join codes, one for user and one for moderator. 
4. Lecture does not start until time indicated or until the user clicks "Start" as moderator. 

**Help page**
1. User is directed to a help page. 
2. User can go back to home screen by clicking "Back".

**Join lecture** 
1. User fills in the corresponding code, for student/moderator. 
2. User can join lecture with chosen alias.
**Start Lecture**
1. The settings bar can be opened.
2. Moderator can start lecture directly, then all student codes will become active. 

**End lecture**
1. The settings bar can be opened.
2. Students currently in the lecture will receive a pop-up stating that lecture ended.
3. On click, the student will be directed back to the home page.
4. Moderators will be unaffected.

**Ask Questions**
1. Student can type in the Ask Question pane 
2. Student can fill in a question of length less than 200 characters
3. Student clicks "Ask" and the question will be shown on the question grid 
4. All users in this lecture sees the question as posted.

**See Questions**
1. Student sees own questions with "Edit", "Delete", "Vote" options. 
2. Student sees other student's questions with only "Vote"
3. Moderator sees all questions with "Edit", "Delete", "Hide", "Reply", "Mark as Answered", "Number of upvotes"
4. All questions within an active lecture are displayed in real time 

**Vote Question**
1. Student can upvote a question. Vote button for the question will then be disabled for that particular student. 
2. Student can only upvote a question once. 
3. Moderator sees the corresponding number of upvotes and cannot upvote any question. 

**Hide Question**
1. Moderator can hide a question. 
2. All moderators/lecturer can no longer see that question
3. Students can still see the hidden question 

**Mark as Answered Question**

1. Moderator clicks to mark a question as answered. 
2. Question turns green for a short time interval
3. Question disappears from moderator screens and then is appended to the list of answered questions in the menu bar on the right 

**Delete Question**
1. Moderator can delete any question in the lecture
2. This question will not be shown for any participant in the lecture

*As for the student* 

1. Students can delete their own questions. 
2. This question will not be shown for any participant in the lecture 

**Edit Question**
1. Student can edit their own question and Moderator can edit any question. 
2. User clicks edit.
3. An TextArea pane will be shown. 
4. User can type in the new text of the question.
5. User can click Submit and the edited version will be shown to all users 

**Reply Question**
1. Moderator can reply to questions
2. Moderator clicks reply
3. An TextArea pane will be shown. 
4. Moderator can type the reply. Near the question reply area, the moderator's alias will be appended and "[mod] is typing..." is shown 
5. Moderator clicks Submit. The reply will be displayed for all users in that lecture. 


**Sort Questions** 
1. User can click the top bar above the question grid
2. User can sort question by "VOTES", "NEW", "HOT" 
	- "VOTES", questions with most votes are shown first
	- "NEW", newest questions are shown first 
	- "HOT", newest + most votes questions are shown first. 

**Speed Poll**
1. Moderator and student can find the speed poll in the menu bar.
2. User has three option "Too quick", "Too Slow", "Just right".
3. User can click one option and the corresponding statistic is updated.
4. User can not choose more than one option. If the user changes the option, the corresponding statistics will update accordingly. 

**Multiple Choice Question Poll**
1. Moderator can create a multiple choice question.
2. Moderator is prompted to fill in the question and the individual options correspondingly. 
3. Student can select one option.
4. Moderator can close the poll.
5. Student can then no longer participate in the given poll.
6. Corresponding statistics are shown. 

**Customisation Feature**
1. Moderator clicks the Settings icon
2. Moderator can choose corresponding text size, color, and background image. 
3. Screen changes accordingly. 

**Lecture View**
1. Moderator clicks the Settings icon.
2. Moderator clicks "Toggle Lecturer View". Menu bar will be hidden. 
3. Only the questions in the lecture are shown. 

**Moderator View**
1. Moderator clicks the Settings icon.
2. Moderator clicks "Toggle Moderator View". Menu bar will be shown. 
3. Moderator now has more usability features. 

**Export Questions** 
1. Moderator clicks the Settings icon.
2. Moderator can export the list of questions in this lecture
3. Moderator can specify file format and file location
4. File should appear in the correct format and location and contain the question in that lecture 

**List of Answered Questions**
1. User opens menu bar on the right 
2. Open the list of answered questions
3. Text of the answered questions are shown in listview  

**List of participants**
1. User opens menu bar on the right 
2. Open the list of participants. 
3. Aliases of participants are shown in listview. Statistic of number of participants are shown.  
4. Aliases of users who left the lecture are removed 

**Ban User**
1. Moderator click Settings icon
2. Moderator can choose to Ban Users 
3. Moderator will receive a new window with corresponding user alias and ban button.
4. Moderator can select the user to ban.
5. Banned user will be removed from the lecture. 
6. However, since this is by alias, the user could join again with another alias. IP addresses can be too general.

**Activity Log** 
1. Moderator clicks the Settings icon.
2. Moderator can export the activity 
3. Moderator can specify the file location
4. File should appear in the correct format and contain all the 

**Rate Limit** 
1. The moderator sets rate limit
2. Students in corresponding lecture gets notified 
3. Student can only submit a new question with a restricted time interval 