package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.core.GameEngine;
import application.exceptions.GameException;
import application.exceptions.UnknownBossMonsterException;
import application.exceptions.UnknownNormalMonsterException;
import application.factories.BossMonsterFactory;
import application.factories.NormalMonsterFactory;
import application.handlers.RollingDiceHandler;
import application.interfaces.Card;
import application.interfaces.Character;
import application.interfaces.Dungeon;
import application.interfaces.Monster;
import application.models.Die;
import application.models.cards.BossMonsterCard;
import application.models.enums.SpellType;
import application.models.spells.FireballSpell;
import application.models.spells.IceSpell;
import application.models.spells.PoisonSpell;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class EventCardController implements Initializable {

	private static final Image ROLL_BUTTON = new Image("/images/rollButton.jpg");
	private static final Image ROLL_BUTTON_HOVER = new Image("/images/rollButtonHover.jpg");
	private static final Image DISABLED_ROLL_BUTTON = new Image("/images/disabledRollButton.jpg");
	private static final Image SKILL_CHECK_BUTTON = new Image("/images/skillCheckButton.jpg");
	private static final Image SKILL_CHECK_BUTTON_HOVER = new Image("/images/skillCheckButtonHover.jpg");
	private static final Image ROLLING_DICE = new Image("/images/dice/diceanimation.gif");
	private static final double ROLLING_DICE_ANIMATION_DURATION = 0.35;


	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private ImageView selectedCardSpace;
	@FXML
	private ImageView rollButton;
	@FXML
	private ImageView diceOneSpace;

	@FXML
	private Label resultLabel;
	@FXML
	private Label skillCheckLabel;
	@FXML
	private Label resultDigit;
	@FXML
	private Label skillCheckOutcome;
	@FXML
	private Label outcomeLabel;
	@FXML
	private Label plusOneLabel;
	@FXML
	private Label minusOneLabel;
	@FXML
	private Label currentResultLabel;
	@FXML
	private Label resolveResultLabelOne;
	@FXML
	private Label resolveResultLabelTwo;
	@FXML
	private Rectangle rectangleOne;

	private Card selectedCard;
	private GameEngine gameEngine;
	private Dungeon dungeon;
	private Character character;
	private RollingDiceHandler diceRoller;
	private int result = 0;
	boolean skillCheckSuccessful = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameEngine = GameController.getEngine();
		dungeon = gameEngine.getDungeon();
		character = gameEngine.getCharacter();
		selectedCard = gameEngine.getSelectedCard();
		diceRoller = new RollingDiceHandler(1);

		activateRollButton();
		selectedCardSpace.setImage(selectedCard.getImage());
	}

	public void resolveOutcome() throws IOException {
		switch (result) {
		case 1:
			resolveResultLabelOne.setText("You don’t know what this meat is, and you don’t care.");
			resolveResultLabelTwo.setText("Gain 1 Food");
			character.setFood(character.getFood() + 1);
			gameEngine.setRoundFinished(true);
			break;
		case 2:
			resolveResultLabelOne.setText("A Monster’s favorite drink. Might as well drink a sip too.");
			resolveResultLabelTwo.setText("Gain 2 HP");
			character.setHealth(character.getHealth() + 2);
			gameEngine.setRoundFinished(true);
			break;
		case 3:
			resolveResultLabelOne.setText("You’ve found a coin hidden in a crack between two stones.");
			resolveResultLabelTwo.setText("Gain 2 Gold");
			character.setGold(character.getGold() + 2);
			gameEngine.setRoundFinished(true);
			break;
		case 4:
			resolveResultLabelOne.setText("You’ve found a Monster’s blade sharpening tools.");
			resolveResultLabelTwo.setText("Gain 2 XP");
			character.setExperiencePoints(character.getExperiencePoints() + 2);
			gameEngine.setRoundFinished(true);
			break;
		case 5:
			resolveResultLabelOne.setText("A piece of armor found on a Monster’s carcass.");
			resolveResultLabelTwo.setText("Gain 1 Armor");
			character.setArmor(character.getArmor() + 1);
			gameEngine.setRoundFinished(true);
			break;
		case 6:
			resolveResultLabelTwo.setText("Fight!");
			gameEngine.setEventMonster(true);			
			PauseTransition delay = new PauseTransition(Duration.seconds(1.8));
			delay.setOnFinished(event -> {
				try {
					loadEventMonsterView();
				} catch (IOException e) {
					e.printStackTrace();
				}

			});
			delay.play();		
			break;

		default:
			break;
		}
		
		
	}

	public void activateRollButton() {
		rollButton.setImage(ROLL_BUTTON);

		rollButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				rollButton.setImage(ROLL_BUTTON_HOVER);
			}
		});

		rollButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				rollButton.setImage(ROLL_BUTTON);
			}
		});

		rollButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				activateSkillCheckButton();
				diceRoller.rollDice();
				rollingDice();
				updateResultLabel(diceRoller.getDice().get(0).getValue());			
			}
		});
	}

	public void activateSkillCheckButton() {
		rollButton.setImage(SKILL_CHECK_BUTTON);

		rollButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				rollButton.setImage(SKILL_CHECK_BUTTON_HOVER);
			}
		});

		rollButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				rollButton.setImage(SKILL_CHECK_BUTTON);
			}
		});

		rollButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				disableRollButton();
				diceRoller.rollDice();
				rollingDice();
				try {
					updateSkillCheckLabel();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void updateResultLabel(int result) {
		this.result = result;
		resultDigit.setText(String.valueOf(result));
	}

	public void updateSkillCheckLabel() throws IOException {
		int outcome = diceRoller.getDice().get(0).getValue();
		if (outcome <= character.getRank()) {
			skillCheckSuccessful = true;
			skillCheckOutcome.setText("Successful");
			outcomeLabel.setText("You can change your result!");
			changeOutcomeOptions();
		} else {
			skillCheckSuccessful = false;
			skillCheckOutcome.setText("Unsuccessful");
			outcomeLabel.setText("You cannot change your result!");
			resolveOutcome();
		}
	}

	public void rollingDice() {
		diceOneSpace.setImage(ROLLING_DICE);

		PauseTransition delay = new PauseTransition(Duration.seconds(ROLLING_DICE_ANIMATION_DURATION));
		delay.setOnFinished(event -> {
			diceOneSpace.setImage(diceRoller.getDice().get(0).getImage());
		});
		delay.play();
	}

	public void disableRollButton() {
		rollButton.setImage(DISABLED_ROLL_BUTTON);
		rollButton.setOnMouseClicked(null);
		rollButton.setOnMouseEntered(null);
		rollButton.setOnMouseExited(null);
	}

	public void changeOutcomeOptions() {
		plusOneLabel.setVisible(true);
		currentResultLabel.setVisible(true);
		minusOneLabel.setVisible(true);

		plusOneLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (result == 6) {
					updateResultLabel(1);
				} else {
					updateResultLabel(result + 1);
				}

				hideOutcomeOptions();
				try {
					resolveOutcome();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		minusOneLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (result == 1) {
					updateResultLabel(6);
				} else {
					updateResultLabel(result - 1);
				}
				
				hideOutcomeOptions();
				try {
					resolveOutcome();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		currentResultLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				updateResultLabel(result);
				hideOutcomeOptions();
				try {
					resolveOutcome();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void hideOutcomeOptions() {
		outcomeLabel.setVisible(false);
		plusOneLabel.setVisible(false);
		currentResultLabel.setVisible(false);
		minusOneLabel.setVisible(false);
	}

	private void loadEventMonsterView() throws IOException {
		AnchorPane newAnchorPane = FXMLLoader.load(getClass().getResource("/views/MonsterCardView.fxml"));
		anchorPane.getChildren().add(newAnchorPane); 
	}
}
