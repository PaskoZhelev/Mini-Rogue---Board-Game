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
import application.interfaces.Spell;
import application.models.Die;
import application.models.cards.BossMonsterCard;
import application.models.enums.SpellType;
import application.models.spells.FireballSpell;
import application.models.spells.HealingSpell;
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
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class TrapCardController implements Initializable {

	private static final Image ROLL_BUTTON = new Image("/images/rollButton.jpg");
	private static final Image ROLL_BUTTON_HOVER = new Image("/images/rollButtonHover.jpg");
	private static final Image DISABLED_ROLL_BUTTON = new Image("/images/disabledRollButton.jpg");
	private static final Image ROLLING_DICE = new Image("/images/dice/diceanimation.gif");
	private static final double ROLLING_DICE_ANIMATION_DURATION = 0.35;
	private static final String ROLL_TRAP_TEXT = "Roll a die to see what TRAP you face!";
	private static final String ROLL_SKILL_CHECK_TEXT = "Roll for SKILL check!";
	private static final String FACE_TEXT = "You face a/an ";
	private static final String MIASMA_TEXT = FACE_TEXT + "MOLD MIASMA (-1 Food)!";
	private static final String TRIPWIRE_TEXT = FACE_TEXT + "TRIPWIRE (-1 Gold)!";
	private static final String ACID_TEXT = FACE_TEXT + "ACID MIST (-1 Armor)!";
	private static final String SPRING_TEXT = FACE_TEXT + "SPRING BLADES (-1 HP)!";
	private static final String MOVING_WALLS_TEXT = FACE_TEXT + "MOVING WALLS (-1 XP)!";
	private static final String PIT_TEXT = FACE_TEXT + "PIT (-2 HP, fall area below)!";
	private static final String LOSE_HP_TEXT = "Insufficient Supplies! You lose -2 HP instead!";

	@FXML
	private ImageView selectedCardSpace;
	@FXML
	private ImageView rollButton;
	@FXML
	private ImageView diceOneSpace;

	@FXML
	private Label rollingLabel;
	@FXML
	private Label resultLabel;
	@FXML
	private Label loseHPLabel;
	@FXML
	private Label trapResultLabel;
	@FXML
	private Label skillCheckLabel;


	@FXML
	private Rectangle rectangleOne;

	private Card selectedCard;
	private GameEngine gameEngine;
	private Dungeon dungeon;
	private Character character;
	private RollingDiceHandler diceRoller;
	private int trapResult = 0;
	private boolean rollingForSkillCheck = false;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameEngine = GameController.getEngine();
		dungeon = gameEngine.getDungeon();
		character = gameEngine.getCharacter();
		selectedCard = gameEngine.getSelectedCard();
		selectedCardSpace.setImage(selectedCard.getImage());
		diceRoller = new RollingDiceHandler(1);

		rollingLabel.setText(ROLL_TRAP_TEXT);
		activateRollButton();

	}

	private void resolveOutcome() throws IOException {
		switch (trapResult) {
		case 1:
			resultLabel.setText(MIASMA_TEXT);
			if (character.getFood() == 0) {
				loseHPLabel.setText(LOSE_HP_TEXT);
				character.setHealth(character.getHealth() - 2);
			} else {
				character.setFood(character.getFood() - 1);
			}			
			break;
		case 2:
			resultLabel.setText(TRIPWIRE_TEXT);
			if (character.getGold() == 0) {
				loseHPLabel.setText(LOSE_HP_TEXT);
				character.setHealth(character.getHealth() - 2);
			} else {
				character.setGold(character.getGold() - 1);
			}
			break;
		case 3:
			resultLabel.setText(ACID_TEXT);
			if (character.getArmor() == 0) {
				loseHPLabel.setText(LOSE_HP_TEXT);
				character.setHealth(character.getHealth() - 2);
			} else {
				character.setArmor(character.getArmor() - 1);
			}
			break;
		case 4:
			resultLabel.setText(SPRING_TEXT);
			character.setHealth(character.getHealth() - 1);
			break;
		case 5:
			resultLabel.setText(MOVING_WALLS_TEXT);
			if (character.getExperiencePoints() == 0) {
				loseHPLabel.setText(LOSE_HP_TEXT);
				character.setHealth(character.getHealth() - 2);
			} else {
				character.setExperiencePoints(character.getExperiencePoints() - 1);
			}
			break;
		case 6:
			resultLabel.setText(PIT_TEXT);
			updateDungeonArea();
			break;
		default:
			break;
		}

		gameEngine.setRoundFinished(true);
	}

	private void activateRollButton() {
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
				diceRoller.rollDice();
				rollingDice();
			}
		});
	}

	private void rollingDice() {
		diceOneSpace.setImage(ROLLING_DICE);

		PauseTransition delay = new PauseTransition(Duration.seconds(ROLLING_DICE_ANIMATION_DURATION));
		delay.setOnFinished(event -> {
			diceOneSpace.setImage(diceRoller.getDice().get(0).getImage());
			if(!rollingForSkillCheck) {
				trapResult = diceRoller.getDice().get(0).getValue();
				trapResultLabel.setText(String.valueOf(trapResult));
				
				rollingLabel.setText(ROLL_SKILL_CHECK_TEXT);
				rollingForSkillCheck = true;
			} else {
				disableRollButton();
				rollingLabel.setVisible(false);
				try {
					updateSkillCheckLabel();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}						
		});
		delay.play();
	}

	private void disableRollButton() {
		rollButton.setImage(DISABLED_ROLL_BUTTON);
		rollButton.setOnMouseClicked(null);
		rollButton.setOnMouseEntered(null);
		rollButton.setOnMouseExited(null);
	}

	private void updateSkillCheckLabel() throws IOException {
		int outcome = diceRoller.getDice().get(0).getValue();
		if (outcome <= character.getRank()) {
			
			skillCheckLabel.setText("Successful");
			resultLabel.setText("You EVADED the trap!");
			trapResultLabel.setVisible(false);
			gameEngine.setRoundFinished(true);
		} else {			
			skillCheckLabel.setText("Unsuccessful");
			resolveOutcome();
		}
	}
	
	private void updateDungeonArea() {
		int currentDungeonArea = dungeon.getDungeonArea();
		if (currentDungeonArea <= 4) {
			dungeon.setDungeonArea(currentDungeonArea + 2);
			character.setHealth(character.getHealth() - 2);
		} else if ((currentDungeonArea >= 6) && (currentDungeonArea <= 10)) {
			dungeon.setDungeonArea(currentDungeonArea + 3);
			character.setHealth(character.getHealth() - 2);
		} else {	//in case it is 5 or larger than 10
			character.setHealth(character.getHealth() - 2);
		}
	}
}
