package nfl.season.input;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class NFLSeasonInput {

	private final Scanner scanner;
	private final PrintStream out;

	public NFLSeasonInput(InputStream in, PrintStream out) {
		scanner = new Scanner(in);
		this.out = out;
	}

	public int askForInt(String message) {
		out.println(message);
		return scanner.nextInt();
	}

	public String askForString(String message) {
		out.println(message);
		return scanner.nextLine();
	}
	
}
