package application.models.cards;

import application.core.GameEngine;
import application.models.enums.CardType;
import javafx.scene.image.Image;

public class TrapCard extends CardImpl {

	private static final Image IMAGE = new Image("/images/cards/Trap.jpg");
	private final String FXML = "/views/TrapCardView.fxml";
	
	public TrapCard() {
		super(IMAGE,CardType.TRAP);	
	}


	@Override
	public String getFxml() {
		return FXML;
	}

}
