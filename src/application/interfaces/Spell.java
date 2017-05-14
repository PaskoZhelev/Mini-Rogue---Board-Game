package application.interfaces;

import application.core.GameEngine;
import application.models.enums.SpellType;

public interface Spell {
	
	public SpellType getType();

	public void activate(GameEngine gameEngine);
}
