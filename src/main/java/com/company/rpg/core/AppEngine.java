package com.company.rpg.core;

import java.net.URISyntaxException;

import com.company.rpg.core.command.Command;
import com.company.rpg.core.command.CommandInput;
import com.company.rpg.core.command.handler.CommandHandlerManager;
import com.company.rpg.core.world.GameWorldProvider;
import com.company.rpg.core.world.event.WorldEvent;
import com.company.rpg.core.world.event.handler.WorldEventHandlerManager;
import com.company.rpg.util.FileUtils;
import com.company.rpg.util.IOUtils;

/**
 * Main game engine class.
 * 
 * <p>
 * Invoke {@link #start(String[])} for starting game.
 * </p>
 * 
 * @author Vipin
 */
public class AppEngine {

	private final CommandInput cmdInput;
	private final CommandHandlerManager cmdHandlerManager;
	private final WorldEventHandlerManager eventHandlerManager;
	private final GameWorldProvider worldProvider;
	private final AppContext context;
	private final IOUtils io;

	public AppEngine(CommandInput cmdInput, CommandHandlerManager cmdHandlerManager,
			WorldEventHandlerManager eventHandlerManager, GameWorldProvider worldProvider, AppContext context,
			IOUtils io) {
		this.cmdInput = cmdInput;
		this.cmdHandlerManager = cmdHandlerManager;
		this.eventHandlerManager = eventHandlerManager;
		this.worldProvider = worldProvider;
		this.context = context;
		this.io = io;
	}

	/**
	 * Starts the game.
	 * 
	 * <p>
	 * Method will block thread until the game exits.
	 * </p>
	 */
	public void start() {
		printBanner();
		initWorld();
		startMainLoop();
	}

	private void initWorld() {
		context.setWorld(worldProvider.get());
		cmdHandlerManager.printHelp();
	}

	private void startMainLoop() {
		while (isGameOngoing()) {
			processCommand(getUserCommand());
			processWorldEvent();
		}
	}

	private boolean isGameOngoing() {
		return context.isOngoing();
	}

	private Command getUserCommand() {
		return cmdInput.get();
	}

	private void processCommand(Command userCommand) {
		cmdHandlerManager.handle(userCommand);
	}

	private void processWorldEvent() {
		if (context.isTriggerEvent()) {
			context.resetTriggerEvent();
			WorldEvent worldEvent = context.getWorld().getEvent();
			eventHandlerManager.handle(worldEvent);
		}
	}

	/**
	 * Just print to the console game ASCII art
	 * 
	 * @throws URISyntaxException
	 */
	private void printBanner() {

		io.print(FileUtils.fileContentToString("banner"));
	}

}
