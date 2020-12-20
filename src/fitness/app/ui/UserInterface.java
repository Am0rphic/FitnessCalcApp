package fitness.app.ui;
import fitness.app.FitnessCalculations;
import fitness.app.domain.Activity;
import fitness.app.domain.User;
import fitness.app.service.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
	private Scanner scan = new Scanner(System.in);
	private UserService userService = new UserService();
	private ActivityService activityService = new ActivityService();
	private User currentUser;
	private ArrayList<Activity> activityList = new ArrayList<>();
	private FitnessCalculations fitnessCalc;
	
	public UserInterface(Scanner scan) {
		this.scan=scan;
	}
	
	public void launch() {
		while (true) {	
			startup(); 
			firstMenu();
		}	
	}
	public void shutdown() {
			System.exit(0);
	}
	public void startup() { 
		System.out.println("Welcome! Are you an existing user? Y/N (type X to exit)...");
		while (true) {
			String input = scan.nextLine();
			if (input.equals("Y")) {
				login();
				break;
			}	else if (input.equals("N")) {
				signup();
				break;
			}	else if (input.equals("X")) {
				shutdown();
			}	else {
				System.out.println("Invalid unput! Are you an existing user? Y/N...");
				continue;
			}
		}
	}
	public void login() {
		System.out.println("Input user id...");
		int id = Integer.valueOf(scan.nextLine());
		currentUser = userService.getUser(id);
		currentUser.setId(id);
		fitnessCalc = new FitnessCalculations(currentUser);
	}
	public void signup() {
		System.out.println("Input name...");
		String name = scan.nextLine();
		System.out.println("Input weight...");
		byte weight = Byte.valueOf(scan.nextLine());
		System.out.println("Input height...");
		short height = Short.valueOf(scan.nextLine());
		User newUser = new User(name, weight, height);
		newUser.setId(userService.getLastUserId()+1);
		userService.addNewUser(newUser);
		currentUser=newUser;
		fitnessCalc = new FitnessCalculations(currentUser);
	}
	
	public void firstMenu() {
		System.out.printf("Welcome %s%n", currentUser.getName());
		while (true) {
			
			System.out.println("List of commands:");
			listFirstMenuCommands();
			String input = scan.nextLine();
			commands(input);
		}
	}
	public void listFirstMenuCommands() {
		System.out.println("1 - add new data (current weight)");
		System.out.println("2 - calculate daily energy expenditure (requires proper activity input)");
		System.out.println("3 - create weight change goal (for proper results calculate 2. first)");
		System.out.println("4 - fetch progress"); 
		System.out.println("5 - log out");
	}
	public void commands(String input) {

		if (input.equals("1")) {
			commandOne();
		}
		if (input.equals("2")) {
			commandTwo();
		}
		if (input.equals("3")) {
			commandThree();
		}
		if (input.equals("4")) {
			commandFour();
		}
		if (input.equals("5")) {
			commandFive();
		}
		if (input.equals("X")) {
			shutdown();
		}
	}
	public void commandOne() {
		System.out.println("Input current weight...");
		byte weight = Byte.valueOf(scan.nextLine());
		userService.addUserWeight(currentUser.getId(), weight);
		currentUser.setUserWeight(weight);
		System.out.println("Successfully added record to database");
	}
	public void commandTwo() {
		while (true) {
			System.out.println("Input activities (type DONE to calculate)");
			System.out.println("Activity id?: ");
			String input = scan.nextLine();
			if (input.equals("DONE")) {
				break;
			}
			int id = Integer.valueOf(input);
			System.out.println("Time per week doing this activity?:");	
			short time = Short.valueOf(scan.nextLine());
			activityList.add(activityService.getActivity(id, time));
		}
		int activityCalories=0;
		for (Activity a: activityList) {
			activityCalories+=fitnessCalc.getActivityCalories(a);
		}
		currentUser.setTDEE(fitnessCalc.getTDEE());
		System.out.printf("Your current Total Daily Energy Expenditure is: %d%n", currentUser.getTDEE());
		if (!fitnessCalc.isExercisingEnough()) {
			System.out.println("You are not getting enough exercise!");
		}
	}
	public void commandThree() {
		System.out.println("Input target weight: ");
		byte targetWeight = Byte.valueOf(scan.nextLine());
		System.out.println("Input time to achieve goal (in weeks): ");
		short timeframe = Short.valueOf(scan.nextLine());
		int calsToGoal = fitnessCalc.getCalsToGoal(timeframe, targetWeight);
		System.out.println("Your current maintenance level daily calorie intake is:" + fitnessCalc.getTDEE());
		if (calsToGoal>=0) {
			System.out.printf("To gain %d kilograms in %d weeks, you need to eat %d more calories per day\n", targetWeight-currentUser.getWeight(), timeframe, calsToGoal);
		} else {
			System.out.printf("To lose %d kilograms in %d weeks, you need to eat %d less calories per day\n", currentUser.getWeight()-targetWeight, timeframe, -calsToGoal);
		};
	}
	public void commandFour() {
		userService.loadUserWeightRecords(currentUser.getId());
		System.out.printf("Over the course of %d days your weight has changed by %d", userService.getTimePeriod(), userService.getWeightDifference());
	}
	public void commandFive() {
		startup(); //for some unexplained reason executes commandFour() no matter what (be it startup() or this.launch() with resetting variables
	}
}

