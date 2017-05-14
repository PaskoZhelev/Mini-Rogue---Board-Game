package application.models.cards;

import application.core.GameEngine;
import application.models.enums.CardType;
import javafx.scene.image.Image;

public class MerchantCard extends CardImpl {

	private static final Image IMAGE = new Image("/images/cards/Merchant.jpg");
	private final String FXML = "/views/MerchantCardView.fxml";
	
	public MerchantCard() {
		super(IMAGE,CardType.MERCHANT);	
	}



	@Override
	public String getFxml() {
		return FXML;
	}

}
