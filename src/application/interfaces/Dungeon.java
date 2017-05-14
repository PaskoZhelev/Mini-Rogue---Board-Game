package application.interfaces;

public interface Dungeon {
	
	public int getLevel();
	
	public int getDungeonArea();
	
	public void setDungeonArea(int num);
	
	public void goToTheNextAreaAndLevel();
}
