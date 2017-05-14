package application.models.cards;

import application.core.GameEngine;
import application.models.enums.CardType;
import javafx.scene.image.Image;

public class RestingCard extends CardImpl {

	private static final Image IMAGE = new Image("/images/cards/Resting.jpg");
	private final String FXML = "/views/RestingCardView.fxml";
	
	public RestingCard() {
		super(IMAGE,CardType.RESTING);	
	}


	@Override
	public String getFxml() {
		return FXML;
	}
	


}
