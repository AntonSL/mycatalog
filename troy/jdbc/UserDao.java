package troy.jdbc;

import java.util.List;


public interface UserDao {
	
	/**
	 * check if there's such user in the DB
	 * @param login
	 * @param password
	 * @return User object or "null" if there's no such user
	 */
	public User login(String login, String password) throws DBAccessException;
	
	/**
	 * Adds new user to DB
	 * @param user
	 * @return id of new user in the DB
	 */
	public int addUser(User user) throws DBAccessException, DuplicateLoginException, DuplicateMailException;
	
	/**
	 * Adds multiple users
	 * @param toAdd
	 * @return
	 */
	public void insertUsers(List<User> toAdd) throws DBAccessException, DuplicateLoginException, DuplicateMailException;
	
	/**
	 * @return List of all users
	 * @throws DBAccessException if there's problem
	 */
	public List<User> getAllUsers() throws DBAccessException;
	
	/**
	 * @param id of user to delete
	 * @throws DBAccessException, NoSuchUserException
	 */
	public void delUser(int id) throws DBAccessException, NoSuchUserException;
	
	/**
	 * @param login of user to delete
	 * @throws DBAccessException, NoSuchUserException
	 */
	public void delUser(String login) throws DBAccessException, NoSuchUserException;
	
}
