package fitness.app.domain;

public class Meal {
	private String mealName;
	private short mealCals;
	
	public Meal(String name, short calories) {
		mealName=name;
		mealCals=calories;
	}
	
	public String getName() {
		return mealName;
	}
	
	public short getCalories() {
		return mealCals;
	}
}