package troy.jdbc;

import java.sql.*;
//import javax.sql.*;
import java.util.List;
import java.util.ArrayList;

//import javax.sql.*;

public class UserDaoClass implements UserDao {

	private ConnectionFactory connectionFactory=null;
	
	/**
	 * 
	 * @param ip
	 * @param db
	 * @param login
	 * @param pass
	 */
	public UserDaoClass() throws SQLException{
			this.connectionFactory = ConnectionFactoryFactory.getFactory();
		    getConnection(); //check dbConnection
	}

	/**
	 * 
	 */
	@Override
	public User login(String userLogin, String userPassword) throws DBAccessException{
		Statement stm=null;
		ResultSet rs=null;
		try{
			Connection conn = getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT * "
								+ "FROM users "
								+ "WHERE login = \'"+userLogin+"\' AND password = \'"+userPassword+"\'");
			return createOneUserFromRs(rs);
		}
		catch(SQLException e){
			throw new DBAccessException("Error accessing DB", e);
		}
		finally{
			JdbcUtils.close(rs);
			JdbcUtils.close(stm);
		}
	}

	
	/**
	 * 
	 */
	@Override
	public void delUser(int id) throws DBAccessException, NoSuchUserException{
		Statement stm=null;
		try{
			Connection conn = getConnection();
			stm = conn.createStatement();
			stm.executeUpdate("DELETE FROM users WHERE id = \'"+id+"\';");
			checkIfDuplicate();
		}
		catch(SQLException e){
			throw new DBAccessException("Error accessing DB", e);
		}
		finally{
			JdbcUtils.close(stm);
		}
		
	}

	
	private void checkIfDuplicate() throws NoSuchUserException {
/*		if(duplicate==true){
			throw new NoSuchUserException("Can't delete, there's no such user");
		}	*/
	}

	/**
	 * 
	 */
	@Override
	public void delUser(String login) throws DBAccessException{
		Statement stm=null;
		try {
			Connection conn = getConnection();
			stm = conn.createStatement();
			stm.executeUpdate("DELETE FROM users WHERE login = \'"+login+"\';");
		}	
		catch(SQLException e){
			throw new DBAccessException("Error accessing DB", e);
		}
		finally{
			JdbcUtils.close(stm);
		}
	}

	/**
	 * @return id of the user assigned by database
	 */
	@Override
	public int addUser(User user) throws DBAccessException {
		Statement stm=null;
		ResultSet rs = null;
		try{
			Connection conn = getConnection();
			stm = conn.createStatement();
			
			String sql = "INSERT INTO users (login, password, email, name, lastName) ";
			sql+="VALUES (";
			sql+="\'"+user.getLogin()+"\', ";
			sql+="\'"+user.getPassword()+"\', ";
			sql+="\'"+user.getEmail()+"\', ";
			sql+="\'"+user.getName()+"\', ";
			sql+="\'"+user.getLastName()+"\'";
			sql+=");";
			stm.executeUpdate(sql,  Statement.RETURN_GENERATED_KEYS);
			rs=stm.getGeneratedKeys();
			rs.next();		
			int autoGeneratedIndex = 1;
			return rs.getInt(autoGeneratedIndex);
		}
		catch(SQLException e){
			throw new DBAccessException("Error accessing DB", e);
		}
		finally{
			JdbcUtils.close(rs);
			JdbcUtils.close(stm);
		}
	}

	@Override
	public void insertUsers(List<User> usersToAdd) throws DBAccessException{
		Connection conn=null;
		PreparedStatement pstm = null;
		
		try {
			conn=getConnection();
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			String sql="INSERT INTO users (login, password, email, name, lastName) ";
			sql+="VALUES (?, ?, ?, ?, ?)";
			pstm=conn.prepareStatement(sql);
			for(User user : usersToAdd){
					pstm.setString(1, user.getLogin());
					pstm.setString(2, user.getPassword());
					pstm.setString(3, user.getEmail());
					pstm.setString(4, user.getName());
					pstm.setString(5, user.getLastName());
					pstm.addBatch();
			}
			try{
				pstm.executeBatch();
			}
			catch(BatchUpdateException e){
				conn.rollback();
				throw new BatchUpdateException();
			}
			conn.commit();
		}
		catch(SQLException e){
			throw new DBAccessException("Error accessing DB", e);
		}
		finally{
			JdbcUtils.close(pstm);
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Exception: unable to resume AutoCommit in UserDaoClass.insertUsers()");
			}
		}
	}

	@Override
	public List<User> getAllUsers() throws DBAccessException {
		Statement stm=null;
		ResultSet rs = null;	
		try{
			Connection conn=getConnection();
			stm = conn.createStatement();
			String sql = "SELECT id, login, password, name, lastName, email FROM users;";
			rs = stm.executeQuery(sql);
			return createUsersFromRs(rs);
		}
		catch(SQLException e){
			e.printStackTrace();
			throw new DBAccessException("Error accessing DB", e);		
		}
		finally{
			JdbcUtils.close(rs);
			JdbcUtils.close(stm);
		}
	}
	
	/**
	 * 
	 * @param rs
	 * @return User object, not NULL
	 * @throws SQLException
	 */
	private User createOneUserFromRs(ResultSet rs) throws SQLException {
		User toReturn=null;
		if(rs.next()){
			//id, login, password, email are not NULL in db, so don't check for NULL value
			toReturn = new User(rs.getString("login"), rs.getString("password"), rs.getString("email"));
			toReturn.setId(rs.getInt("id"));
			//name and lastname are optional, so check for NULL
			if(rs.getString("name")!=null) toReturn.setName(rs.getString("name"));
			if(rs.getString("lastName")!=null) toReturn.setLastName(rs.getString("lastName"));
		}
		//don't return null, if rs is empty better throw an exception
		if(toReturn==null) throw new SQLException();
		return toReturn;
	}		

	/**
	 * 
	 * @param rs
	 * @return List<User> which is not null and size>0 or throws exception
	 * @throws SQLException
	 */
	private List<User> createUsersFromRs(ResultSet rs) throws SQLException {
		List<User> toReturn=new ArrayList<User>();
		User freshUser=null;
		while(true){
			try {
				freshUser=createOneUserFromRs(rs);
			} 
			catch (SQLException e) {
				break;
			}
			toReturn.add(freshUser);
		};
		if(toReturn.size()==0) throw new SQLException();
		return toReturn;
	}	
	
	private Connection getConnection() throws SQLException{		
		return this.connectionFactory.getConnection();
	}
	
	
}
