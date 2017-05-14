package application.models.monsters.normal;

import java.util.HashMap;
import java.util.Map;

import application.models.monsters.MonsterImpl;

public class Skeleton extends MonsterImpl {
	
	private static final int DAMAGE = 4;
	private static final String REWARD_TEXT = "You gain 1 XP!";
	private static final Map<String, Integer> REWARD = new HashMap<String, Integer>(){{
	    put("experience", 1);
	}};

	public Skeleton(int health) {
		super(health, DAMAGE, REWARD, REWARD_TEXT);
		
	}

}
