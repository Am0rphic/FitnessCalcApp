package fitness.app.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import fitness.app.domain.Activity;

public class ActivityService {
	
	private String activityName;
	private double activityCalCoefficient;
	private Database db = new Database("Am0rphic", "password");
	
	public Activity getActivity(int id, short time) {
		try {
			String sql = String.format("SELECT name, calCoefficient FROM Activities WHERE id=%d", id);
			db.runQuery(sql);
			ResultSet resultSet = db.getResultSet();
			while(resultSet.next()){
				activityName = resultSet.getString("name");
				activityCalCoefficient = resultSet.getDouble("calCoefficient");
			}
			db.closeResultSet();
		}	catch(SQLException sqlEx){
				sqlEx.printStackTrace();
		} 	catch(Exception e){
				e.printStackTrace();
		} 
		return new Activity(activityName, activityCalCoefficient, time);	
	}
	
	public void addNewActivity(Activity activity) {
		String sql = String.format("INSERT INTO Activities (name, calCoefficient) VALUES (%s, %f)", activity.getName(), activity.getCaloriesCoefficient());
		try{
			db.runQuery(sql);
			db.closeResultSet();
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
