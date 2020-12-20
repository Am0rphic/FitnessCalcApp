package fitness.app.domain;

public class Activity {

	private String activityName;
	private double activityCalsCoefficient;
	private short activityTime;
	
//	public Activity(String name, short calories) {
//		loadActivities();
//		activityName=name;
//		activityCals=calories;
//		activityId=(short) (count+1);
//		activityList.put(activityId, new HashMap<String, Short>() {
//			{
//				put(name, calories);
//			}
//		});
//		saveActivity(activityId, activityName, activityCals);
//	}
	
//	public String getName(short id) {
//		loadActivities();
//		for (short i: activityList.keySet()) { //go through all ID
//			if (i==id) {
//				for (String j: activityList.get(i).keySet()) { //this keyset has ONE element so we return the activity NAME
//					return j;
//				}
//			}
//		}
//		return null;
//	}
//	public short getCals(short id) {
//		loadActivities();
//		for (short i: activityList.keySet()) { //go through all ID
//			if (i==id) {
//				for (String j: activityList.get(i).keySet()) { //this keyset has ONE element so we return the activity NAME
//					return activityList.get(id).get(j);
//				}
//			}
//		}
//		return 0;
//	}
	
//	public HashMap<Short, HashMap<String, Short>> loadActivities() {
//		if (activityList.isEmpty()) {
//		//TODO get DB table Activities, make a stream, populate HashMap<ID, HashMap<Name, Cals>>!!!  --- SET COUNT TO LAST ID
//		}
//		return activityList;
//	}
//	
//	public void saveActivity(short id, String name, short cals) {
//		//TODO save activity to DB
//	}
	
	public Activity(String name, double calCoefficient, short time) {
		activityName=name;
		activityCalsCoefficient=calCoefficient;
		activityTime=time;
	}
	
	public String getName() {
		return activityName;
	}
	
	public double getCaloriesCoefficient() {
		return activityCalsCoefficient;
	}
	
	public short getTime() {
		return activityTime;
	}
	

}


