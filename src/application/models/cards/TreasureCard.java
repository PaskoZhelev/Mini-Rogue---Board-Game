package application.models.cards;

import application.core.GameEngine;
import application.models.enums.CardType;
import javafx.scene.image.Image;

public class TreasureCard extends CardImpl {

	private static final Image IMAGE = new Image("/images/cards/Treasure.jpg");
	private final String FXML = "/views/TreasureCardView.fxml";
	
	public TreasureCard() {
		super(IMAGE,CardType.TREASURE);	
	}



	@Override
	public String getFxml() {
		return FXML;
	}

}
