package fitness.app.service;


import java.sql.ResultSet;
import java.sql.SQLException;
import fitness.app.domain.Meal;

public class MealService {
		
	private String mealName;
	private short mealCalories;
	private Database db = new Database("Am0rphic", "password");
	
	public Meal getMeal(int id) {
		try {
			String sql = String.format("SELECT name, calories FROM Meals WHERE id=%d", id);
			db.runQuery(sql);
			ResultSet resultSet = db.getResultSet();
			while(resultSet.next()){
				mealName = resultSet.getString("name");
				mealCalories = resultSet.getShort("calories");
			}
			db.closeResultSet();
		}	catch(SQLException sqlEx){
				sqlEx.printStackTrace();
		} 	catch(Exception e){
				e.printStackTrace();
		} 
		return new Meal(mealName, mealCalories);
	}
	
	public void addNewMeal(Meal meal) {
		String sql = String.format("INSERT INTO Meals (name, calories) VALUES (%s, %d)", meal.getName(), meal.getCalories());
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
