package com.company.rpg.core.command.handler;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;
import com.company.rpg.model.PlayerCharacter;
import com.company.rpg.util.IOUtils;

/**
 * Handles the character status command
 * 
 * @author Vipin
 */
public class StatusHandler extends CommandHandler {

	private static final String ACTION = "status";
	private static final String DESCR = "displays current status of the character";
	private final IOUtils io;

	public StatusHandler(IOUtils io) {
		super(ACTION, DESCR);
		this.io = io;
	}

	@Override
	public void handle(Command cmd, AppContext context) {
		PlayerCharacter playerCharacter = context.getWorld().getPlayerCharacter();
		io.say("%s's status:\n", playerCharacter.getName());
		io.say(" Attack experience: %d\n", playerCharacter.getAttack());
		io.say(" Health: %d\n", playerCharacter.getHealth());
		io.say(" State: %s\n", playerCharacter.getState().name());
	}

}
