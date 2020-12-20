package fitness.app;

import fitness.app.domain.*;

public class FitnessCalculations {
	
	private short totalActivitiesCalories=0;
	private short totalTimeExercising;
	private User currentUser;
	
	public FitnessCalculations(User user) {
		currentUser=user;
	}
	
	public short getActivityCalories(Activity activity) { //get 1 activity energy for user and increment totalActivityTDEE
		short activityCals = (short) (currentUser.getWeight()*activity.getCaloriesCoefficient()*activity.getTime()/60);
		totalActivitiesCalories+= activityCals;
		totalTimeExercising+=activity.getTime(); //used to get remaining rest period
		return activityCals;
	}
	
	public short getTotalCalories() {
		return totalActivitiesCalories;
	}
	
	public short getTDEE() { //get user TDEE (go through all activities for user with getActivityCalories() first to set total calories burned daily from ex.
		if (getTotalCalories()!=0) {
			return (short) (currentUser.getRestCalsPerHour()*(24-(totalTimeExercising/60))+getTotalCalories());
		}
		return (short) currentUser.getRestCalsPerHour();
	}
	
	public int getCalsToGoal(short timeInWeeks, byte weightGoal) { //approx Calories + or - per day to earch goal in specified time
		int weightDiff =(byte) (weightGoal-currentUser.getWeight());
		return (weightDiff*1000/timeInWeeks);
	}

	public boolean isExercisingEnough() { //flavor to program to check if enough exercise, else syso warning
		if (totalTimeExercising<90) {
			return false;
		}
		return true;
	}
	
}
