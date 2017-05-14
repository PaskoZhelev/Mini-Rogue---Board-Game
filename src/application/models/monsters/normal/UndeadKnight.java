package application.models.monsters.normal;

import java.util.HashMap;
import java.util.Map;

import application.models.monsters.MonsterImpl;

public class UndeadKnight extends MonsterImpl {
	
	private static final int DAMAGE = 6;
	private static final String REWARD_TEXT = "You gain 2 XP!";
	private static final Map<String, Integer> REWARD = new HashMap<String, Integer>(){{
	    put("experience", 2);
	}};

	public UndeadKnight(int health) {
		super(health, DAMAGE, REWARD,REWARD_TEXT);
		
	}

}
