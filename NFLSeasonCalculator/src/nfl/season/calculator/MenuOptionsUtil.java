package nfl.season.calculator;

public class MenuOptionsUtil {

	public static final String MENU_INTRO = 
			"Please enter in an integer corresponding to one of the following:\n";
	
	public static <T extends MenuOptions> String createMenuMessage(Class<T> optionsEnum) {
		StringBuilder menuMessage = new StringBuilder();
		menuMessage.append(MENU_INTRO);
		for (T option : optionsEnum.getEnumConstants()) {
			menuMessage.append(option.getOptionNumber() + ". " 
					+ option.getOptionDescription() + "\n");
		}
			menuMessage.setLength(menuMessage.length() - 1);
		return menuMessage.toString();
	}
	
}
