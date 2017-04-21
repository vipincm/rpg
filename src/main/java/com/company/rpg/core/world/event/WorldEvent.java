package com.company.rpg.core.world.event;

import java.io.Serializable;

import com.company.rpg.model.PlayerCharacter;

/**
 * Base class encapsulating a game world event.
 * 
 * <p>
 * A world event might be any event that is coming from the game's world being
 * indirectly triggered by an user action (in other words it's the worlds
 * 'reaction' to that action)
 * </p>
 * 
 * @author Vipin
 */
public abstract class WorldEvent implements Serializable {

	private static final long serialVersionUID = -1189864687854232051L;
	private final PlayerCharacter playerCharacter;

	public WorldEvent(PlayerCharacter playerCharacter) {
		this.playerCharacter = playerCharacter;
	}

	public PlayerCharacter getPlayerCharacter() {
		return playerCharacter;
	}
}
