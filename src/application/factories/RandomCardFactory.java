package application.factories;

import java.util.Random;

import application.core.GameEngine;
import application.interfaces.Card;

public class RandomCardFactory {

	public static Card create(GameEngine gameEngine) {
		Card card = null;
		Random random = new Random();

		do {
			int randCardIndex = random.nextInt(6); 				// excl. boss monster card												
			card = gameEngine.getCardByIndex(randCardIndex);
		} while (card.isShown());

		card.setShown(true);

		return card;
	}
}
