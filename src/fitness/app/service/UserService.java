package fitness.app.service;


import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import fitness.app.domain.User;

public class UserService {
	
	private String userName;
	private byte userWeight;
	private short userHeight;
	private ArrayList<Byte> weightList = new ArrayList<>();
	private ArrayList<Date> dateList = new ArrayList<>();
	private Database db = new Database("Am0rphic", "password");
	
	public User getUser(int id) {
		try {
			String sql = String.format("SELECT name, height FROM Users WHERE id=%d", id);  //id=" + id;
			
		//try innerjoin
			
			db.runQuery(sql);
			ResultSet resultSet = db.getResultSet();
			while(resultSet.next()){
				userName = resultSet.getString("name");
				userHeight= resultSet.getShort("height");
			}
		}	catch(SQLException sqlEx){
				sqlEx.printStackTrace();
		} 	catch(Exception e){
				e.printStackTrace();
		} 
		try {
			String sql = String.format("SELECT weight FROM UserStats WHERE idUser=%d ORDER BY id DESC LIMIT 1", id); //get last input userWeight in DB
			db.runQuery(sql);
			ResultSet resultSet = db.getResultSet();
			while(resultSet.next()){
				userWeight = resultSet.getByte("weight");
			}
		}	catch(SQLException sqlEx){
				sqlEx.printStackTrace();
		} 	catch(Exception e){
				e.printStackTrace();
		} 
		return new User(userName, userWeight, userHeight);
	}
	
	public int getLastUserId() {
		int id=0;
		try {
			String sql = "SELECT id FROM Users ORDER BY id DESC LIMIT 1";
			db.runQuery(sql);
			ResultSet resultSet = db.getResultSet();
			while(resultSet.next()){
				id = resultSet.getInt("id");;
			}
			db.closeResultSet();
		}	catch(SQLException sqlEx){
				sqlEx.printStackTrace();
		} 	catch(Exception e){
				e.printStackTrace();
		} 
		return id;
	}
	
	public void loadUserWeightRecords(int id) {
		try {
			String sql = String.format("SELECT * FROM UserStats WHERE idUser=%d", id);
			db.runQuery(sql);
			ResultSet resultSet = db.getResultSet();
			while(resultSet.next()){
				weightList.add(resultSet.getByte("weight"));
				dateList.add(resultSet.getDate("date"));
			}
			db.closeResultSet();
		}	catch(SQLException sqlEx){
				sqlEx.printStackTrace();
		} 	catch(Exception e){
				e.printStackTrace();
		} 
	}
	
	public byte getWeightDifference() {
		byte weightDiff = (byte) (weightList.get(0)-weightList.get(weightList.size()-1));
		return weightDiff;
	}
	public long getTimePeriod() {
		Date start = dateList.get(0);
		Date end = dateList.get(dateList.size()-1);
		return TimeUnit.DAYS.convert(Math.abs(start.getTime()-end.getTime()), TimeUnit.MILLISECONDS);
	}
	
	public void addNewUser(User user) {	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateStringTwo = LocalDate.now().format(formatter).toString();
		
		String sql1 = String.format("INSERT INTO Users (name, height) VALUES ('%s', %d)", user.getName(), user.getHeight());
		String sql2 = String.format("INSERT INTO UserStats (idUser, weight, date) VALUES (LAST_INSERT_ID(), %d, '%s')", user.getWeight(), dateStringTwo); //last_insert_id gets last auto-incremented id (in my db case user->userStats ID relation)
	
		try{
			db.runBatchQuery(sql1, sql2);
			db.closeResultSet();
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addUserWeight(int id, byte weight) {	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateStringTwo = LocalDate.now().format(formatter).toString();

		String sql = String.format("INSERT INTO UserStats (idUser, weight, date) VALUES (%d, %d, '%s')", id, weight, dateStringTwo); //STR_TO_DATE(date, %s)
		
		try{
			db.runUpdate(sql);
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
