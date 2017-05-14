package application.factories;

import application.exceptions.UnknownBossMonsterException;
import application.interfaces.Dungeon;
import application.interfaces.Monster;
import application.models.monsters.bosses.OgsRemains;
import application.models.monsters.bosses.SerpentDemon;
import application.models.monsters.bosses.SkeletonLord;
import application.models.monsters.bosses.UndeadGiant;
import application.models.monsters.bosses.UndeadLord;

public class BossMonsterFactory {
	
	public static Monster create(Dungeon dungeon) throws UnknownBossMonsterException {
		switch (dungeon.getLevel()) {
		case 1:
			return new UndeadGiant();
		case 2:
			return new SkeletonLord();
		case 3:
			return new UndeadLord();
		case 4:
			return new SerpentDemon();
		case 5:
			return new OgsRemains();
		default:
			throw new UnknownBossMonsterException("The boss monster type is unknown!");			
		}
	}
}
