package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import application.core.GameEngine;
import application.exceptions.GameException;
import application.factories.RandomCardFactory;
import application.interfaces.Card;
import application.interfaces.Character;
import application.interfaces.Dungeon;
import application.models.cards.BossMonsterCard;
import application.models.cards.EventCard;
import application.models.cards.MerchantCard;
import application.models.cards.MonsterCard;
import application.models.cards.RestingCard;
import application.models.cards.TrapCard;
import application.models.cards.TreasureCard;
import application.models.enums.CardType;
import application.models.enums.Difficulty;
import application.models.spells.FireballSpell;
import application.models.spells.HealingSpell;
import application.models.spells.PoisonSpell;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class GameController implements Initializable {

	private static final String MONSTER_CARD = "/images/cards/Monster.jpg";
	private static final String BOSS_MONSTER_CARD = "/images/cards/BossMonster.jpg";
	private static final String EVENT_CARD = "/images/cards/Event.jpg";
	private static final String MERCHANT_CARD = "/images/cards/Merchant.jpg";
	private static final String RESTING_CARD = "/images/cards/Resting.jpg";
	private static final String TRAP_CARD = "/images/cards/Trap.jpg";
	private static final String TREASURE_CARD = "/images/cards/Treasure.jpg";
	private static final String BACK_OF_CARD = "/images/cards/CardBackTitle.jpg";
	private static final String DUNGEON_CARD = "/images/cards/Dungeon.jpg";
	private static final String CHAR_STATS_CARD = "/images/cards/CharStatsOld.jpg";
	private static final String DEAD_FXML = "/views/DeadView.fxml";
	private static final String WIN_FXML = "/views/WinGameView.fxml";
	private static final String MAIN_MENU_FXML = "/views/Main.fxml";
	private static final double IMAGE_VIEW_WIDTH = 163;
	private static final double IMAGE_VIEW_HEIGHT = 228;
	private static final double IMAGE_VIEW_WIDTH_ENLARGED = 351;
	private static final double IMAGE_VIEW_HEIGHT_ENLARGED = 431;
	private static final double NORMAL_X_PROPERTY_CARD_THREE = 204;
	private static final double NORMAL_Y_PROPERTY_CARD_THREE = 349;
	private static final double ENLARGING_X_PROPERTY_CARD_THREE = 216;
	private static final double ENLARGING_Y_PROPERTY_CARD_THREE = 216;
	private static final double NORMAL_X_PROPERTY_CARD_SIX = 573;
	private static final double NORMAL_Y_PROPERTY_CARD_SIX = 349;
	private static final double ENLARGING_X_PROPERTY_CARD_SIX = 510;
	private static final double ENLARGING_Y_PROPERTY_CARD_SIX = 216;
	private static final double NORMAL_X_PROPERTY_CARD_SEVEN = 757;
	private static final double NORMAL_Y_PROPERTY_CARD_SEVEN = 216;
	private static final double ENLARGING_X_PROPERTY_CARD_SEVEN = 623;
	private static final double ENLARGING_Y_PROPERTY_CARD_SEVEN = 216;
	

	@FXML
	private ImageView cardSpaceOne;
	@FXML
	private ImageView cardSpaceTwo;
	@FXML
	private ImageView cardSpaceThree;
	@FXML
	private ImageView cardSpaceFour;
	@FXML
	private ImageView cardSpaceFive;
	@FXML
	private ImageView cardSpaceSix;
	@FXML
	private ImageView cardSpaceSeven;

	@FXML
	private Image monsterCardImage = new Image(MONSTER_CARD);
	@FXML
	private Image bossMonsterCardImage = new Image(BOSS_MONSTER_CARD);
	@FXML
	private Image eventCardImage = new Image(EVENT_CARD);
	@FXML
	private Image merchantCardImage = new Image(MERCHANT_CARD);
	@FXML
	private Image restingCardImage = new Image(RESTING_CARD);
	@FXML
	private Image trapCardImage = new Image(TRAP_CARD);
	@FXML
	private Image treasureCardImage = new Image(TREASURE_CARD);
	@FXML
	private Image dungeonCardImage = new Image(DUNGEON_CARD);
	@FXML
	private Image backCardImage = new Image(BACK_OF_CARD);
	@FXML
	private Image charStatsCardImage = new Image(CHAR_STATS_CARD);
	
	@FXML
	private Label healthLabel;
	@FXML
	private Label goldLabel;
	@FXML
	private Label foodLabel;
	@FXML
	private Label armorLabel;
	@FXML
	private Label rankLabel;
	@FXML
	private Label xpLabel;
	@FXML
	private Label spellOneLabel;
	@FXML
	private Label spellTwoLabel;
	@FXML
	private Label difficultyLabel;
	@FXML
	private Rectangle dungeon1;
	@FXML
	private Rectangle dungeon2;
	@FXML
	private Rectangle dungeon3;
	@FXML
	private Rectangle dungeon4;
	@FXML
	private Rectangle dungeon5;
	@FXML
	private Rectangle dungeon6;
	@FXML
	private Rectangle dungeon7;
	@FXML
	private Rectangle dungeon8;
	@FXML
	private Rectangle dungeon9;
	@FXML
	private Rectangle dungeon10;
	@FXML
	private Rectangle dungeon11;
	@FXML
	private Rectangle dungeon12;
	@FXML
	private Rectangle dungeon13;
	@FXML
	private Rectangle dungeon14;
	

	private static GameEngine engine;
	private Map<ImageView, Card> generatedCards;
	private Map<Integer, Rectangle> dungeonLevels;
	private int currentRound = 1;
	private Character character; 
	private Dungeon dungeon;
	
	private Card cardOne;
	private Card cardTwo;
	private Card cardThree;
	private Card cardFour;
	private Card cardFive;
	private Card cardSix;
	private Card cardSeven;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		engine = new GameEngine(MainController.getDifficulty());
		generatedCards = new HashMap<>();
		character = engine.getCharacter();
		dungeon = engine.getDungeon();
		dungeonLevels = new HashMap<>();
		difficultyLabel.setText(MainController.getDifficulty().toString());
		createDungeonLevelRectangles();
		roundOne();
		updateCharStatsLabels();
		
		
	}

	/*
	 * ROUNDS
	 */

	public void roundOne() {
		
		resetShownCards();
		flipCardsToBack();
		disableCardsZoom();
		generateRandomCards();
		
		engine.setRoundFinished(false);
		cardSpaceOne.setImage(generatedCards.get(cardSpaceOne).getImage());
		allowMouseZoom(cardSpaceOne);
		allowMouseClick(cardSpaceOne);
		disableMouseClick(cardSpaceSeven);
	}

	public void roundTwo() {
		engine.setRoundFinished(false);
		disableMouseClick(cardSpaceOne);
		cardSpaceTwo.setImage(generatedCards.get(cardSpaceTwo).getImage());
		cardSpaceThree.setImage(generatedCards.get(cardSpaceThree).getImage());
		allowMouseClick(cardSpaceTwo);
		allowMouseClick(cardSpaceThree);
		allowMouseZoom(cardSpaceTwo);
		allowMouseZoom(cardSpaceThree);
	}
	
	public void roundThree() {
		engine.setRoundFinished(false);
		disableMouseClick(cardSpaceTwo);
		disableMouseClick(cardSpaceThree);
		cardSpaceFour.setImage(generatedCards.get(cardSpaceFour).getImage());		
		allowMouseClick(cardSpaceFour);
		allowMouseZoom(cardSpaceFour);
	}
	
	public void roundFour() {
		engine.setRoundFinished(false);
		disableMouseClick(cardSpaceFour);
		cardSpaceFive.setImage(generatedCards.get(cardSpaceFive).getImage());
		cardSpaceSix.setImage(generatedCards.get(cardSpaceSix).getImage());
		allowMouseClick(cardSpaceFive);
		allowMouseClick(cardSpaceSix);
		allowMouseZoom(cardSpaceFive);
		allowMouseZoom(cardSpaceSix);
	}
	
	public void roundFive() throws GameException {
		switch (dungeon.getDungeonArea()) {
		case 2:
		case 4:
		case 7:
		case 10:
		case 14:
			engine.setRoundFinished(false);
			disableMouseClick(cardSpaceFive);
			disableMouseClick(cardSpaceSix);
			cardSpaceSeven.setImage(cardSeven.getImage());
			allowMouseClick(cardSpaceSeven);
			allowMouseZoom(cardSpaceSeven);
			break;

		default:
			disableMouseClick(cardSpaceFive);
			disableMouseClick(cardSpaceSix);
			eatAndChangeDungeonArea();
			changeCurrentRound();
			loadCurrentRound();
			break;
		}
		
	}
	
	/****************************/

	public void loadCurrentRound() throws GameException {
		switch (currentRound) {
		case 1:
			roundOne();			
			break;
		case 2:
			roundTwo();
			break;
		case 3:
			roundThree();
			break;
		case 4:
			roundFour();
			break;
		case 5:
			roundFive();
			break;

		default:
			throw new GameException("Cannot load the round!");
		}
	}

	public void changeCurrentRound() throws GameException {
		if (currentRound < 5) {
			currentRound++;
		} else if (currentRound == 5) {
			currentRound = 1;			
		} else {
			throw new GameException("Invalid Current Round!");
		}
	}

	private void eatAndChangeDungeonArea() {
		if(character.getFood() > 0) {
			character.setFood(character.getFood() - 1);
		} else {
			character.setHealth(character.getHealth() - 2);
		}
		
		dungeon.goToTheNextAreaAndLevel();
	}
	
	public void generateRandomCards() {		
		cardOne = RandomCardFactory.create(engine);
		cardTwo = RandomCardFactory.create(engine);
		cardThree = RandomCardFactory.create(engine);
		cardFour = RandomCardFactory.create(engine);
		cardFive = RandomCardFactory.create(engine);
		cardSix = RandomCardFactory.create(engine);
		cardSeven = new BossMonsterCard();
		generatedCards.put(cardSpaceOne, cardOne);
		generatedCards.put(cardSpaceTwo, cardTwo);
		generatedCards.put(cardSpaceThree, cardThree);
		generatedCards.put(cardSpaceFour, cardFour);
		generatedCards.put(cardSpaceFive, cardFive);
		generatedCards.put(cardSpaceSix, cardSix);
		
		generatedCards.put(cardSpaceSeven, cardSeven);
	}

	public void flipCardsToBack() {
		cardSpaceOne.setImage(backCardImage);
		cardSpaceTwo.setImage(backCardImage);
		cardSpaceThree.setImage(backCardImage);
		cardSpaceFour.setImage(backCardImage);
		cardSpaceFive.setImage(backCardImage);
		cardSpaceSix.setImage(backCardImage);
		cardSpaceSeven.setImage(backCardImage);
	}

	public void resetShownCards() {
		generatedCards.clear();
		engine.cardsResetShownStatus();
	}

	public static GameEngine getEngine() {
		return engine;
	}

	public void loadCardView(Card selectedCard) {
		Parent cardViewParent;
		try {
			cardViewParent = FXMLLoader.load(getClass().getResource(selectedCard.getFxml()));
			Stage stage = new Stage();
			Scene card_scene = new Scene(cardViewParent);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(cardViewParent.getScene().getWindow());
			stage.setScene(card_scene);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent ev) {
					if (!engine.isRoundFinished()) {
						ev.consume();
					}
				}
			});
			stage.setOnHiding(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent windowEvent) {
					try {
						if((currentRound == 5) && (!engine.isGameFinished()) && (!(character.getHealth() <= 0))) {
							eatAndChangeDungeonArea();
						}
						
						if(isGameStatusChanged()) {
							loadGameOverViewByGameStatus();
						} else {
							changeCurrentRound();						
							loadCurrentRound();
						}										
					} catch (GameException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					updateCharStatsLabels();
				}
			});
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 *
	 * MOUSE EVENTS
	 *
	 */

	public void allowMouseClick(ImageView imageView) {
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {				
				Card card = generatedCards.get(imageView);

				engine.setSelectedCard(card);
				loadCardView(card);
			}
		});
	}

	public void disableMouseClick(ImageView imageView) {
		imageView.setOnMouseClicked(null);
	}
	
	private void disableCardsZoom() {
		disableMouseZoom(cardSpaceTwo);
		disableMouseZoom(cardSpaceThree);
		disableMouseZoom(cardSpaceFour);
		disableMouseZoom(cardSpaceFive);
		disableMouseZoom(cardSpaceSix);
		disableMouseZoom(cardSpaceSeven);
	}
	
	public void allowMouseZoom(ImageView imageView) {
		imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (imageView.equals(cardSpaceThree)) { // bottom space zoom of the cards should be controlled
					imageView.setFitHeight(IMAGE_VIEW_HEIGHT_ENLARGED);
					imageView.setFitWidth(IMAGE_VIEW_WIDTH_ENLARGED);
					imageView.toFront();
					imageView.setLayoutX(ENLARGING_X_PROPERTY_CARD_THREE);
					imageView.setLayoutY(ENLARGING_Y_PROPERTY_CARD_THREE);
				} else if (imageView.equals(cardSpaceSix)) {
					imageView.setFitHeight(IMAGE_VIEW_HEIGHT_ENLARGED);
					imageView.setFitWidth(IMAGE_VIEW_WIDTH_ENLARGED);
					imageView.toFront();
					imageView.setLayoutX(ENLARGING_X_PROPERTY_CARD_SIX);
					imageView.setLayoutY(ENLARGING_Y_PROPERTY_CARD_SIX);
				} else if(imageView.equals(cardSpaceSeven)) {
					imageView.setFitHeight(IMAGE_VIEW_HEIGHT_ENLARGED);
					imageView.setFitWidth(IMAGE_VIEW_WIDTH_ENLARGED);
					imageView.toFront();
					imageView.setLayoutX(ENLARGING_X_PROPERTY_CARD_SEVEN);
					imageView.setLayoutY(ENLARGING_Y_PROPERTY_CARD_SEVEN);
				} else {
					imageView.setFitHeight(IMAGE_VIEW_HEIGHT_ENLARGED);
					imageView.setFitWidth(IMAGE_VIEW_WIDTH_ENLARGED);
					imageView.toFront();
				}
			}
		});

		imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (imageView.equals(cardSpaceThree)) {
					imageView.setFitHeight(IMAGE_VIEW_HEIGHT);
					imageView.setFitWidth(IMAGE_VIEW_WIDTH);
					imageView.setLayoutX(NORMAL_X_PROPERTY_CARD_THREE);
					imageView.setLayoutY(NORMAL_Y_PROPERTY_CARD_THREE);
				} else if (imageView.equals(cardSpaceSix)) {
					imageView.setFitHeight(IMAGE_VIEW_HEIGHT);
					imageView.setFitWidth(IMAGE_VIEW_WIDTH);
					imageView.setLayoutX(NORMAL_X_PROPERTY_CARD_SIX);
					imageView.setLayoutY(NORMAL_Y_PROPERTY_CARD_SIX);
				} else if (imageView.equals(cardSpaceSeven)) {
					imageView.setFitHeight(IMAGE_VIEW_HEIGHT);
					imageView.setFitWidth(IMAGE_VIEW_WIDTH);
					imageView.setLayoutX(NORMAL_X_PROPERTY_CARD_SEVEN);
					imageView.setLayoutY(NORMAL_Y_PROPERTY_CARD_SEVEN);
				} else {
					imageView.setFitHeight(IMAGE_VIEW_HEIGHT);
					imageView.setFitWidth(IMAGE_VIEW_WIDTH);
				}
			}
		});
	}

	public void disableMouseZoom(ImageView imageView) {
		imageView.setOnMouseEntered(null);
		imageView.setOnMouseExited(null);
	}

	private boolean isGameStatusChanged() {
		if ((character.getHealth() <= 0) || (engine.isGameFinished())) {
			return true;
		} else {
			return false;
		}
	}
	
	private void loadGameOverViewByGameStatus() throws IOException {
		if (character.getHealth() <= 0) {
			loadGameOverView(DEAD_FXML);
		} else if (engine.isGameFinished()) {
			loadGameOverView(WIN_FXML);
		} else {
			// do nothing
		}
	}
	
	public void loadGameOverView(String fxml) throws IOException {
		
		Parent cardViewParent;
		try {
			cardViewParent = FXMLLoader.load(getClass().getResource(fxml));
			Stage stage = new Stage();
			Scene card_scene = new Scene(cardViewParent);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(cardViewParent.getScene().getWindow());
			stage.setScene(card_scene);
			stage.setResizable(false);	
			stage.setOnHiding(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent windowEvent) {
					try {
						loadMainMenu();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
			//stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void updateCharStatsLabels() {
		goldLabel.setText(String.valueOf(character.getGold()));
		healthLabel.setText(String.valueOf(character.getHealth()));
		armorLabel.setText(String.valueOf(character.getArmor()));
		foodLabel.setText(String.valueOf(character.getFood()));
		xpLabel.setText(String.valueOf(character.getExperiencePoints()));
		rankLabel.setText(String.valueOf(character.getRank()));
		showDungeonLevel();
				
		if (character.getSpellOne() != null) {			
			spellOneLabel.setText(character.getSpellOne().getType().toString());
		} else {
			spellOneLabel.setText(null);
		}
		if (character.getSpellTwo() != null) {		
			spellTwoLabel.setText(character.getSpellTwo().getType().toString());
		} else {
			spellTwoLabel.setText(null);
		}
	}
	
	private void loadMainMenu() throws IOException {
		Parent newgame_parent = FXMLLoader.load(getClass().getResource(MAIN_MENU_FXML));
      Scene newgame_scene = new Scene(newgame_parent);
      Stage app_stage = (Stage) cardSpaceOne.getScene().getWindow();
      app_stage.setScene(newgame_scene);
      app_stage.setX(100.0);
      app_stage.setY(18.0);
      app_stage.show();
	}
	
	private void showDungeonLevel() {
		hideDungeonLevelRectangles();
		int dungeonArea = dungeon.getDungeonArea();
		dungeonLevels.get(dungeonArea).setVisible(true);
	}
	
	private void createDungeonLevelRectangles() {
		dungeonLevels.put(1, dungeon1);
		dungeonLevels.put(2, dungeon2);
		dungeonLevels.put(3, dungeon3);
		dungeonLevels.put(4, dungeon4);
		dungeonLevels.put(5, dungeon5);
		dungeonLevels.put(6, dungeon6);
		dungeonLevels.put(7, dungeon7);
		dungeonLevels.put(8, dungeon8);
		dungeonLevels.put(9, dungeon9);
		dungeonLevels.put(10, dungeon10);
		dungeonLevels.put(11, dungeon11);
		dungeonLevels.put(12, dungeon12);
		dungeonLevels.put(13, dungeon13);
		dungeonLevels.put(14, dungeon14);
	}
	
	private void hideDungeonLevelRectangles() {
		dungeon1.setVisible(false);
		dungeon2.setVisible(false);
		dungeon3.setVisible(false);
		dungeon4.setVisible(false);
		dungeon5.setVisible(false);
		dungeon6.setVisible(false);
		dungeon7.setVisible(false);
		dungeon8.setVisible(false);
		dungeon9.setVisible(false);
		dungeon10.setVisible(false);
		dungeon11.setVisible(false);
		dungeon12.setVisible(false);
		dungeon13.setVisible(false);
		dungeon14.setVisible(false);
	}


	
}
