package com.company.rpg.core;

import java.io.Serializable;

import com.company.rpg.core.world.GameWorld;

/**
 * Represents the current application context.
 * 
 * @author Vipin
 */
public class AppContext  implements Serializable {

	private static final long serialVersionUID = 3413461373790921330L;

	/** Tells if the game is ongoing */
	private boolean ongoing;
	
	/** Tells if an event can be triggered */
	private boolean event;
	
	/** Holds the current world state */
	private GameWorld world;

	/**
	 * Sets the current world
	 * 
	 * @param world to set
	 */
	public void setWorld(GameWorld world) {
		this.world = world;
	}

	/**
	 * @return current world
	 */
	public GameWorld getWorld() {
		return world;
	}
	
	/**
	 * Sets the game ongoing state
	 * 
	 * @param ongoing flag to set
	 */
	public void setOngoing(boolean ongoing) {
		this.ongoing = ongoing;
	}

	/**
	 * Tells if the game is ongoing.
	 * 
	 * @return true if ongoing, false otherwise
	 */
	public boolean isOngoing() {
		return ongoing && world.getPlayerCharacter() != null && !world.getPlayerCharacter().isDead();
	}
	
	/**
	 * Sets the trigger event flag
	 */
	public void triggerEvent() {
		this.event = true;
	}
	
	/**
	 * Resets the trigger event flag
	 */
	public void resetTriggerEvent() {
		this.event = false;
	}
	
	/**
	 * @return trigger event flag
	 */
	public boolean isTriggerEvent() {
		return event;
	}
}
