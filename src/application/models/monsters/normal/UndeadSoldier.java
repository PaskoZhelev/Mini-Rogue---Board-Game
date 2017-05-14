package application.models.monsters.normal;

import java.util.HashMap;
import java.util.Map;

import application.models.monsters.MonsterImpl;

public class UndeadSoldier extends MonsterImpl {
	
	private static final int DAMAGE = 2;
	private static final String REWARD_TEXT = "You gain 1 XP!";
	private static final Map<String, Integer> REWARD = new HashMap<String, Integer>(){{
	    put("experience", 1);
	}};

	public UndeadSoldier(int health) {
		super(health, DAMAGE, REWARD,REWARD_TEXT);
		
	}

}
