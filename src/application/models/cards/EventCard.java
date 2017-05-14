package application.models.cards;

import application.core.GameEngine;
import application.models.enums.CardType;
import javafx.scene.image.Image;

public class EventCard extends CardImpl {

	private static final Image IMAGE = new Image("/images/cards/Event.jpg");
	private final String FXML = "/views/EventCardView.fxml";
	
	public EventCard() {
		super(IMAGE, CardType.EVENT);	
	}


	@Override
	public String getFxml() {
		return FXML;
	}
	

}
