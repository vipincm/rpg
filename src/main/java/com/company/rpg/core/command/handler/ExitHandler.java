package com.company.rpg.core.command.handler;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;
import com.company.rpg.core.world.GameWorldRepository;
import com.company.rpg.util.IOUtils;

import java.io.IOException;

/**
 * Handles the exit event.
 * 
 * @author Vipin
 */
public class ExitHandler extends CommandHandler {

	private static final String ACTION = "exit";
	private static final String DESCR = "exits the game";

	private final IOUtils io;
	private final GameWorldRepository gameWorldRepository;

	public ExitHandler(IOUtils io, GameWorldRepository gameWorldRepository) {
		super(ACTION, DESCR);
		this.io = io;
		this.gameWorldRepository = gameWorldRepository;
	}

	@Override
	public void handle(Command cmd, AppContext context) {
		context.setOngoing(false);

		if (io.askYesNo("Do you want to save the current game progress")) {
			try {
				gameWorldRepository.store(context.getWorld());
				io.say("Game stored successfully!\n");
			} catch (IOException e) {
				io.say("Saving game progress failed.\n");
			} finally {
				io.close();
			}
		}
	}

}
