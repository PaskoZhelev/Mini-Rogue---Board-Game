package application.models.spells;

import application.core.GameEngine;
import application.interfaces.Spell;
import application.models.enums.SpellType;

public abstract class SpellImpl implements Spell{
	
	private SpellType type;
	
	public SpellImpl(SpellType type) {
		this.type = type;
	}	
	
	@Override
	public SpellType getType() {
		return type;
	}

	@Override
	public abstract void activate(GameEngine gameEngine);
}
