package troy.mycatalog;

import java.sql.SQLException;
import java.util.*;

import troy.consolemenu.MultiAnswerMenu;
import troy.consolemenu.MenuAction;
import troy.consolemenu.MenuData;
import troy.consolemenu.SingleAnswerMenu;
/*import troy.jdbc.ConnectionFactoryFactory;
import troy.jdbc.User;
import troy.jdbc.UserDao;
import troy.jdbc.UserDaoClass;*/
import troy.jdbc.*;


public class MainClass {
	
	private static UserDao userDao=null;
	private static User user=null;
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String... args){
		try {
			ConnectionFactoryFactory.setFactoryType(ConnectionFactoryFactory.FactoryTypes.NATIVE_JDBC);
			MainClass.userDao = new UserDaoClass();
		} catch (SQLException e) {
			System.out.println("Can't connect to specified DB");
			System.exit(0);
		}	
		
		MultiAnswerMenu menu = buildMenu();
		boolean goodAnswer = false;
		while(goodAnswer==false){
			menu.showQuestion();
			try {
				menu.readAnswerAndAct();
				goodAnswer=true;
			} catch (Exception e) {
				System.out.println(e.getMessage()+" Try again.");
			}
		}
		
		if(user!=null){
			System.out.print("Hi, "+user.getLogin()+"!  ");
			System.out.println("Your id is: "+user.getId()+".");
		}
		
		System.out.println("END");
		
	}
	
	/**
	 * 
	 * @return
	 */
	private static MultiAnswerMenu buildMenu(){
		String menuQuestion="Hi, Guest! Do you whant to\n"
				+ "login(L), register(R), add list of Users(U),\n"
				+ "get all users(G), delete one user(D) or quit(Q)?: ";
		MenuData menuData = new MenuData(menuQuestion);
        
		menuData.addAnswerAndAction("L", new MenuAction(){
			public void execute(){
				SingleAnswerMenu menu = new SingleAnswerMenu("Enter your login: ");
				menu.showQuestion();
				String login = menu.getAnswer();
				menu.setQuestion("Enter pasword: ");
				menu.showQuestion();
				String password = menu.getAnswer();
				
				try {
					MainClass.user=userDao.login(login, password);
				} catch (DBAccessException e) {
					System.out.println("No such user, bye!");
					System.exit(0);
				}
			}//execute	
		});//addAction
		

		menuData.addAnswerAndAction("R", new MenuAction(){
			public void execute(){
				SingleAnswerMenu menu = new SingleAnswerMenu("Enter your login: ");
				menu.showQuestion();
				String login = menu.getAnswer();
				menu.setQuestion("Enter pasword: ");
				menu.showQuestion();
				String password = menu.getAnswer();
				menu.setQuestion("Enter your email: ");
				menu.showQuestion();
				String email = menu.getAnswer();
				
				user=new User(login, password, email);
				int id=-1;
				try {
					id = userDao.addUser(user);
				} catch (DaoException e) {
					System.out.println("Error adding user");
				}
				if(id==-1) System.exit(0);
				user.setId(id);
			}//execute	
		});//addAction
		

		menuData.addAnswerAndAction("U", new MenuAction(){
			public void execute(){
				List<User> list = new ArrayList<User>();
				user = new User("drei", "3333", "mail@");
				list.add(new User("eins", "1111", "mail@"));
				list.add(new User("zwei", "2222", "mail@"));
				list.add(user);
				try {
					userDao.insertUsers(list);
				} catch (DaoException e) {
					System.out.println("Unable to add all users");
					e.printStackTrace();
					System.exit(0);
				}
			}//execute	
		});//addAction
		
		menuData.addAnswerAndAction("G", new MenuAction(){
			public void execute(){
				try {
					List<User> toPrint=userDao.getAllUsers();
					for(User u : toPrint){
						System.out.println(u);
					}
					System.exit(0);
				} catch (DBAccessException e) {
					System.out.println("DB error, can't get user list");
					e.printStackTrace();
				}
			}//execute	
		});//addAction
		
		menuData.addAnswerAndAction("D", new MenuAction(){
			public void execute(){
				SingleAnswerMenu menu = new SingleAnswerMenu("Enter user id: ");
				menu.showQuestion();
				int userId = Integer.parseInt(menu.getAnswer());
				try {
					userDao.delUser(userId);
					System.out.println("Done.");
					System.exit(0);
				} catch (DaoException e) {
					System.out.println("DB error, can't get such user");
				}
			}//execute	
		});//addAction
		
		menuData.addAnswerAndAction("Q", new MenuAction(){
			public void execute(){
				System.out.println("Good bye!");
				System.exit(0);
			}//execute	
		});//addAction
		
		return new MultiAnswerMenu(menuData);
				
	}//buildMenu

}//class
