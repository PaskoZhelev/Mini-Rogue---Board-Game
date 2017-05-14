package application.factories;

import application.exceptions.UnknownBossMonsterException;
import application.exceptions.UnknownNormalMonsterException;
import application.handlers.RollingDiceHandler;
import application.interfaces.Dungeon;
import application.interfaces.Monster;
import application.models.monsters.normal.OgSanctumGuard;
import application.models.monsters.normal.SerpentKnight;
import application.models.monsters.normal.Skeleton;
import application.models.monsters.normal.UndeadKnight;
import application.models.monsters.normal.UndeadSoldier;

public class NormalMonsterFactory {

	public static Monster create(Dungeon dungeon) throws UnknownNormalMonsterException{
		RollingDiceHandler diceHandler = new RollingDiceHandler(1);
		diceHandler.rollDice();
		int monsterHealth = diceHandler.getDice().get(0).getValue() + dungeon.getDungeonArea();

		switch (dungeon.getLevel()) {
		case 1:
			return new UndeadSoldier(monsterHealth);
		case 2:
			return new Skeleton(monsterHealth);
		case 3:
			return new UndeadKnight(monsterHealth);
		case 4:
			return new SerpentKnight(monsterHealth);
		case 5:
			return new OgSanctumGuard(monsterHealth);
		default:
			throw new UnknownNormalMonsterException("This monster type is unknown!");
		}
	}
}
