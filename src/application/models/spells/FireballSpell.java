package application.models.spells;

import application.core.GameEngine;
import application.interfaces.Monster;
import application.models.enums.SpellType;

public class FireballSpell extends SpellImpl {
	
	public FireballSpell() {
		super(SpellType.FIREBALL);
		
	}

	@Override
	public void activate(GameEngine gameEngine) {
		Monster currentMonster = gameEngine.getMonster();
		currentMonster.setHealth(currentMonster.getHealth() - 8);
	}

	



}
