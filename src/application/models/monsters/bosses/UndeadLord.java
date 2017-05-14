package application.models.monsters.bosses;

import java.util.HashMap;
import java.util.Map;

import application.models.monsters.MonsterImpl;

public class UndeadLord extends MonsterImpl {
	
	private static final int HEALTH = 20;
	private static final int DAMAGE = 7;
	private static final String REWARD_TEXT = "You gain 4 XP, 3 Gold and 1 item!";
	private static final Map<String, Integer> REWARD = new HashMap<String, Integer>(){{
		put("gold", 3);
	    put("experience", 4);
	    put("item", 1);
	}};

	public UndeadLord() {
		super(HEALTH, DAMAGE, REWARD,REWARD_TEXT);
		
	}

}
