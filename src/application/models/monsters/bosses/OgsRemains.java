package application.models.monsters.bosses;

import java.util.HashMap;
import java.util.Map;

import application.models.monsters.MonsterImpl;

public class OgsRemains extends MonsterImpl {
	
	private static final int HEALTH = 30;
	private static final int DAMAGE = 12;
	private static final String REWARD_TEXT = "Og's blood...!";
	private static final Map<String, Integer> REWARD = new HashMap<String, Integer>(){{
		put("ogsblood", 1);
	    
	}};

	public OgsRemains() {
		super(HEALTH, DAMAGE, REWARD,REWARD_TEXT);
		
	}

}
