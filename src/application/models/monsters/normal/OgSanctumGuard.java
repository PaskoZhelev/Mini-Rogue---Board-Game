package application.models.monsters.normal;

import java.util.HashMap;
import java.util.Map;

import application.models.monsters.MonsterImpl;

public class OgSanctumGuard extends MonsterImpl {
	
	private static final String REWARD_TEXT = "You gain 3 XP!";
	private static final int DAMAGE = 10;
	private static final Map<String, Integer> REWARD = new HashMap<String, Integer>(){{
	    put("experience", 3);
	}};

	public OgSanctumGuard(int health) {
		super(health, DAMAGE, REWARD, REWARD_TEXT);
		
	}

}
