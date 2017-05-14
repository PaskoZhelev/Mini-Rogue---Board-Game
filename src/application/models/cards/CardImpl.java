package application.models.cards;

import application.core.GameEngine;
import application.interfaces.Card;
import application.models.enums.CardType;
import javafx.scene.image.Image;

public abstract class CardImpl implements Card {

	private boolean shown;
	private Image image;
	private CardType cardType;
	
	public CardImpl(Image image, CardType cardType) {
		this.shown = false;
		this.image = image;
		this.cardType = cardType;
	}
	
	@Override
	public Image getImage() {
		return image;
	}
	
	@Override
	public CardType getCardType() {
		return cardType;
	}
	
	@Override
	public boolean isShown() {
		return shown;
	}

	@Override
	public void setShown(boolean shown) {
		this.shown = shown;
	}
	
	


}
