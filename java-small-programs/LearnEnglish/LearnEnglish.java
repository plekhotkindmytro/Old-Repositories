import java.util.Scanner;

class LearnEnglish {
	public static void main(final String [] args) {
		
		printHello();
		
		final QuestionDao questionDao = new QuestionDaoStable();
		
		String userInput;
		do {
			// ask a question
			Question question = questionDao.getQuestion();
			println(question.getText());	
			// get user answer
			Scanner scanner = new Scanner(System.in);
			userInput = scanner.next();
			
			// show result
			
			if (userInput.equals(question.geAnswer())) {
				println("Right! :)");
			} else {
				println("Wrong! :(");
			}
		} while (!quit(userInput));
	} 
	
	public static boolean quit(String userInput) {
		return userInput.equals("q") || userInput.equals("quit") 
			|| userInput.equals("exit");
	}
	private static void printHello() {
		println("************************************************************************");
		println("* Good day, dear visitor. You are in a great land of english language. *");
		println("************************************************************************");
	}
	private static void println(final String s) {
		System.out.println(s);
	}
	
	private static void print(final String s) {
		System.out.print(s);
	}
}
