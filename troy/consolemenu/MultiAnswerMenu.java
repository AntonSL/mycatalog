package troy.consolemenu;

import java.util.*;

public class MultiAnswerMenu {
	
	private MenuData menuData=null;

	/**
	 * 
	 * @param menuData
	 */
	public MultiAnswerMenu(MenuData menuData) {
		this.menuData = menuData;
	}
	
	/**
	 * 
	 */
	public void showQuestion(){
		System.out.print(this.menuData.getQuestion());
	}
	
	/**
	 * 
	 */
	public void readAnswerAndAct() throws Exception{
		Scanner in = new Scanner(System.in);
		String answerFromConsole = in.next();
		//in.close();

		List<String> allowedAnswers = this.menuData.getUserAnswers();
		if(allowedAnswers.contains(answerFromConsole)){
		    for(int i=0; i<allowedAnswers.size(); i++){
		    	if(answerFromConsole.equals(allowedAnswers.get(i))){
		    		this.menuData.getAction(i).execute();
		    	    break;
		    	}    
			}//for
		}//if
		else
			throw new Exception("No such option.");
	    
		
	}
	
	/**
	 * @param menuData the menuData to set
	 */
	public void setMenuData(MenuData menuData) {
		this.menuData = menuData;
	}



}
