package com.company.rpg.core.world.event.handler;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.world.event.EmptyWorldLocationEvent;
import com.company.rpg.util.IOUtils;

/**
 * Handler for {@link EmptyWorldLocationEventHandler}. 
 * 
 * @author Vipin
 */
public class EmptyWorldLocationEventHandler extends WorldEventHandler<EmptyWorldLocationEvent> {

	private final IOUtils io;

	public EmptyWorldLocationEventHandler(IOUtils io) {
		this.io = io;
	}
	
	@Override
	public void handleEvent(EmptyWorldLocationEvent event, AppContext context) {
		io.say("Hmm.. nothing interesting here\n");
		context.getWorld().discardPlayerCharacterEnemy();
	}

}
