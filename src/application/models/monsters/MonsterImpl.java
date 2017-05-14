package application.models.monsters;

import java.util.Map;

import application.interfaces.Monster;

public abstract class MonsterImpl implements Monster {

	private int health;
	private int damage;
	private Map<String, Integer> reward;
	private String rewardText;

	public MonsterImpl(int health, int damage, Map<String, Integer> reward, String rewardText) {
		this.health = health;
		this.damage = damage;
		this.reward = reward;
		this.rewardText = rewardText;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public Map<String, Integer> getReward() {
		return reward;
	}

	@Override
	public String getRewardText() {
		return rewardText;
	}
	
}
