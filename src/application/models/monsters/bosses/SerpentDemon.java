package application.models.monsters.bosses;

import java.util.HashMap;
import java.util.Map;

import application.models.monsters.MonsterImpl;

public class SerpentDemon extends MonsterImpl {
	
	private static final int HEALTH = 25;
	private static final int DAMAGE = 9;
	private static final String REWARD_TEXT = "You gain 5 XP, 3 Gold and 1 item!";
	private static final Map<String, Integer> REWARD = new HashMap<String, Integer>(){{
		put("gold", 3);
	    put("experience", 5);
	    put("item", 1);
	}};

	public SerpentDemon() {
		super(HEALTH, DAMAGE, REWARD,REWARD_TEXT);
		
	}

}
