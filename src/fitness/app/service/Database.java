package fitness.app.service;
import java.sql.*;

public class Database {
	private String user="Am0rphic";
	private String pass="password";
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/fitnesscalcappdb";
	private ResultSet resultSet=null;
	private Connection connection=null;
	private Statement statement=null;
	
	public Database(String username, String password) {
		this.user=username;
		this.pass=password;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void runQuery(String sql) throws SQLException, Exception {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url,getUser(), getPass());
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);		
		}	catch(SQLException sqlEx){
				sqlEx.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public void runUpdate(String sql) throws SQLException, Exception {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url,getUser(), getPass());
			statement = connection.createStatement();
			statement.executeUpdate(sql);		
		}	catch(SQLException sqlEx){
				sqlEx.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public void runBatchQuery(String... args) throws SQLException, Exception { //cant select
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url,getUser(), getPass());
			connection.setAutoCommit(false);
			statement = connection.createStatement();			
			for (String arg: args) {
				statement.addBatch(arg);
			}
			
			statement.executeBatch();
			connection.commit();		
		}	catch(SQLException sqlEx){
				sqlEx.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public ResultSet getResultSet() {
		return resultSet;
	}
	public void closeResultSet() {
		try {
			resultSet.close();
			statement.close();
			connection.close();
		}
		catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
	}
}

