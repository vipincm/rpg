package com.company.rpg.core.world.event;

import com.company.rpg.model.PlayerCharacter;

/**
 * Event triggered in case of character relocating to an empty terrain
 * 
 * @author Vipin
 *
 */
public class EmptyWorldLocationEvent extends WorldEvent {
	private static final long serialVersionUID = 1L;

	public EmptyWorldLocationEvent(PlayerCharacter playerCharacter) {
		super(playerCharacter);
	}
}
