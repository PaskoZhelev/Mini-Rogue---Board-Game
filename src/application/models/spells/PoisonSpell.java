package application.models.spells;

import application.core.GameEngine;
import application.models.enums.SpellType;

public class PoisonSpell extends SpellImpl {

	public PoisonSpell() {
		super(SpellType.POISON);
		
	}

	@Override
	public void activate(GameEngine gameEngine) {
		gameEngine.setPoisonSpellActivated(true);
	}
	
}
