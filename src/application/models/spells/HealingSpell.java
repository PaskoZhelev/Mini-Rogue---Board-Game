package application.models.spells;

import application.core.GameEngine;
import application.interfaces.Character;
import application.models.enums.SpellType;

public class HealingSpell extends SpellImpl {

	public HealingSpell() {
		super(SpellType.HEALING);
		
	}

	@Override
	public void activate(GameEngine gameEngine) {
		Character character = gameEngine.getCharacter();
		character.setHealth(character.getHealth() + 8);
	}

}
