package application.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import application.core.GameEngine;
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
import application.models.monsters.normal.EventMonster;
import application.models.spells.FireballSpell;
import application.models.spells.HealingSpell;
import application.models.spells.IceSpell;
import application.models.spells.PoisonSpell;
import javafx.animation.PauseTransition;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class MonsterCardController implements Initializable {

	private static final String REWARD_ITEM_FXML = "/views/RewardItemView.fxml";
	private static final Image ROLL_BUTTON = new Image("/images/rollButton.jpg");
	private static final Image ROLL_BUTTON_HOVER = new Image("/images/rollButtonHover.jpg");
	private static final Image DISABLED_ROLL_BUTTON = new Image("/images/disabledRollButton.jpg");
	private static final Image ROLLING_DICE = new Image("/images/dice/diceanimation.gif");
	private static final double ROLLING_DICE_ANIMATION_DURATION = 0.35;

	@FXML
	private ImageView selectedCardSpace;
	@FXML
	private ImageView rollButton;
	@FXML
	private ImageView diceOneSpace;
	@FXML
	private ImageView diceTwoSpace;
	@FXML
	private ImageView diceThreeSpace;
	@FXML
	private ImageView diceFourSpace;

	@FXML
	private Label charHPLabel;
	@FXML
	private Label charHPDigit;
	@FXML
	private Label monsterHPLabel;
	@FXML
	private Label monsterHPDigit;
	@FXML
	private Label charDMGLabel;
	@FXML
	private Label charDMGDigit;
	@FXML
	private Label monsterDMGLabel;
	@FXML
	private Label monsterDMGDigit;
	@FXML
	private Text battleResultLabel;
	@FXML
	private Label charSpellsLabel;
	@FXML
	private Label spellOneLabel;
	@FXML
	private Label spellTwoLabel;
	@FXML
	private Label critical;
	@FXML
	private Label miss;
	@FXML
	private Text rerollLabel;
	@FXML
	private Label yesLabel;
	@FXML
	private Label noLabel;
	@FXML
	private Label minusHPLabel;
	@FXML
	private Label minusXPLabel;
	@FXML
	private Label typeOfMonsterLabel;
	@FXML
	private Text rewardText;
	@FXML
	private Text rerollCriticalLabel;
	@FXML
	private Label yesCriticalLabel;
	@FXML
	private Label noCriticalLabel;

	@FXML
	private Rectangle rectangleOne;
	@FXML
	private Rectangle rectangleTwo;
	@FXML
	private Rectangle rectangleThree;
	@FXML
	private Rectangle rectangleFour;

	private Card selectedCard;
	private GameEngine gameEngine;
	private Dungeon dungeon;
	private Character character;
	private int characterDamage;
	private Monster monster;
	private RollingDiceHandler diceRoller;
	private ImageView[] diceSpaces = new ImageView[4];

	private boolean battleFinished;
	private boolean gameOver;
	private boolean spellUsedDuringThisAttack;
	private boolean damageFromCritical;
	private List<Integer> alreadyRerolledDice;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		diceSpaces[0] = diceOneSpace;
		diceSpaces[1] = diceTwoSpace;
		diceSpaces[2] = diceThreeSpace;
		diceSpaces[3] = diceFourSpace;
		gameEngine = GameController.getEngine();
		dungeon = gameEngine.getDungeon();
		character = gameEngine.getCharacter();
		selectedCard = gameEngine.getSelectedCard();
		alreadyRerolledDice = new ArrayList<>();
		selectedCardSpace.setImage(selectedCard.getImage());

		switch (selectedCard.getCardType()) {
		case MONSTER:
			try {
				resolveMonsterCard();
			} catch (UnknownNormalMonsterException e) {
				e.printStackTrace();
			}
			break;
		case BOSS_MONSTER:
			try {
				resolveBossMonsterCard();
			} catch (UnknownBossMonsterException e) {
				e.printStackTrace();
			}
			break;
		default:
			try {
				resolveMonsterCard();
			} catch (UnknownNormalMonsterException e) {
				e.printStackTrace();
			}
			break;
		}

	}

	public void resolveMonsterCard() throws UnknownNormalMonsterException {
		if (!gameEngine.isEventMonster()) {
			monster = NormalMonsterFactory.create(dungeon);
		} else {
			Die die = new Die();
			die.roll();
			monster = new EventMonster(dungeon, dungeon.getDungeonArea() + die.getValue());
			gameEngine.setEventMonster(false);
		}

		gameEngine.setMonster(monster);
		diceRoller = new RollingDiceHandler(character.getRank());
		characterDamage = 0;
		battleFinished = false;

		showMonsterCardPanel();
	}

	public void resolveBossMonsterCard() throws UnknownBossMonsterException {
		monster = BossMonsterFactory.create(dungeon);
		gameEngine.setMonster(monster);
		diceRoller = new RollingDiceHandler(character.getRank());
		characterDamage = 0;
		battleFinished = false;

		showMonsterCardPanel();
	}

	/*
	 * MONSTER CARD
	 */

	@FXML
	public void activateSpellOne() {
		if (!spellUsedDuringThisAttack) {
			character.getSpellOne().activate(gameEngine);
			spellOneLabel.setText(null);
			if (character.getSpellOne().getType().toString().equals("POISON")) {
				charDMGDigit.setText(String.valueOf(characterDamage + 5));
			}
			
			character.removeSpellOne();
			updateCharacterAndMonsterHealthSpace();
			checkIsMonsterAliveAfterSpell();
			spellUsedDuringThisAttack = true;
		}
	}

	@FXML
	public void activateSpellTwo() {
		if (!spellUsedDuringThisAttack) {
			character.getSpellTwo().activate(gameEngine);
			spellTwoLabel.setText(null);
			if (character.getSpellTwo().getType().toString().equals("POISON")) {
				charDMGDigit.setText(String.valueOf(characterDamage + 5));
			}
			character.removeSpellTwo();
			updateCharacterAndMonsterHealthSpace();
			checkIsMonsterAliveAfterSpell();
			spellUsedDuringThisAttack = true;
		}
	}

	public void showMonsterCardPanel() {
		rollButton.setVisible(true);
		clearDiceSpaces();
		charHPLabel.setVisible(true);
		charHPDigit.setVisible(true);
		charHPDigit.setText(String.valueOf(character.getHealth()));
		monsterHPLabel.setVisible(true);
		monsterHPDigit.setVisible(true);
		monsterHPDigit.setText(String.valueOf(monster.getHealth()));
		charDMGLabel.setVisible(true);
		charDMGDigit.setVisible(true);
		charDMGDigit.setText("0");
		monsterDMGLabel.setVisible(true);
		monsterDMGDigit.setVisible(true);
		monsterDMGDigit.setText(String.valueOf(monster.getDamage()));
		battleResultLabel.setVisible(true);
		charSpellsLabel.setVisible(true);
		spellOneLabel.setVisible(true);
		spellTwoLabel.setVisible(true);
		rectangleOne.setVisible(true);
		rectangleTwo.setVisible(true);
		rectangleThree.setVisible(true);
		rectangleFour.setVisible(true);
		critical.setVisible(true);
		miss.setVisible(true);
		gameEngine.setIceSpellActivated(false);
		gameEngine.setPoisonSpellActivated(false);
		yesLabel.setVisible(false);
		noLabel.setVisible(false);
		minusHPLabel.setVisible(false);
		minusXPLabel.setVisible(false);
		showTypeOfMonster();
		if (character.getSpellOne() != null) {
			spellOneLabel.setText(character.getSpellOne().getType().toString());
		}

		if (character.getSpellTwo() != null) {
			spellTwoLabel.setText(character.getSpellTwo().getType().toString());
		}

		activateRollButton();
	}

	public void showTypeOfMonster() {
		typeOfMonsterLabel.setVisible(true);
		typeOfMonsterLabel.setText("You are fighting: " + monster.getClass().getSimpleName().toString());
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
				disableRollButton();
				characterDamage = 0;
				alreadyRerolledDice.clear();
				damageFromCritical = false;
				critical.setText(null);
				miss.setText(null);
				battleResultLabel.setText(null);

				diceRoller.rollDice();
				rollingDiceAnimation();
				calculateCharacterDamage();
				updateCharacterDamageSpace();

			}
		});
	}

	public void rollingDiceAnimation() {
		int numDice = diceRoller.getDice().size();
		for (int i = 0; i < numDice; i++) {
			diceSpaces[i].setImage(ROLLING_DICE);
		}
		PauseTransition delay = new PauseTransition(Duration.seconds(ROLLING_DICE_ANIMATION_DURATION));
		delay.setOnFinished(event -> {
			populateDiceSpaces();

		});
		delay.play();
	}

	public void populateDiceSpaces() {
		int numDice = diceRoller.getDice().size();
		for (int a = 0; a < numDice; a++) {
			diceSpaces[a].setImage(diceRoller.getDice().get(a).getImage());
		}
	}

	private void showRerollSixOption() {

		rerollCriticalLabel.setVisible(true);
		rerollCriticalLabel.setText("Reroll Critical?");
		yesCriticalLabel.setVisible(true);
		noCriticalLabel.setVisible(true);
		critical.setText("Critical!");

		yesCriticalLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				rerollCriticalLabel.setText("Click the critical die to reroll");
				yesCriticalLabel.setVisible(false);
				noCriticalLabel.setVisible(false);
				activateCriticalRerollOnClick();
			}
		});

		noCriticalLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				disableRerollSixOption();
				showRerollOption();
			}
		});
	}

	private void activateCriticalRerollOnClick() {
		int numDice = diceRoller.getDice().size();
		for (int a = 0; a < numDice; a++) {
			int index = a;
			Die die = diceRoller.getDice().get(index);
			if (die.getValue() == 6) {
					diceSpaces[index].setCursor(Cursor.HAND);
					diceSpaces[index].setOnMouseClicked(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent evt) {
							die.roll();
							populateDiceSpaces();
							// calculateCharacterDamage();
							if (die.getValue() != 1) {
								characterDamage += die.getValue();
							} else {
								miss.setText("Miss!");
								characterDamage -= 6;
							}
							disableDiceRerollOnClick();
							updateCharacterDamageSpace();
						}
					});
				}
			}
		}	

	private void disableRerollSixOption() {
		rerollCriticalLabel.setVisible(false);
		yesCriticalLabel.setVisible(false);
		noCriticalLabel.setVisible(false);
		critical.setText(null);
	}

	private boolean isSixRolled() {
		int numDice = diceRoller.getDice().size();
		for (int b = 0; b < numDice; b++) {
			int diceValue = diceRoller.getDice().get(b).getValue();
			if (diceValue == 6) {
				return true;
			}
		}

		return false;
	}
	
	public void characterAttacks() {

		if ((monster.getHealth() - characterDamage) < 0) {
			monster.setHealth(0);
		} else {
			monster.setHealth(monster.getHealth() - characterDamage);
		}
		spellUsedDuringThisAttack = false;

	}

	public void monsterAttacks() {
		if (gameEngine.isIceSpellActivated()) {
			gameEngine.setIceSpellActivated(false);
			return;
		}
		int monsterDamageReducedByArmor = monster.getDamage() - character.getArmor();
		if (monsterDamageReducedByArmor > 0) {
			if ((character.getHealth() - monsterDamageReducedByArmor <= 0)) {
				character.setHealth(0);
			} else {
				character.setHealth(character.getHealth() - monsterDamageReducedByArmor);
			}
		}

	}

	public void calculateCharacterDamage() {
		characterDamage = 0;
		int numDice = diceRoller.getDice().size();
		if (gameEngine.isPoisonSpellActivated()) {
			characterDamage += 5;
		}

		for (int b = 0; b < numDice; b++) {
			int diceValue = diceRoller.getDice().get(b).getValue();
			if (diceValue != 1) {
				characterDamage += diceValue;
			} else {
				miss.setText("Miss!");
			}
		}
	}

	public void updateCharacterDamageSpace() {
		charDMGDigit.setText(String.valueOf(characterDamage));
		
		if (isSixRolled()) {
			showRerollSixOption();
		} else {
			disableRerollSixOption();
			showRerollOption();
		}
	}

	public void updateCharacterAndMonsterHealthSpace() {
		if (character.getHealth() < 0) {
			charHPDigit.setText("0");
		} else {
			charHPDigit.setText(String.valueOf(character.getHealth()));
		}
		if (monster.getHealth() < 0) {
			monsterHPDigit.setText("0");
		} else {
			monsterHPDigit.setText(String.valueOf(monster.getHealth()));
		}
	}

	/* BATTLE FINISH */

	public void checkBattleFinished() {
		if (monster.getHealth() <= 0) {
			if ((dungeon.getDungeonArea() == 14)
					&& (monster.getClass().getSimpleName().toString().equals("OgsRemains"))) {
				gameEngine.setGameFinished(true);
			}
			battleResultLabel.setText("The monster is killed!");
			rewardText.setText(monster.getRewardText());
			gainReward();
			battleFinished = true;
			gameEngine.setRoundFinished(true);
			gameEngine.setMonster(null);
			disableRollButton();
			disableRerollSixOption();
			disableDiceRerollOption();
			if (!character.isMonsterDefeated()) {
				character.setMonsterDefeated(true);
			}
		} else {
			battleResultLabel.setText("The monster is still alive and attacks! Roll Again!");
			monsterAttacks();
			disableRerollSixOption();
			disableDiceRerollOption();
			if (character.getHealth() <= 0) {
				character.setDead(true);
				gameOver = true;
				gameEngine.setRoundFinished(true);
				battleResultLabel.setText("You are dead! Game Over!");
				disableRollButton();
				disableRerollSixOption();
				disableDiceRerollOption();
			}

		}
	}

	public void checkIsMonsterAliveAfterSpell() {
		if (monster.getHealth() <= 0) {
			battleResultLabel.setText("The monster is killed!");
			battleFinished = true;
			gameEngine.setRoundFinished(true);
			gameEngine.setMonster(null);
			disableRollButton();
			if (!character.isMonsterDefeated()) {
				character.setMonsterDefeated(true);
			}
		} else {
			battleResultLabel.setText("The monster is still alive!");
		}
	}

	public void clearDiceSpaces() {
		diceOneSpace.setImage(null);
		diceTwoSpace.setImage(null);
		diceThreeSpace.setImage(null);
		diceFourSpace.setImage(null);
		diceOneSpace.setVisible(true);
		diceTwoSpace.setVisible(true);
		diceThreeSpace.setVisible(true);
		diceFourSpace.setVisible(true);
	}

	public void disableRollButton() {
		rollButton.setImage(DISABLED_ROLL_BUTTON);
		rollButton.setOnMouseClicked(null);
		rollButton.setOnMouseEntered(null);
		rollButton.setOnMouseExited(null);
	}

	private void performAttackSteps() {
		activateRollButton();
		characterAttacks();
		checkBattleFinished();
		updateCharacterAndMonsterHealthSpace();
	}

	private void showRerollOption() {
		int numDice = diceRoller.getDice().size();
		if (!(numDice == alreadyRerolledDice.size())) {
			rerollLabel.setVisible(true);
			rerollLabel.setText("Reroll? (-2 HP or -1 XP)");
			yesLabel.setVisible(true);
			noLabel.setVisible(true);

			yesLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent evt) {
					selectRerollOption();
				}
			});
			noLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent evt) {
					alreadyRerolledDice.clear();
					disableDiceRerollOption();
					performAttackSteps();
				}
			});
		} else {

			if (isSixRolled()) {
				showRerollSixOption();
			} else {
				disableDiceRerollOption();
				performAttackSteps();
			}					
		}
	}

	private void selectRerollOption() {
		yesLabel.setVisible(false);
		noLabel.setVisible(false);
		minusHPLabel.setVisible(true);
		minusXPLabel.setVisible(true);
		minusHPLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (!((character.getHealth() - 2) <= 0)) {
					character.setHealth(character.getHealth() - 2);
					rerollLabel.setText("Click on a die that you want to reroll!");
					minusHPLabel.setVisible(false);
					minusXPLabel.setVisible(false);
					updateCharacterAndMonsterHealthSpace();
					activateDiceRerollOnClick();
				} else {
					minusHPLabel.setVisible(false);
					minusXPLabel.setVisible(false);
					battleResultLabel.setText("Not Enough HP!");
					showRerollOption();
				}
			}
		});
		minusXPLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (!((character.getExperiencePoints() - 1) < 0)) {
					character.setExperiencePoints(character.getExperiencePoints() - 1);
					rerollLabel.setText("Click on a die that you want to reroll!");
					minusHPLabel.setVisible(false);
					minusXPLabel.setVisible(false);
					activateDiceRerollOnClick();
				} else {
					minusHPLabel.setVisible(false);
					minusXPLabel.setVisible(false);
					battleResultLabel.setText("Not Enough XP!");
					showRerollOption();
				}
			}
		});
	}

	private void activateDiceRerollOnClick() {
		int numDice = diceRoller.getDice().size();
		for (int a = 0; a < numDice; a++) {
			int index = a;
			if (!alreadyRerolledDice.contains(index)) {
				diceSpaces[index].setCursor(Cursor.HAND);
				diceSpaces[index].setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent evt) {
						alreadyRerolledDice.add(index);
						Die die = diceRoller.getDice().get(index);
						if (die.getValue() != 1) {
							characterDamage -= die.getValue();
						}
						die.roll();
						if (die.getValue() != 1) {
							characterDamage += die.getValue();
						}
						rerollLabel.setVisible(false);
						populateDiceSpaces();
						// calculateCharacterDamage();
						updateCharacterDamageSpace();
						disableDiceRerollOnClick();
						if (die.getValue() == 6) {
							showRerollSixOption();
						}

						if (!battleFinished) {
							showRerollOption();
						} else {
							disableRerollSixOption();
							disableRollButton();
						}
					}
				});
			}
		}

	}

	private void disableDiceRerollOnClick() {
		int numDice = diceRoller.getDice().size();
		for (int a = 0; a < numDice; a++) {
			diceSpaces[a].setCursor(null);
			diceSpaces[a].setOnMouseClicked(null);
		}
	}

	private void gainReward() {
		rewardText.setText(monster.getRewardText());
		Map<String, Integer> rewards = monster.getReward();
		for (Map.Entry<String, Integer> entry : rewards.entrySet()) {
			switch (entry.getKey()) {
			case "experience":
				character.setExperiencePoints(character.getExperiencePoints() + entry.getValue());
				break;
			case "gold":
				character.setGold(character.getGold() + entry.getValue());
				break;
			case "item":
				PauseTransition delayItemReward = new PauseTransition(Duration.seconds(1.0));
				delayItemReward.setOnFinished(event -> {
					itemRewardAction();
				});
				delayItemReward.play();
				break;
			case "ogsblood":
				break;
			default:
				break;
			}
		}
	}

	private void itemRewardAction() {
		Parent cardViewParent;
		try {
			cardViewParent = FXMLLoader.load(getClass().getResource(REWARD_ITEM_FXML));
			Stage stage = new Stage();
			Scene card_scene = new Scene(cardViewParent);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(cardViewParent.getScene().getWindow());
			stage.setScene(card_scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void disableDiceRerollOption() {
		rerollLabel.setVisible(false);
		yesLabel.setVisible(false);
		noLabel.setVisible(false);
	}
	/*
	 * MONSTER CARD END
	 */
}
