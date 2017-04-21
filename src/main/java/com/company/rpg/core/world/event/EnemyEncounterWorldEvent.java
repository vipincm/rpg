package com.company.rpg.core.world.event;

import com.company.rpg.model.EnemyCharacter;
import com.company.rpg.model.PlayerCharacter;

/**
 * Event triggered in case character encountering an enemy.
 * 
 * @author Vipin
 */
public class EnemyEncounterWorldEvent extends WorldEvent {

	private static final long serialVersionUID = 1L;
	private final EnemyCharacter enemy;

	public EnemyEncounterWorldEvent(PlayerCharacter playerCharacter, EnemyCharacter enemy) {
		super(playerCharacter);
		this.enemy = enemy;
	}

	public EnemyCharacter getEnemy() {
		return enemy;
	}

}
