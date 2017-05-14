package application.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.models.Die;

public class RollingDiceHandler {
	
	private int numberDice;
	private List<Die> dice;
	
	public RollingDiceHandler(int numberDice) {
		dice = new ArrayList<>();
		this.numberDice = numberDice;
		
		for (int i = 0; i < numberDice; i++) {
			Die newDie = new Die();			
			dice.add(newDie);
			newDie.roll();
		}
		
	}
	
	public void rollDice() {
		for (int i = 0; i < numberDice; i++) {
			dice.get(i).roll();			
		}
	}
	
	public List<Die> getDice() {
		return Collections.unmodifiableList(dice);
	}
}
