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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class MerchantCardController implements Initializable {

	private static final String INSUFFICIENT_GOLD_WARNING = "You don't have enough gold!";
	private static final String NO_MORE_ARMOR_WARNING = "You cannot have more Armor!";
	private static final String NO_MORE_FOOD_WARNING = "You cannot carry more Food!";
	private static final String NO_MORE_SPELL_WARNING = "You cannot have more Spells!";
	private static final String NO_ARMOR_FOR_SELLING = "You do not have Armor!";
	private static final String NO_SPELLS_FOR_SELLING = "You do not have Spells!";
	private static final String HEALTH_BOUGHT = "You bought 1 Health!";
	private static final String BIG_HEALTH_BOUGHT = "You bought 4 Health!";
	private static final String FOOD_BOUGHT = "You bought 1 Food!";
	private static final String ARMOR_BOUGHT = "You bought 1 Armor!";
	private static final String SPELL_BOUGHT = "You bought a Spell!";
	private static final String ARMOR_SOLD = "You sold 1 Armor!";
	private static final String SPELL_SOLD = "You sold a Spell!";
	
	
	@FXML
	private ImageView selectedCardSpace;

	@FXML
	private Label buyFood;
	@FXML
	private Label buyHealth;
	@FXML
	private Label buyBigHealth;
	@FXML
	private Label buyArmor;
	@FXML
	private Label buySpell;
	@FXML
	private Label buyPoisonLabel;
	@FXML
	private Label buyHealingLabel;
	@FXML
	private Label buyIceLabel;
	@FXML
	private Label buyFireballLabel;
	@FXML
	private Label sellArmor;
	@FXML
	private Label sellSpell;
	@FXML
	private Label spellOneLabel;
	@FXML
	private Label spellTwoLabel;
	@FXML
	private Label warningLabel;
	@FXML
	private Label charGold;
	@FXML
	private Label charHealth;
	@FXML
	private Label charArmor;
	@FXML
	private Label charFood;

	@FXML
	private Rectangle rectangleOne;

	private int foodBuyPrice = 1;
	private int healthBuyPrice = 1;
	private int bigHealthBuyPrice = 3;
	private int armorBuyPrice = 6;
	private int spellBuyPrice = 8;
	private int armorSellPrice = 3;
	private int spellSellPrice = 4;

	private Card selectedCard;
	private GameEngine gameEngine;
	private Dungeon dungeon;
	private Character character;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameEngine = GameController.getEngine();
		dungeon = gameEngine.getDungeon();
		character = gameEngine.getCharacter();
		selectedCard = gameEngine.getSelectedCard();

		selectedCardSpace.setImage(selectedCard.getImage());
		updateCharStatsLabels();
		makeButtonsClickable();
		
		gameEngine.setRoundFinished(true);	//player can finish the round immediately if s(he) wants
	}

	private void makeButtonsClickable() {
		
		/** Buying **/
		
		buyFood.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (character.getGold() >= foodBuyPrice) {
					if (character.getFood() >= 6) {
						showWarning(NO_MORE_FOOD_WARNING);
						
					} else {																	
						character.setFood(character.getFood() + 1);
						character.setGold(character.getGold() - foodBuyPrice);
						showWarning(FOOD_BOUGHT);
					}
				} else {
					showWarning(INSUFFICIENT_GOLD_WARNING);
				}
			}
		});

		buyHealth.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (character.getGold() >= healthBuyPrice) {						
					character.setHealth(character.getHealth() + 1);
					character.setGold(character.getGold() - healthBuyPrice);
					showWarning(HEALTH_BOUGHT);
				} else {
					showWarning(INSUFFICIENT_GOLD_WARNING);
				}
			}
		});

		buyBigHealth.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (character.getGold() >= bigHealthBuyPrice) {															
					character.setHealth(character.getHealth() + 4);
					character.setGold(character.getGold() - bigHealthBuyPrice);
					showWarning(BIG_HEALTH_BOUGHT);
				} else {
					showWarning(INSUFFICIENT_GOLD_WARNING);
				}
			}
		});

		buyArmor.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (character.getGold() >= armorBuyPrice) {
					if (character.getArmor() >= 5) {
						showWarning(NO_MORE_ARMOR_WARNING);
					} else {																
						character.setArmor(character.getArmor() + 1);
						character.setGold(character.getGold() - armorBuyPrice);
						showWarning(ARMOR_BOUGHT);	
					}
				} else {
					showWarning(INSUFFICIENT_GOLD_WARNING);
				}
			}
		});
		
		buySpell.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (character.getGold() >= spellBuyPrice) {
					if ((character.getSpellOne() != null) && (character.getSpellTwo() != null)){
						showWarning(NO_MORE_SPELL_WARNING);
					} else {
						showBuySpellOptions();
					}
					
				} else {
					showWarning(INSUFFICIENT_GOLD_WARNING);
				}
			}
		});
		
		/** Selling **/
		
		sellArmor.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (character.getArmor() > 0) {
					character.setArmor(character.getArmor() - 1);
					character.setGold(character.getGold() + armorSellPrice);
					showWarning(ARMOR_SOLD);
				} else {
					showWarning(NO_ARMOR_FOR_SELLING);
				}
			}
		});
		
		sellSpell.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if ((character.getSpellOne() == null) && (character.getSpellTwo() == null)) {			
					showWarning(NO_SPELLS_FOR_SELLING);
				} else {
					showSellSpellOptions();
				}
			}
		});
	}

	private void showWarning(String warningText) {
		warningLabel.setVisible(true);
		warningLabel.setText(warningText);
		updateCharStatsLabels();
		PauseTransition delay = new PauseTransition(Duration.seconds(2.0));
		delay.setOnFinished(event -> {
			warningLabel.setVisible(false);
		});
		delay.play();

	}
	
	private void showBuySpellOptions() {
		buyPoisonLabel.setVisible(true);
		buyFireballLabel.setVisible(true);
		buyHealingLabel.setVisible(true);
		buyIceLabel.setVisible(true);
		
		buyPoisonLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (character.getSpellOne() == null) {
					character.addSpell(new PoisonSpell());
					character.setGold(character.getGold() - spellBuyPrice);
					showWarning(SPELL_BOUGHT);
				} else if (character.getSpellTwo() == null){
					character.addSpell(new PoisonSpell());
					character.setGold(character.getGold() - spellBuyPrice);
					showWarning(SPELL_BOUGHT);
				} else {
					showWarning(NO_MORE_SPELL_WARNING);
				}
				
				hideBuySpellOptions();
			}
		});
		buyFireballLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (character.getSpellOne() == null) {
					character.addSpell(new FireballSpell());
					character.setGold(character.getGold() - spellBuyPrice);
					showWarning(SPELL_BOUGHT);
				} else if (character.getSpellTwo() == null){
					character.addSpell(new FireballSpell());
					character.setGold(character.getGold() - spellBuyPrice);
					showWarning(SPELL_BOUGHT);
				} else {
					showWarning(NO_MORE_SPELL_WARNING);
				}
				
				hideBuySpellOptions();
			}
		});
		buyHealingLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (character.getSpellOne() == null) {
					character.addSpell(new HealingSpell());
					character.setGold(character.getGold() - spellBuyPrice);
					showWarning(SPELL_BOUGHT);
				} else if (character.getSpellTwo() == null){
					character.addSpell(new HealingSpell());
					character.setGold(character.getGold() - spellBuyPrice);
					showWarning(SPELL_BOUGHT);
				} else {
					showWarning(NO_MORE_SPELL_WARNING);
				}
				
				hideBuySpellOptions();
			}
		});
		buyIceLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (character.getSpellOne() == null) {
					character.addSpell(new IceSpell());
					character.setGold(character.getGold() - spellBuyPrice);
					showWarning(SPELL_BOUGHT);
				} else if (character.getSpellTwo() == null){
					character.addSpell(new IceSpell());
					character.setGold(character.getGold() - spellBuyPrice);
					showWarning(SPELL_BOUGHT);
				} else {
					showWarning(NO_MORE_SPELL_WARNING);
				}
				
				hideBuySpellOptions();
			}
		});
	}

	private void showSellSpellOptions() {
		spellOneLabel.setText(null);
		spellTwoLabel.setText(null);
		spellOneLabel.setOnMouseClicked(null);
		spellTwoLabel.setOnMouseClicked(null);
		spellOneLabel.setVisible(true);
		spellTwoLabel.setVisible(true);
		
		if (character.getSpellOne() != null) {			
			spellOneLabel.setText(character.getSpellOne().getType().toString());
		} 
		
		if (character.getSpellTwo() != null) {		
			spellTwoLabel.setText(character.getSpellTwo().getType().toString());
		} 
		
		spellOneLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				character.removeSpellOne();
				character.setGold(character.getGold() + spellSellPrice);
				showWarning(SPELL_SOLD);
				hideSellSpellOptions();				
			}
		});
		
		spellTwoLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				character.removeSpellTwo();
				character.setGold(character.getGold() + spellSellPrice);
				showWarning(SPELL_SOLD);
				hideSellSpellOptions();
			}
		});
	}
	
	private void hideBuySpellOptions() {
		buyPoisonLabel.setVisible(false);
		buyFireballLabel.setVisible(false);
		buyHealingLabel.setVisible(false);
		buyIceLabel.setVisible(false);
	}

	private void hideSellSpellOptions() {
		spellOneLabel.setVisible(false);
		spellTwoLabel.setVisible(false);
	}

	private void updateCharStatsLabels() {
		charGold.setText(String.valueOf(character.getGold()));
		charHealth.setText(String.valueOf(character.getHealth()));
		charArmor.setText(String.valueOf(character.getArmor()));
		charFood.setText(String.valueOf(character.getFood()));
	}
}
