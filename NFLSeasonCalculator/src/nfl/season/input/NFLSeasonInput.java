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
		int returnInt = -1;
		boolean inputError = true;
		while (inputError) {
			if (scanner.hasNextInt()) {
				returnInt = scanner.nextInt();
				if (scanner.hasNextLine()) {
					scanner.nextLine();
				}
				inputError = false;
			} else {
				out.println(message);
				scanner.nextLine();
				continue;
			}
		}
		return returnInt;
	}

	public String askForString(String message) {
		out.println(message);
		return scanner.nextLine();
	}
	
}
