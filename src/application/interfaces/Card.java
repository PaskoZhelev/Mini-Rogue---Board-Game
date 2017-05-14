package application.interfaces;

import application.core.GameEngine;
import application.models.enums.CardType;
import javafx.scene.image.Image;

public interface Card {
	
	public Image getImage();
	
	public CardType getCardType();
	
	public String getFxml();
	
	public boolean isShown();
	
	public void setShown(boolean shown);
	
	
}
