package application.interfaces;


public interface Character {
	
	public int getExperiencePoints();
	
	public int getArmor();

	public int getHealth();
	
	public int getGold();
	
	public int getFood();
	
	public int getRank();
	
	public void setHealth(int health);
	
	public void setRank(int rank);
	
	public void setArmor(int armor);
	
	public void setGold(int gold);
	
	public void setFood(int food);
	
	public void setExperiencePoints(int experiencePoints);
	
	public Spell getSpellOne();
	
	public Spell getSpellTwo();
	
	public void addSpell(Spell spell);
	
	public void removeSpellOne();
	
	public void removeSpellTwo();
	
	public boolean isDead();
	
	public void setDead(boolean dead);
	
	public boolean isMonsterDefeated();
	
	public void setMonsterDefeated(boolean monsterDefeated);
}
