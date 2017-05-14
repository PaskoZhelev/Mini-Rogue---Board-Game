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

public class RewardItemController implements Initializable {

	private static final Image ROLL_BUTTON = new Image("/images/rollButton.jpg");
	private static final Image ROLL_BUTTON_HOVER = new Image("/images/rollButtonHover.jpg");
	private static final Image DISABLED_ROLL_BUTTON = new Image("/images/disabledRollButton.jpg");
	private static final Image ROLLING_DICE = new Image("/images/dice/diceanimation.gif");
	private static final double ROLLING_DICE_ANIMATION_DURATION = 0.35;
	private static final String ROLL_POSSIBLE_TEXT = "Roll for item!";
	private static final String FOUND_TEXT = "You found a/an ";
	private static final String ARMOR_TEXT = FOUND_TEXT + "ARMOR PIECE (+1 Armor)!";
	private static final String WEAPON_TEXT = FOUND_TEXT + "BETTER WEAPON (+2 XP)!";
	private static final String FIREBALL_TEXT = FOUND_TEXT + "FIREBALL SPELL!";
	private static final String ICE_TEXT = FOUND_TEXT + "ICE SPELL!";
	private static final String POISON_TEXT = FOUND_TEXT + "POISON SPELL!";
	private static final String HEALING_TEXT = FOUND_TEXT + "HEALING SPELL!";

	
	@FXML
	private ImageView rollButton;
	@FXML
	private ImageView diceOneSpace;

	@FXML
	private Label monsterDefeatedLabel;
	@FXML
	private Label resultLabel;
	@FXML
	private Label spellOneLabel;
	@FXML
	private Label spellTwoLabel;
	@FXML
	private Label twoSpellAlreadyLabel;
	@FXML
	private Label replaceLabel;
	@FXML
	private Label continueLabel;


	@FXML
	private Rectangle rectangleOne;

	private Card selectedCard;
	private GameEngine gameEngine;
	private Dungeon dungeon;
	private Character character;
	private RollingDiceHandler diceRoller;
	private int result = 0;
	boolean rollingForItem = false;
	private Spell foundSpell;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameEngine = GameController.getEngine();
		dungeon = gameEngine.getDungeon();
		character = gameEngine.getCharacter();			
		diceRoller = new RollingDiceHandler(1);
		
		activateRollButton();

	}

	public void resolveOutcome() throws IOException {
		switch (result) {
		case 1:
			resultLabel.setText(ARMOR_TEXT);
			character.setArmor(character.getArmor() + 1);
			break;
		case 2:
			resultLabel.setText(WEAPON_TEXT);
			character.setExperiencePoints(character.getExperiencePoints() + 2);
			break;
		case 3:
			resultLabel.setText(FIREBALL_TEXT);
			foundSpell = new FireballSpell();
			checkIfCanCarrySpells();
			break;
		case 4:
			resultLabel.setText(ICE_TEXT);
			foundSpell = new IceSpell();
			checkIfCanCarrySpells();
			break;
		case 5:
			resultLabel.setText(POISON_TEXT);
			foundSpell = new PoisonSpell();
			checkIfCanCarrySpells();
			break;
		case 6:
			resultLabel.setText(HEALING_TEXT);
			foundSpell = new HealingSpell();
			checkIfCanCarrySpells();
			break;

		default:
			break;
		}

		gameEngine.setRoundFinished(true);
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
				diceRoller.rollDice();
				rollingDice();
			}
		});
	}

	public void rollingDice() {
		diceOneSpace.setImage(ROLLING_DICE);

		PauseTransition delay = new PauseTransition(Duration.seconds(ROLLING_DICE_ANIMATION_DURATION));
		delay.setOnFinished(event -> {
			diceOneSpace.setImage(diceRoller.getDice().get(0).getImage());			
				result = diceRoller.getDice().get(0).getValue();			
				disableRollButton();
				try {
					resolveOutcome();
				} catch (IOException e) {
					e.printStackTrace();
				}			
		});
		delay.play();
	}

	public void disableRollButton() {
		rollButton.setImage(DISABLED_ROLL_BUTTON);
		rollButton.setOnMouseClicked(null);
		rollButton.setOnMouseEntered(null);
		rollButton.setOnMouseExited(null);
	}	

	public void checkIfCanCarrySpells() {
		if ((character.getSpellOne() != null) && (character.getSpellTwo() != null)) {
			showRemoveSpellOptions();
			gameEngine.setRoundFinished(true);
		} else {
			character.addSpell(foundSpell);
			gameEngine.setRoundFinished(true);
		}
	}
	
	private void showRemoveSpellOptions() {
		twoSpellAlreadyLabel.setVisible(true);
		replaceLabel.setVisible(true);		
		spellOneLabel.setText(null);
		spellTwoLabel.setText(null);
		spellOneLabel.setOnMouseClicked(null);
		spellTwoLabel.setOnMouseClicked(null);
		spellOneLabel.setVisible(true);
		spellTwoLabel.setVisible(true);
		continueLabel.setVisible(true);

		if (character.getSpellOne() != null) {
			spellOneLabel.setText(character.getSpellOne().getType().toString());
		}

		if (character.getSpellTwo() != null) {
			spellTwoLabel.setText(character.getSpellTwo().getType().toString());
		}

		spellOneLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				character.removeSpellOne();
				character.addSpell(foundSpell);
				hideRemoveSpellOptions();
			}
		});

		spellTwoLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				character.removeSpellTwo();
				character.addSpell(foundSpell);
				hideRemoveSpellOptions();
			}
		});
		
		continueLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {				
				hideRemoveSpellOptions();
			}
		});
	}
	
	private void hideRemoveSpellOptions() {
		spellOneLabel.setVisible(false);
		spellTwoLabel.setVisible(false);
		twoSpellAlreadyLabel.setVisible(false);
		replaceLabel.setVisible(false);
		continueLabel.setVisible(false);
	}
}
