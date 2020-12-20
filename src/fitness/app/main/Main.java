package fitness.app.main;
import fitness.app.ui.UserInterface;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		UserInterface ui = new UserInterface(scan);
		ui.launch();
	}
}
