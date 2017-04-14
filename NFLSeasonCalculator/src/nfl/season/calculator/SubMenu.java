package nfl.season.calculator;

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
	
}
