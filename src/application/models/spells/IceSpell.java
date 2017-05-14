package application.models.spells;

import application.core.GameEngine;
import application.models.enums.SpellType;

public class IceSpell extends SpellImpl{

	public IceSpell() {
		super(SpellType.ICE);
		
	}

	@Override
	public void activate(GameEngine gameEngine) {
		gameEngine.setIceSpellActivated(true);
	}
	
}
