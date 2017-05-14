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

public class RestingCardController implements Initializable {

	
	private static final String UPPER_TEXT = "You can REST! Choose ONE of the options!";
	private static final String REINFORCE_TEXT = "You reinforced your weapon (+1 XP)!";
	private static final String FOOD_TEXT = "You found food (+1 Food)!";
	private static final String NO_MORE_FOOD_TEXT = "You CANNOT carry more food!";
	private static final String HEAL_TEXT = "You healed yourself (+2 HP)!";

	@FXML
	private ImageView selectedCardSpace;


	@FXML
	private Label upperLabel;
	@FXML
	private Label resultLabel;
	@FXML
	private Label reinforceWeapon;
	@FXML
	private Label searchFood;
	@FXML
	private Label healWounds;


	private Card selectedCard;
	private GameEngine gameEngine;
	private Character character;

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameEngine = GameController.getEngine();
		character = gameEngine.getCharacter();
		selectedCard = gameEngine.getSelectedCard();
		selectedCardSpace.setImage(selectedCard.getImage());

		upperLabel.setText(UPPER_TEXT);
		activateButtons();
		
	}

	private void activateButtons() {
		
		reinforceWeapon.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				character.setExperiencePoints(character.getExperiencePoints() + 1);
				disableButtons();
				resultLabel.setText(REINFORCE_TEXT);
			}
		});
		
		searchFood.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				int food = character.getFood();
				if (food >= 6) {
					resultLabel.setText(NO_MORE_FOOD_TEXT);
				} else {
					character.setFood(character.getFood() + 1);
					disableButtons();
					resultLabel.setText(FOOD_TEXT);
				}
			}
		});
		
		healWounds.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				character.setHealth(character.getHealth() + 2);
				disableButtons();
				resultLabel.setText(HEAL_TEXT);
			}
		});
		
		gameEngine.setRoundFinished(true);
	}
	
	private void disableButtons() {
		reinforceWeapon.setVisible(false);
		searchFood.setVisible(false);
		healWounds.setVisible(false);
	}
}
