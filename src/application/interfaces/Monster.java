package application.interfaces;

import java.util.Map;

public interface Monster {
	
	public int getHealth();
	
	public int getDamage();
	
	public Map<String, Integer> getReward();
	
	public void setHealth(int health);
	
	public String getRewardText();
}
