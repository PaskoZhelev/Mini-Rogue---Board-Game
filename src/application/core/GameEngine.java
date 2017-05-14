package application.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.interfaces.Card;
import application.interfaces.Character;
import application.interfaces.Dungeon;
import application.interfaces.Monster;
import application.models.DungeonImpl;
import application.models.cards.BossMonsterCard;
import application.models.cards.EventCard;
import application.models.cards.MerchantCard;
import application.models.cards.MonsterCard;
import application.models.cards.RestingCard;
import application.models.cards.TrapCard;
import application.models.cards.TreasureCard;
import application.models.characters.CharacterImpl;
import application.models.enums.CardType;
import application.models.enums.Difficulty;

public class GameEngine {
	
	private static final int HEALTH_CASUAL = 5;
	private static final int GOLD_CASUAL = 5;
	private static final int FOOD_CASUAL = 6;
	private static final int ARMOR_CASUAL = 1;
	private static final int HEALTH_NORMAL = 5;
	private static final int GOLD_NORMAL = 3;
	private static final int FOOD_NORMAL = 6;
	private static final int ARMOR_NORMAL = 0;
	private static final int HEALTH_HARD = 4;
	private static final int GOLD_HARD = 2;
	private static final int FOOD_HARD = 5;
	private static final int ARMOR_HARD = 0;
	private static final int HEALTH_IMPOSSIBLE = 3;
	private static final int GOLD_IMPOSSIBLE = 1;
	private static final int FOOD_IMPOSSIBLE = 3;
	private static final int ARMOR_IMPOSSIBLE = 0;
	
	private Character character;
	private Dungeon dungeon;
	private List<Card> cards;
	private Card selectedCard;
	private Monster monster;
	private boolean iceSpellActivated;
	private boolean poisonSpellActivated;
	private boolean roundFinished;
	private boolean eventMonster;
	private boolean gameFinished;
		

	public GameEngine(Difficulty difficulty) {
		character = createCharacter(difficulty);
		dungeon = new DungeonImpl();
		selectedCard = null;
		monster = null;
		cards = new ArrayList<>();
		cards.add(new MonsterCard());		
		cards.add(new EventCard());
		cards.add(new MerchantCard());
		cards.add(new RestingCard());
		cards.add(new TrapCard());
		cards.add(new TreasureCard());
		cards.add(new BossMonsterCard());
	}

	public Character getCharacter() {
		return this.character;
	}

	public Dungeon getDungeon() {
		return this.dungeon;
	}
	
	public List<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}
	
	public Card getCardByIndex(int index){
		return cards.get(index);
	}
	
	public Card getCardByType(CardType type) {
		Card neededCard = null;
		for (Card card : cards) {
			if (card.getCardType().equals(type)){
				neededCard = card;
			}
		}
		
		return neededCard;
	}
	
	public void cardsResetShownStatus() {
		cards.stream().forEach(card ->{
			card.setShown(false);
		});
	}

	public Card getSelectedCard() {
		return this.selectedCard;
	}

	public void setSelectedCard(Card card) {
		this.selectedCard = card;
	}

	public Monster getMonster() {
		return this.monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	public boolean isIceSpellActivated() {
		return this.iceSpellActivated;
	}

	public void setIceSpellActivated(boolean iceSpellActivated) {
		this.iceSpellActivated = iceSpellActivated;
	}

	public boolean isPoisonSpellActivated() {
		return this.poisonSpellActivated;
	}

	public void setPoisonSpellActivated(boolean poisonSpellActivated) {
		this.poisonSpellActivated = poisonSpellActivated;
	}

	public boolean isRoundFinished() {
		return this.roundFinished;
	}

	public void setRoundFinished(boolean roundFinished) {
		this.roundFinished = roundFinished;
	}

	public boolean isEventMonster() {
		return this.eventMonster;
	}

	public void setEventMonster(boolean eventMonster) {
		this.eventMonster = eventMonster;
	}
	
	public boolean isGameFinished() {
		return gameFinished;
	}

	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}
	
	private Character createCharacter(Difficulty difficulty) {
		switch (difficulty) {
		case CASUAL:
			return new CharacterImpl(ARMOR_CASUAL, HEALTH_CASUAL, GOLD_CASUAL, FOOD_CASUAL);
		case NORMAL:
			return new CharacterImpl(ARMOR_NORMAL, HEALTH_NORMAL, GOLD_NORMAL, FOOD_NORMAL);
		case HARD:
			return new CharacterImpl(ARMOR_HARD, HEALTH_HARD, GOLD_HARD, FOOD_HARD);
		case IMPOSSIBLE:
			return new CharacterImpl(ARMOR_IMPOSSIBLE, HEALTH_IMPOSSIBLE, GOLD_IMPOSSIBLE, FOOD_IMPOSSIBLE);
		default:
			return new CharacterImpl(ARMOR_CASUAL, HEALTH_CASUAL, GOLD_CASUAL, FOOD_CASUAL);
		}
	}
}
