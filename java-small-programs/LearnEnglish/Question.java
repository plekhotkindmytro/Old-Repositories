class Question {
	private String text;
	private String answer;
	public Question(final String questionText, final String answer) {
		this.text = questionText;
		this.answer = answer;
	}
	
	public String getText() {
		return text;
	}
	
	public String geAnswer() {
		return answer;
	}
}
