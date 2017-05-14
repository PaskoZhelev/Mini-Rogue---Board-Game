package application.models.monsters.bosses;

import java.util.HashMap;
import java.util.Map;

import application.models.monsters.MonsterImpl;

public class SkeletonLord extends MonsterImpl {
	
	private static final int HEALTH = 15;
	private static final int DAMAGE = 5;
	private static final String REWARD_TEXT = "You gain 3 XP, 2 Gold and 1 item!";
	private static final Map<String, Integer> REWARD = new HashMap<String, Integer>(){{
		put("gold", 2);
	    put("experience", 3);
	    put("item", 1);
	}};

	public SkeletonLord() {
		super(HEALTH, DAMAGE, REWARD,REWARD_TEXT);
		
	}

}
