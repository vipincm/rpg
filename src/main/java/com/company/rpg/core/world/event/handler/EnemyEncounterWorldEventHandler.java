package com.company.rpg.core.world.event.handler;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.world.event.EnemyEncounterWorldEvent;
import com.company.rpg.model.EnemyCharacter;
import com.company.rpg.model.PlayerCharacter;
import com.company.rpg.util.IOUtils;
import com.company.rpg.util.RandomUtils;

/**
 * Handler for {@link EnemyEncounterWorldEvent}.
 * 
 * @author Vipin
 */
public class EnemyEncounterWorldEventHandler extends WorldEventHandler<EnemyEncounterWorldEvent> {

	private final IOUtils io;
	private final RandomUtils randomNumberGenerator;

	public EnemyEncounterWorldEventHandler(IOUtils io, RandomUtils randomNumberGenerator) {
		this.io = io;
		this.randomNumberGenerator = randomNumberGenerator;
	}

	@Override
	public void handleEvent(EnemyEncounterWorldEvent event, AppContext context) {
		PlayerCharacter playerCharacter = event.getPlayerCharacter();
		EnemyCharacter enemy = event.getEnemy();

		context.getWorld().setPlayerCharacterEnemy(enemy);

		if (enemy.isDead()) {
			io.say("%s found a dead %s.\n", playerCharacter.getName(), enemy.getName());
		} else {
			switch (enemy.getHostality()) {
				case LOW:
					handleLowlyHostileEnemy(enemy, playerCharacter);
					break;
				case HIGH:
				default:
					handleHighlyHostileEnemy(enemy, playerCharacter);
					break;
			}
		}
	}

	private void handleHighlyHostileEnemy(EnemyCharacter enemy, PlayerCharacter playerCharacter) {
		int damage = randomNumberGenerator.nextInt(enemy.getAttack());
		playerCharacter.takeDamage(damage);
		
		if(playerCharacter.isDead()) {
			io.say("%s was killed by %s.\nGame over.\n", playerCharacter.getName(), enemy.getName());
		} else if(damage == 0){
			io.say("%s dodged an attacked from a %s.\n", playerCharacter.getName(), enemy.getName());
		} else {
			io.say("%s was attacked by %s. Lost %d health.\n", playerCharacter.getName(), enemy.getName(), damage);
		}
	}

	private void handleLowlyHostileEnemy(EnemyCharacter enemy, PlayerCharacter playerCharacter) {
		io.say("%s encounters friendly %s.\n", playerCharacter.getName(), enemy.getName());
	}

}
