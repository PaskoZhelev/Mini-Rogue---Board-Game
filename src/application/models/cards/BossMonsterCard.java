package application.models.cards;

import application.core.GameEngine;
import application.models.enums.CardType;
import javafx.scene.image.Image;

public class BossMonsterCard extends CardImpl {

	private static final Image IMAGE = new Image("/images/cards/BossMonster.jpg");
	private final String FXML = "/views/MonsterCardView.fxml";
	
	public BossMonsterCard() {
		super(IMAGE, CardType.BOSS_MONSTER);	
	}


	public String getFxml() {
		return FXML;
	}
}
