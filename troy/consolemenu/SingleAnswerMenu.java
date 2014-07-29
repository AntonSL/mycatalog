package troy.consolemenu;

import java.util.Scanner;

public class SingleAnswerMenu {
	
	private String question = null;
	private MenuAction action=null;
	//private String answerConstraint;
	
	public SingleAnswerMenu(String question) {
		this.question = question;
		this.action=new MenuAction(){
			public void execute(){}
		};
	}
	
	/**
	 * 
	 */
	public void showQuestion(){
		System.out.print(this.question);
	}
	
	/**
	 * 
	 */
	public void setQuestion(String question){
		this.question=question;
	}
	
	/**
	 * 
	 */
	public String getAnswer(){
		Scanner in = new Scanner(System.in);
		String answerFromConsole = in.next();
		//in.close();		
		return answerFromConsole;
		
	}
	
	/**
	 * 
	 */
	public void setAction(MenuAction action){
		this.action=action;
		
	}

	/**
	 * 
	 */
	public String getAnswerAndAct(){
		Scanner in = new Scanner(System.in);
		String answerFromConsole = in.next();
		//in.close();		
		this.action.execute();
		return answerFromConsole;
		
	}
	

}
