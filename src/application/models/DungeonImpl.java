package application.models;

import application.interfaces.Dungeon;

public class DungeonImpl implements Dungeon{

	private int level;
	private int dungeonArea;
	
	public DungeonImpl() {
		this.level = 1;
		this.dungeonArea = 1;
	}
	
	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public int getDungeonArea() {
		return dungeonArea;
	}
	
	@Override
	public void setDungeonArea(int num) {
		if (num <= 14){
			this.dungeonArea = num;
		} else {
			this.dungeonArea = 14;
		}
		
		
		switch (num) {
		case 1:
		case 2:
			level = 1;
			break;
		case 3:
		case 4:
			level = 2;
			break;
		case 5:
		case 6:
		case 7:
			level = 3;
			break;
		case 8:
		case 9:
		case 10:
			level = 4;
			break;
		case 11:
		case 12:
		case 13:
		case 14:
			level = 5;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void goToTheNextAreaAndLevel() {		
		switch (this.dungeonArea) {
		case 2:
		case 4:
		case 7:
		case 10:
			level++;
			dungeonArea++;
			break;
		default:
			dungeonArea++;
			break;
		}
		
	}



}
