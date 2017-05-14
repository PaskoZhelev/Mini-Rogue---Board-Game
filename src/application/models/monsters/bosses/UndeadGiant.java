package application.models.monsters.bosses;

import java.util.HashMap;
import java.util.Map;

import application.models.monsters.MonsterImpl;

public class UndeadGiant extends MonsterImpl {
	
	private static final int HEALTH = 10;
	private static final int DAMAGE = 3;
	private static final String REWARD_TEXT = "You gain 2 XP, 2 Gold and 1 item!";
	private static final Map<String, Integer> REWARD = new HashMap<String, Integer>(){{
		put("gold", 2);
	    put("experience", 2);
	    put("item", 1);
	}};

	public UndeadGiant() {
		super(HEALTH, DAMAGE, REWARD,REWARD_TEXT);
		
	}

}
