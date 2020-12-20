package fitness.app.domain;

public class User {
	
	private String userName; //First+Last name for simplicity's sake
	private byte userWeight; //in KG, limit 255
	private short userHeight; //limit 255
	private int userId; //take from DB? - YES, NEED TO HAVE IT UP AND RUNNING AT START OF UI
	private float userBMI; //f(weight,height) //18.5<NORMAL>25.0
	private short userRestCalPerHour; // hourly cal burned at rest (1.0 coefficient)
	private short tdee;
	
	public User (String name, byte weight, short height) {
		userName=name;
		userWeight=weight;
		userHeight=height;
		userBMI = userWeight/(userHeight*userHeight);
		userRestCalPerHour=(short) (weight*1.3);
	}

	public void setUserWeight(byte userWeight) { //setting weight auto-updates BMI and restCalsPerHour
		this.userWeight = userWeight;
		this.userBMI=this.userWeight/(userHeight*userHeight);
		this.userRestCalPerHour = (short) (userWeight*1.3);
	}

	public String getName() {
		return userName;
	}

	public byte getWeight() {
		return userWeight;
	}

	public short getHeight() {
		return userHeight;
	}

	public int getId() {
		return userId;
	}
	public void setId(int id) {
		userId=id;
	}

	public float getBMI() {
		this.userBMI=this.userWeight/(userHeight*userHeight);
		return userBMI;
	}

	public int getRestCalsPerHour() {
		this.userRestCalPerHour = (short) (userWeight*1.3);
		return userRestCalPerHour;
	}
	
	public short getTDEE() {
		return tdee;
	}
	public void setTDEE(short tdee) {
		this.tdee=tdee;
	}

	@Override
	public String toString() {
		return "User id=" + userId + " [userName=" + userName + ", userWeight=" + userWeight + ", userHeight=" + userHeight + ", userBMI=" + userBMI + "]";
	}

}
