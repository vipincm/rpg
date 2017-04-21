package com.company.rpg.core.world.event.generator;

import com.company.rpg.core.world.event.WorldEvent;

/**
 * World event generator interface
 */
public interface WorldEventGenerator {
	/**
	 * Generates a new world event
	 * 
	 * @return produced {@link WorldEvent}
	 */
	WorldEvent generate();
}