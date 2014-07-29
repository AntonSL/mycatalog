package troy.consolemenu;

import java.util.List;
import java.util.ArrayList;

public class MenuData {

	private String question = "Empty question"; 
	private List<String> userAnswers=new ArrayList<>();
	private List<MenuAction> actions=new ArrayList<>();
	
	public MenuData(String question){
		this.question = question;
	}
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	
	public String getUserAnswer(int index) {
		return this.userAnswers.get(index);
	}

	public void addAnswerAndAction(String userAnswer, MenuAction action) {
		this.userAnswers.add(userAnswer);
		this.actions.add(action);
	}

	public MenuAction getAction(int index) {
		return this.actions.get(index);
	}
		
	public List<String> getUserAnswers() {
		return userAnswers;
	}

	public void setUserAnswers(List<String> userAnswers) {
		this.userAnswers = userAnswers;
	}

	public List<MenuAction> getActions() {
		return actions;
	}
	
	public void setActions(List<String> userAnswers, List<MenuAction> actions) throws Exception {
		if(userAnswers.size()!=actions.size()) throw new Exception("Unequal number of answers and actions");
		this.userAnswers = userAnswers;
		this.actions = actions;
	}
	
	

	
	
	
}
