package application.models.monsters.normal;

import java.util.HashMap;
import java.util.Map;

import application.interfaces.Dungeon;
import application.models.Die;
import application.models.monsters.MonsterImpl;

public class EventMonster extends MonsterImpl {
	
	private static final String REWARD_TEXT = "You gain 2 XP!";
	private static final Map<String, Integer> REWARD = new HashMap<String, Integer>(){{
	    put("experience", 2);
	}};
	
	public EventMonster(Dungeon dungeon, int health) {		
		super(health,dungeon.getLevel() * 2 , REWARD, REWARD_TEXT);	
	}
	
	
}
