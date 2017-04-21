package com.company.rpg.core.world.event.generator;

import com.company.rpg.model.EnemyCharacter;

/**
 * Factory for {@link EnemyCharacter}'s
 * 
 * @author Vipin
 */
public interface EnemyFactory {

	/**
	 * Creates an enemy instance
	 */
	EnemyCharacter createEnemy();
}