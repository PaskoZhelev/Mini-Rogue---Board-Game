package application.models;

import java.util.Random;

import javafx.scene.image.Image;

public class Die {
	
	private static final Image ONE = new Image("/images/dice/dice1.png");
	private static final Image TWO = new Image("/images/dice/dice2.png");
	private static final Image THREE = new Image("/images/dice/dice3.png");
	private static final Image FOUR = new Image("/images/dice/dice4.png");
	private static final Image FIVE = new Image("/images/dice/dice5.png");
	private static final Image SIX = new Image("/images/dice/dice6.png");

	
	private int value;
	private Random random;
	private Image image;
	
	public Die() {
		random = new Random();
	}
	
	public void roll(){
		this.value = random.nextInt(6) + 1;
		this.setDiceImage();
	}

	public int getValue() {
		return this.value;
	}

	public Image getImage() {
		return this.image;
	}

	private void setDiceImage() {
		switch (value) {
		case 1:
			image = ONE;
			break;
		case 2:
			image = TWO;
			break;
		case 3:
			image = THREE;
			break;
		case 4:
			image = FOUR;
			break;
		case 5:
			image = FIVE;
			break;
		case 6:
			image = SIX;
			break;
		default:
			break;
		}
	}
}
