package com.company.rpg.core.world.event.generator;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.world.GameWorld;
import com.company.rpg.core.world.event.EmptyWorldLocationEvent;
import com.company.rpg.core.world.event.EnemyEncounterWorldEvent;
import com.company.rpg.core.world.event.WorldEvent;
import com.company.rpg.model.Coordinates;
import com.company.rpg.model.PlayerCharacter;
import com.company.rpg.util.RandomUtils;

import java.io.Serializable;

/**
 * Strategy for generating world events. 
 * 
 * @author Vipin
 */
public class RandomWorldEventGenerator implements WorldEventGenerator, Serializable {
	private static final long serialVersionUID = 1L;
	private static final Coordinates START_POINT = new Coordinates(0, 0);

	private final RandomUtils randomNumberGenerator;
	private final AppContext context;

	public RandomWorldEventGenerator(RandomUtils randomNumberGenerator, AppContext context) {
		this.randomNumberGenerator = randomNumberGenerator;
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 * <p> Enemy characteristics is based on randomness. </p>
	 */
	@Override
	public WorldEvent generate() {
		GameWorld world = context.getWorld();
		PlayerCharacter playerCharacter = world.getPlayerCharacter();
		
		// the user always has the start point discovered, so generate empty world event
		boolean isStartPoint = world.getPlayerCharacterLocation().equals(START_POINT);
		if ( isStartPoint || randomNumberGenerator.nextBoolean()) {
			return new EmptyWorldLocationEvent(playerCharacter);
		} else {
			EnemyFactory enemyFactor= new RandomEnemyFactory(randomNumberGenerator, context.getWorld().getTopic());
			return new EnemyEncounterWorldEvent(playerCharacter, enemyFactor.createEnemy());
		}
	}
}
