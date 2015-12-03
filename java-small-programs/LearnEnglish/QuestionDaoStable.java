class QuestionDaoStable implements QuestionDao {
	private static final Question[] QUESTIONS;
	
	static {
		QUESTIONS = new Question[2];
		
			QUESTIONS[0] = new Question("How old are you?", "24");
			QUESTIONS[1] = new Question("2+2?", "4");
		
	} 
	
	public Question getQuestion() {
		int randomQuestionIndex = (int)(Math.random() * (QUESTIONS.length));
		return QUESTIONS[randomQuestionIndex];
	}
	
}
