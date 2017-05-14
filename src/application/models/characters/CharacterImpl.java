package application.models.characters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.exceptions.GameException;
import application.interfaces.Character;
import application.interfaces.Spell;

public class CharacterImpl implements Character {
	
	private int experiencePoints;
	private int armor;
	private int health;
	private int gold;
	private int food;
	private int rank;
	private Spell spellOne;
	private Spell spellTwo;
	private boolean dead;
	private boolean monsterDefeated;
	
	
	
	public CharacterImpl(int armor, int health, int gold, int food) {
		this.armor = armor;
		this.health = health;
		this.gold = gold;
		this.food = food;
		this.experiencePoints = 0;
		this.rank = 1;
		spellOne = null;
		spellTwo = null;
		this.dead = false;
		this.monsterDefeated = false;
	}

	@Override
	public int getExperiencePoints() {
		return experiencePoints;
	}

	@Override
	public int getArmor() {
		return armor;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public int getGold() {
		return gold;
	}

	@Override
	public int getFood() {
		return food;
	}
	
	@Override
	public int getRank() {
		return rank;
	}

	@Override
	public boolean isDead() {
		return dead;
	}

	@Override
	public boolean isMonsterDefeated() {
		return monsterDefeated;
	}

	@Override
	public Spell getSpellOne() {
		return spellOne;
	}

	@Override
	public Spell getSpellTwo() {
		return spellTwo;
	}

	@Override
	public void addSpell(Spell spell) {
	
		if(spellOne == null) {
			spellOne = spell;
		} else if (spellTwo == null){
			spellTwo = spell;
		} else {
			System.out.println("You cannot hold more than two spells!");
		}
	}

	@Override
	public void removeSpellOne() {
		spellOne = null;
	}
	
	@Override
	public void removeSpellTwo() {
		spellTwo = null;
	}

	public void setExperiencePoints(int experiencePoints) {
		if (experiencePoints <= 36) {
			this.experiencePoints = experiencePoints;
		} else {
			this.experiencePoints = 36;
		}
		
		if (this.experiencePoints < 6) {
			this.rank = 1;
		} else if ((this.experiencePoints >= 6) && (this.experiencePoints <= 17)) {
			this.rank = 2;
		} else if ((this.experiencePoints >= 18) && (this.experiencePoints <= 35)) {
			this.rank = 3;
		} else if ((this.experiencePoints >= 36)) {
			this.rank = 4;
		}
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	@Override
	public void setArmor(int armor) {
		if (armor > 5) {
			this.armor = 5;
		} else {
			this.armor = armor;
		}
	}

	@Override
	public void setHealth(int health) {
		if (health > 20) {
			this.health = 20;
		} else {
			this.health = health;
		}
		
	}

	@Override
	public void setGold(int gold) {
		if (gold > 20) {
			this.gold = 20;
		} else {
			this.gold = gold;
		}
	}

	@Override
	public void setFood(int food) {
		if (food > 6) {
			this.food = 6;
		} else {
			this.food = food;
		}
	}

	@Override
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	@Override
	public void setMonsterDefeated(boolean monsterDefeated) {
		this.monsterDefeated = monsterDefeated;
	}

	
}
