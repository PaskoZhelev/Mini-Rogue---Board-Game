package application.models.cards;

import application.core.GameEngine;
import application.models.enums.CardType;
import javafx.scene.image.Image;

public class MonsterCard extends CardImpl {

	private static final Image IMAGE = new Image("/images/cards/Monster.jpg");
	private final String FXML = "/views/MonsterCardView.fxml";
	
	public MonsterCard() {
		super(IMAGE,CardType.MONSTER);	
	}


	public String getFxml() {
		return FXML;
	}
	
	
}
