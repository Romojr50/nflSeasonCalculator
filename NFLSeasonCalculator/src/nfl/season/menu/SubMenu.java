package nfl.season.menu;

public class SubMenu {

	protected SubMenu[] subMenus;
	
	public void launchSubMenu() {
		
	}
	
	public SubMenu getSubMenu(int optionNumber) {
		return subMenus[optionNumber - 1];
	}
	
	public void setSubMenu(SubMenu subMenu, int optionNumber) {
		subMenus[optionNumber - 1] = subMenu;
	}
	
	protected boolean isNotYesOrNoIndicator(String overwriteAnswer) {
		return !"Y".equalsIgnoreCase(overwriteAnswer) && 
				!"N".equalsIgnoreCase(overwriteAnswer);
	}
	
}
